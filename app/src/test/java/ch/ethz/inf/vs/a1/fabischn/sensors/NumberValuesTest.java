package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.hardware.Sensor;

import org.junit.Before;
import org.junit.Test;

import ch.ethz.inf.vs.a1.fabischn.sensors.SensorTypes;
import ch.ethz.inf.vs.a1.fabischn.sensors.SensorTypesImpl;

import static org.junit.Assert.assertEquals;

public class NumberValuesTest {

    SensorTypes mSensorTypes;

    @Before
    public void setupBeforeClass(){
        mSensorTypes = new SensorTypesImpl();
    }

    @Test
    public void accelerometerTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_ACCELEROMETER));
    }

    @Test
    public void ambientTemperatureTest() throws Exception {
        assertEquals(1, mSensorTypes.getNumberValues(Sensor.TYPE_AMBIENT_TEMPERATURE));
    }

    @Test
    public void gravityTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_GRAVITY));
    }

    @Test
    public void gyroscopeTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_GYROSCOPE));
    }

    @Test
    public void lightTest() throws Exception {
        assertEquals(1, mSensorTypes.getNumberValues(Sensor.TYPE_LIGHT));
    }

    @Test
    public void linearAccelerationTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_LINEAR_ACCELERATION));
    }

    @Test
    public void magneticFieldTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_MAGNETIC_FIELD));
    }

    @Test
    public void PressureTest() throws Exception {
        assertEquals(1, mSensorTypes.getNumberValues(Sensor.TYPE_PRESSURE));
    }

    @Test
    public void RelativeHumidityTest() throws Exception {
        assertEquals(1, mSensorTypes.getNumberValues(Sensor.TYPE_RELATIVE_HUMIDITY));
    }

    @Test
    public void RotationVectorTest() throws Exception {
        assertEquals(3, mSensorTypes.getNumberValues(Sensor.TYPE_ROTATION_VECTOR));
    }
}