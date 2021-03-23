package com.example.jetpackcomposedemo1.status

import com.example.jetpackcomposedemo1.model.TimerViewModel

class CompletedStatus(private val viewModel: TimerViewModel) : Istatus {
    override fun startButtonDisplayString() = "Start"
    override fun clickStartButton() = viewModel.animatorController.start()
    override fun stopButtonEnabled() = false
    override fun clickStopButton() {}
    override fun showEditText() = true
    override fun progressSweepAngle() = 0f
    override fun completedString() = "Completed!"
}