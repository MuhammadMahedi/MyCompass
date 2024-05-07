package com.example.mycompass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycompass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),SensorEventListener {

    lateinit var binding: ActivityMainBinding
    var currentDegree=0f
    var sensorManager : SensorManager? =null
    var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //always keeps the app in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sensorManager =getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree =Math.round(event?.values?.get(0)!!)
        binding.tvDegree.setText(degree.toString()+"  Degree")
        val rotateAnimation = RotateAnimation(currentDegree,(-degree).toFloat(), Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f)

        rotateAnimation.duration =210
        rotateAnimation.fillAfter = true

        binding.ivCompass.startAnimation(rotateAnimation)
        currentDegree= (-degree).toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}