package ch.ethz.inf.vs.a1.fabischn.sensors;

import android.hardware.Sensor;

/**
 * Created by fabian on 08.10.16.
 */

/**
 * Got the sensors units and values from
 * https://developer.android.com/guide/topics/sensors/sensors_position.html
 * https://developer.android.com/guide/topics/sensors/sensors_environment.html
 * https://developer.android.com/guide/topics/sensors/sensors_motion.html
 * https://developer.android.com/reference/android/hardware/SensorEvent.html#values
 * https://source.android.com/devices/sensors/sensor-types.html
 */

public class SensorTypesImpl implements SensorTypes {

    private final static String SQUARE = "&amp;#xB2;";
    private final static String DEGREE = "°";
    private final static String MICRO = "&amp;#xB5;";

    @Override
    public int getNumberValues(int sensorType) {
        switch (sensorType){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_PRESSURE:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_MOTION_DETECT:
            case Sensor.TYPE_HEART_BEAT:
            case Sensor.TYPE_STATIONARY_DETECT:
            case Sensor.TYPE_STEP_DETECTOR:
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_HEART_RATE:
                return 1;
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
            case Sensor.TYPE_ORIENTATION: // Legacy, e.g. Galaxy S7 Edge... :D
                return 3;
            case Sensor.TYPE_POSE_6DOF:
//                return 15; // ? Only reference found mentioned values[0-14]
            default:
                return 0;
        }
    }

    @Override
    public String getUnitString(int sensorType) {
        switch (sensorType){
            // common sensors
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_LINEAR_ACCELERATION:
//                return "m/s"+SQUARE;
                return "m/s^2";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
//                return DEGREE + "C";
                return "°C";
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "rad/s";
            case Sensor.TYPE_LIGHT:
                return "lx";
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
//                return MICRO+"T";
                return "microT";
            case Sensor.TYPE_PRESSURE:
                return "hPa";
            case Sensor.TYPE_PROXIMITY:
                return "cm";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "%";
            case Sensor.TYPE_STEP_COUNTER:
                return "Steps";
            case Sensor.TYPE_HEART_RATE:
                return "bpm";
            case Sensor.TYPE_ORIENTATION: // Legacy, e.g. Galaxy S7 Edge... :D
                return "°";
            case Sensor.TYPE_STEP_DETECTOR:
            case Sensor.TYPE_SIGNIFICANT_MOTION:
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_POSE_6DOF:
            case Sensor.TYPE_HEART_BEAT:
            case Sensor.TYPE_STATIONARY_DETECT:
            case Sensor.TYPE_MOTION_DETECT:

            default:
                return "no unit";


        }
    }
}
