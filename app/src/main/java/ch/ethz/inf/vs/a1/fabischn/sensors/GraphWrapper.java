package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Iterator;

/**
 * Created by fabian on 11.10.16.
 */

public class GraphWrapper implements GraphContainer {

    private final int MAX_ELEMENTS = 100;
    private GraphView mGraph;
    private LineGraphSeries<DataPoint>[] mSeries;
    private int mSensorValues = 0;
    private String mUnit;
    private int mDataCount = 0;
    private long mDelay = 0;
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

    public GraphWrapper(View root, int graphViewID, long delay){
        mGraph = (GraphView) root.findViewById(graphViewID);
        mDelay = delay;
    }

    public void initGraph(int sensorValues, String unitString, boolean setYAxis, double minY, double maxY) {
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
        GridLabelRenderer glr = mGraph.getGridLabelRenderer();

        vp.setXAxisBoundsManual(true);
        vp.setScalable(false);
        vp.setScalableY(false);
        vp.setScrollable(false);
        vp.setScrollableY(false);

          if (setYAxis){
              vp.setYAxisBoundsManual(true);
              vp.setMinY(minY);
              vp.setMaxY(maxY);
          }
        vp.setMinX(0); // use delay to calculate correct delta x
        // TODO Delay ... hmm
        vp.setMaxX(5);
        glr.setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        glr.setHorizontalAxisTitle("Time in seconds");
        glr.setNumHorizontalLabels(4);
        glr.setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, true) + " s";
                } else {
                    // show unit for y values
                    return super.formatLabel(value, false) + " " + mUnit;
                }
            }
        });
        glr.setLabelVerticalWidth(300); // Labels need enough space
        glr.reloadStyles();
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
        for (int i = 0; i < mDataCount; i++){
            values[i] = new float[mSensorValues];
        }
        DataPoint data;
        int i;
        int j = 0;
        for(LineGraphSeries s:mSeries){
            Iterator<DataPoint> it = s.getValues(s.getLowestValueX(), s.getHighestValueX());
            i = 0;
            while(it.hasNext()){
                data = it.next();
                values[i][j] = (float) data.getY();
                i++;
            }
            j++;
        }
        return values;
    }

}
