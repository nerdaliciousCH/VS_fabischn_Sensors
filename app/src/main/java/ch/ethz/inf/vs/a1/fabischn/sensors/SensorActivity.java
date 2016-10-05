package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity {

    private TextView mTextViewSensorName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mTextViewSensorName = (TextView) findViewById(R.id.text_sensor);
        Intent intent = getIntent();
        String sensorName = intent.getStringExtra(Intent.EXTRA_TEXT);
        mTextViewSensorName.setText(sensorName);
    }

    public GraphContainer getGraphContainer(){
        return null;
    }
}
