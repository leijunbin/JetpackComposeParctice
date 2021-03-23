package com.example.jetpackcomposedemo1.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposedemo1.animator.AnimatorController
import com.example.jetpackcomposedemo1.status.Istatus
import com.example.jetpackcomposedemo1.status.NotStartedStatus

class TimerViewModel: ViewModel() {
    var totalTime: Long by mutableStateOf(0)
    var timeLeft: Long by mutableStateOf(0)

    companion object{
        const val MAX_INPUT_LENGTH = 5
    }

    fun updateValue(text: String) {
        if(text.length > MAX_INPUT_LENGTH)
            return
        var value = text.replace("\\D".toRegex(), "")
        if(value.startsWith("0"))
            value = value.substring(1)
        if(value.isBlank())
            value = "0"
        totalTime = value.toLong()
        timeLeft = value.toLong()
    }
    var animatorController = AnimatorController(this)
    var status: Istatus by mutableStateOf(NotStartedStatus(this))
    var animValue: Float by mutableStateOf(0.0f)
}

