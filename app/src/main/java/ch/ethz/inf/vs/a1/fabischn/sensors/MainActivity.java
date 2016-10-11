package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private List<Sensor> mSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview_sensors);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        listView.setAdapter(new SensorAdapter(this, R.layout.listview_item, mSensors));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent sensorIntent = new Intent(this, SensorActivity.class);
        Sensor sensor = (Sensor) parent.getAdapter().getItem(position);
        sensorIntent.putExtra("sensor_index", sensor.getType());
        startActivity(sensorIntent);
    }


}



