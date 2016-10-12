package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = SensorActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private LinearLayout mLinearLayout;
    private List<Integer> mTextViewIDs;
    private GraphWrapper mGraphWrapper;
    private SensorTypesImpl mSensorTypesImpl;
    private int mAccuracy = 0;
    private int mValues = 0; // How many values does the sensor return? e.g. 3 for 3D vectors
    private String mUnit = "";
    private long mTimeStart;
    private boolean mIsFirstEvent;
    private int mCurrID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_sensor);
        TextView textViewSensorName = (TextView) mLinearLayout.findViewById(R.id.text_sensor_name);
        mSensorTypesImpl = new SensorTypesImpl();
        Intent intent = getIntent();
        int sensorType = intent.getIntExtra("sensor_index", 0);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorType != 0){
            mSensor = mSensorManager.getDefaultSensor(sensorType);
        } else{
            Log.w(TAG, "INSTRUMENTATIONTEST");
            mGraphWrapper = new GraphWrapper(this, mLinearLayout, R.id.graphview, 0);
            mGraphWrapper.initGraph(3,"TEST");
        }
        if (mSensor != null){
            textViewSensorName.setText("Name: " + mSensor.getName());
            TextView textViewType = (TextView) mLinearLayout.findViewById(R.id.text_type);
            textViewType.setText("Type: " + mSensor.getStringType());

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

            mGraphWrapper = new GraphWrapper(this, mLinearLayout, R.id.graphview, (long) (mSensor.getMinDelay()/1e3));
            mGraphWrapper.initGraph(mValues,mUnit);

        } else {
            Log.e(TAG, "Sensor was null, no sensor for this type available");
            textViewSensorName.setText("no sensor for this type available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSensor != null){
            mIsFirstEvent = true;
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        // TODO reset mgraph series data

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        // TODO save mgraph series data
    }

    public GraphContainer getGraphContainer(){
        return mGraphWrapper;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = 0.0;
        if(mIsFirstEvent) {
            mTimeStart = event.timestamp;
            mIsFirstEvent = false;
        }
        else {
            x = (double)(event.timestamp - mTimeStart) / 1e9;
        }

                for (int i = 0; i < mValues; i++){
            TextView textView = (TextView) mLinearLayout.findViewById(mTextViewIDs.get(i));
            textView.setText(Float.toString(event.values[i]));
            textView.setText(mUnit + ": " + Float.toString(event.values[i]));
        }
        mGraphWrapper.addValues(x, event.values);
        Log.w(TAG, "wrote: " + Double.toString(x) + ", " + Double.toString(event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        mAccuracy = accuracy;
    }
    public int findUnusedId() {
        while( mLinearLayout.findViewById(++mCurrID) != null );
        return mCurrID;
    }

}
