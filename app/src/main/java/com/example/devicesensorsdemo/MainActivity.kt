package com.example.devicesensorsdemo

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var x = 0.0f
    private var y = 0.0f
    private var z = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        startSensor()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
            updateTextViews()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Not used
    }

    private fun startSensor() {
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            // The device does not have a accelerometer sensor
            Toast.makeText(this, "No accelerometer sensor found", Toast.LENGTH_SHORT).show()
        } else {
            // Register the sensor listener
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun updateTextViews() {
        val xTextView: TextView = findViewById(R.id.x_axis)
        val yTextView: TextView = findViewById(R.id.y_axis)
        val zTextView: TextView = findViewById(R.id.z_axis)
        xTextView.text = "X-Axis: $x"
        yTextView.text = "Y-Axis: $y"
        zTextView.text = "Z-Axis: $z"
    }
}