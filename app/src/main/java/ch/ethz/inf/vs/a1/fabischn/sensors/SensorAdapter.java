package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fabian on 09.10.16.
 */

// Ispired by http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
public class SensorAdapter extends ArrayAdapter<Sensor> {

    public SensorAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public SensorAdapter(Context context, int resource, List<Sensor> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listview_item, null);
        }

        Sensor s = getItem(position);

        if (s != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.textview_sensor_name);
            TextView tt2 = (TextView) v.findViewById(R.id.textview_sensor_type);

            if (tt1 != null) {
                tt1.setText("Sensor name: " + s.getName());
            }

            if (tt2 != null) {
                tt2.setText("Type: " + s.getType() + ": " + s.getStringType());
            }
        }

        return v;
    }

}