package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = SensorActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private LinearLayout mLinearLayout;
    private TextView mTextViewSensorName;
    private TextView mTextViewType;
    private TextView mTextViewMinDelay;
    private TextView mTextViewMaxDelay;
    private TextView mTextViewMaxRange;
    private GraphFragment mGraphFragment;
    private SensorTypesImpl mSensorTypesImpl;
    private int mCurrAccuracy = 0;
    private int mValues = 0;
    private String mUnit = "";
    private int mCurrID= 0;
    private List<Integer> mTextViewIDs; // left is hierarchy, right is id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_sensor);
        mTextViewSensorName = (TextView) mLinearLayout.findViewById(R.id.text_sensor_name);
        mSensorTypesImpl = new SensorTypesImpl();

        Intent intent = getIntent();
        int sensorType = intent.getIntExtra("sensorType", 0);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorType != 0){
            mSensor = mSensorManager.getDefaultSensor(sensorType);
        } else{
            Log.d(TAG,"Sensor Type was 0!");
        }
        if (mSensor != null){
            mTextViewType = (TextView) mLinearLayout.findViewById(R.id.text_type);
            mTextViewMinDelay = (TextView) mLinearLayout.findViewById(R.id.text_min_delay);
            mTextViewMaxDelay = (TextView) mLinearLayout.findViewById(R.id.text_max_delay);
            mTextViewMaxRange = (TextView) mLinearLayout.findViewById(R.id.text_max_range);
            mTextViewSensorName.setText("Name: " + mSensor.getName());
            mTextViewType.setText("Type: " + mSensor.getStringType());
            mTextViewMinDelay.setText("Min Delay: " + Integer.toString(mSensor.getMinDelay()));
            mTextViewMaxDelay.setText("Max Delay: " + Integer.toString(mSensor.getMaxDelay()));
            mTextViewMaxRange.setText("Max Range: " + Float.toString(mSensor.getMaximumRange()));

            mValues = mSensorTypesImpl.getNumberValues(sensorType);
            mUnit = mSensorTypesImpl.getUnitString(sensorType);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            mTextViewIDs = new ArrayList<>();

            for(int i = 0; i < mValues; i++){
                TextView textView = new TextView(this);
                int id = findUnusedId();
                textView.setId(id);
                mTextViewIDs.add(i, id);
                textView.setText("0");
                mLinearLayout.addView(textView, layoutParams);
            }

            // TODO call with mValues, min, max etc.
            mGraphFragment = new GraphFragment();
            getFragmentManager().beginTransaction().add(R.id.activity_sensor, mGraphFragment).commit();

        } else {
            mTextViewSensorName.setText("no sensor for this type available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSensor != null){
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public GraphContainer getGraphContainer(){
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // mGraphFragment.addValues(event.timestamp, );
        for (int i = 0; i < mValues; i++){
            TextView textView = (TextView) mLinearLayout.findViewById(mTextViewIDs.get(i));
            textView.setText(mUnit + ": " + Float.toString(event.values[i]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        mCurrAccuracy = accuracy;
    }

    // TODO javadoc?
    // Utility function to produce IDs for the dynamically added View objects
    // As in http://stackoverflow.com/questions/1714297/android-view-setidint-id-programmatically-how-to-avoid-id-conflicts
    public int findUnusedId() {
        while( mLinearLayout.findViewById(++mCurrID) != null );
        return mCurrID;
    }

}
