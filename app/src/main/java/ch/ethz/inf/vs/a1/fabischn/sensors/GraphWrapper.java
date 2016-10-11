package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Iterator;

/**
 * Created by fabian on 11.10.16.
 */

public class GraphWrapper implements GraphContainer {

    private Activity mActivity;
    private final int MAX_ELEMENTS = 100;
    private GraphView mGraph;
    private LineGraphSeries<DataPoint>[] mSeries;
    private int mSensorValues = 0;
    private String mUnit;
    private int mDataCount = 0;
    private int[] mColors = {Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.BLACK,
            Color.CYAN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.DKGRAY
            };
    private final int mColorCount = 8;

    public GraphWrapper(Activity activity, View root, int graphViewID){
        mActivity = activity;
        mGraph = (GraphView) root.findViewById(graphViewID);
    }

    public void initGraph(int sensorValues, String unitString) {
        mSensorValues = sensorValues;
        mUnit = unitString;
        mSeries = new LineGraphSeries[sensorValues];

        for(int i = 0; i < sensorValues;i++){
            mSeries[i] = new LineGraphSeries<>();
            int color = mColors[i%mColorCount];
            //Paint paint = new Paint();
            mSeries[i].setDrawBackground(true);
            //mSeries[i].setDrawDataPoints(true);
            //mSeries[i].setBackgroundColor();
            mSeries[i].setColor(color);
            mGraph.addSeries(mSeries[i]);
        }
        Viewport vp = mGraph.getViewport();
        vp.setXAxisBoundsManual(false);
        vp.setMinX(0);
        GridLabelRenderer glr = mGraph.getGridLabelRenderer();
        glr.setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        glr.setHorizontalAxisTitle("Time");
        glr.setNumHorizontalLabels(2);
        glr.setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, true) + " sec";
                } else {
                    // show unit for y values
                    return super.formatLabel(value, false) + " " + mUnit;
                }
            }
        });

        //mGraph.getGridLabelRenderer().setLabelVerticalWidth(100);
        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(mActivity));
    }


    @Override
    public void addValues(double xIndex, float[] values) {
        for (int i = 0; i < mSensorValues; i++){
            mSeries[i].appendData(new DataPoint(xIndex, values[i]), true, MAX_ELEMENTS);
        }
        mGraph.onDataChanged(true,true);
        mDataCount++;
    }

    @Override
    public float[][] getValues() {
        float [][] values = new float[mDataCount][];
        for(float[] f:values){
            f = new float[mSensorValues];
        }
        DataPoint data;
        int i = 0;
        int j = 0;
        for(LineGraphSeries s:mSeries){
            Iterator<DataPoint> it = s.getValues(s.getLowestValueX(), s.getHighestValueX());
            i++;
            while(it.hasNext()){
                data = it.next();
                values[i][j] = (float) data.getY();
                j++;
            }
        }
        return values;
    }

}
