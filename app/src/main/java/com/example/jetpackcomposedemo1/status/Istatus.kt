package com.example.jetpackcomposedemo1.status

interface Istatus {
    fun startButtonDisplayString(): String
    fun clickStartButton()
    fun stopButtonEnabled(): Boolean
    fun clickStopButton()
    fun showEditText(): Boolean
    fun progressSweepAngle(): Float
    fun completedString(): String
}