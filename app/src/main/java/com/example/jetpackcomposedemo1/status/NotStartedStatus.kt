package com.example.jetpackcomposedemo1.status

import com.example.jetpackcomposedemo1.model.TimerViewModel

class NotStartedStatus(private val viewModel: TimerViewModel) : Istatus{
    override fun startButtonDisplayString() = "Start"
    override fun clickStartButton() = viewModel.animatorController.start()
    override fun stopButtonEnabled() = false
    override fun clickStopButton() {}
    override fun showEditText() = true
    override fun progressSweepAngle() = if(viewModel.totalTime > 0) 360f else 0f
    override fun completedString() = ""
}