package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private SensorManager mSensorManager = null;
    private List<Sensor> mSensors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_sensors);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        listView.setAdapter(new ArrayAdapter<Sensor>(this,R.layout.listview_item,mSensors));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent sensorIntent = new Intent(this, SensorActivity.class);
        Sensor sensor = (Sensor) parent.getAdapter().getItem(position);
        String sensorName = sensor.getName();
        sensorIntent.putExtra(Intent.EXTRA_TEXT, sensorName);
        startActivity(sensorIntent);
    }
}



