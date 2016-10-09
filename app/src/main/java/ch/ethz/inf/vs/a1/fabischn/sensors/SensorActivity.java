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
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = SensorActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mTextViewSensorName;
    private TextView mTextViewValue;
    private TextView mtextViewType;
    private TextView mtextViewMinDelay;
    private TextView mtextViewMaxDelay;
    private TextView mtextViewMaxRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mTextViewSensorName = (TextView) findViewById(R.id.text_sensor_name);
        mTextViewValue = (TextView) findViewById(R.id.text_value);
        mtextViewType = (TextView) findViewById(R.id.text_type);
        mtextViewMinDelay = (TextView) findViewById(R.id.text_min_delay);
        mtextViewMaxDelay = (TextView) findViewById(R.id.text_max_delay);
        mtextViewMaxRange = (TextView) findViewById(R.id.text_max_range);
        Intent intent = getIntent();
        int sensorType = intent.getIntExtra("sensorType", 0);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorType != 0){
            mSensor = mSensorManager.getDefaultSensor(sensorType);
        } else{
            Log.e(TAG,"Sensor Type was 0!");
        }
        if (mSensor != null){
            mTextViewSensorName.setText(mSensor.getName());
        } else{
            mTextViewSensorName.setText("no sensor for this type available");
        }
        mTextViewSensorName.setText("Name: " + mSensor.getName());
        mTextViewValue.setText("Value: no value");
        mtextViewType.setText("Type: " + mSensor.getStringType());
        mtextViewMinDelay.setText("Min Delay: " + Integer.toString(mSensor.getMinDelay()));
        mtextViewMaxDelay.setText("Max Delay: " + Integer.toString(mSensor.getMaxDelay()));
        mtextViewMaxRange.setText("Max Range: " + Float.toString(mSensor.getMaximumRange()));
        // TODO now we should check how many values we get and what the unit is

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        Log.w(TAG, "onSensorChanged: " + event.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.w(TAG, "onAccuracyChanged: " + sensor.getName() + " changed to to " + Integer.toString(accuracy));
    }

}
