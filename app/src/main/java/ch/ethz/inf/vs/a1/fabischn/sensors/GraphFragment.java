package ch.ethz.inf.vs.a1.fabischn.sensors;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment implements GraphContainer {


    private final Handler mHandler = new Handler();
    private Runnable mTimer;
    private LineGraphSeries<DataPoint> mSeries;
    private double graph2LastXValue = 5d;
    private GraphView graph;
    private Viewport viewport;

    public GraphFragment() {
        // Required empty public constructor
    }

    //TODO write another constructor with arguments min max mValues etc.


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);

        graph = (GraphView) rootView.findViewById(R.id.graphview);
        viewport = graph.getViewport();
        viewport.setMinX(0.0);
        viewport.setMaxX(100.0);
        // TODO CONTINUE HERE
//        viewport.setMinY();
//        viewport.setMaxY();

        mSeries = new LineGraphSeries<>();
        graph.addSeries(mSeries);
        return rootView;
    }

    @Override
    public void onResume() {
        // TODO what comes here?
    }


    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer);
        super.onPause();
    }

    @Override
    public void addValues(double xIndex, float[] values) {

    }

    @Override
    public float[][] getValues() {
        return new float[0][];
    }
}
