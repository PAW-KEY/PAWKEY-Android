package com.paw.key.presentation.ui.course.util// StepCounter.kt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

// 걸음 수 변경을 외부에 알리기 위한 인터페이스
interface StepCountListener {
    fun onStepCountChanged(sessionSteps: Long)
    fun onSensorNotFound()
}

@Composable
fun rememberStepCounter(): StepCounter {
    val context = LocalContext.current

    val stepCounter = remember {
        StepCounter(context)
    }

    DisposableEffect(Unit) {
        onDispose {
            stepCounter.deactivate()
        }
    }

    return stepCounter
}

class StepCounter(context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private var listener: StepCountListener? = null
    private var isListening = false

    fun setStepCountListener(listener: StepCountListener) {
        this.listener = listener
    }

    fun activate() {
        if (stepCounterSensor == null) {
            Log.e("StepCounter", "걸음 수 측정 센서를 찾을 수 없습니다.")
            listener?.onSensorNotFound()
            return
        }
        if (!isListening) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
            isListening = true
            Log.d("StepCounter", "걸음 수 측정 리스너 등록")
        }
    }

    fun deactivate() {
        if (isListening) {
            sensorManager.unregisterListener(this)
            isListening = false
            Log.d("StepCounter", "걸음 수 측정 리스너 해제")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val totalStepsFromSensor = event.values[0].toLong()
            listener?.onStepCountChanged(totalStepsFromSensor)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}