package com.example.jetpackcomposedemo1.status

import com.example.jetpackcomposedemo1.model.TimerViewModel

class PausedStatus(private val viewModel: TimerViewModel) : Istatus {
    override fun startButtonDisplayString() = "Resume"
    override fun clickStartButton() = viewModel.animatorController.resume()
    override fun stopButtonEnabled() = true
    override fun clickStopButton() = viewModel.animatorController.stop()
    override fun showEditText() = false
    override fun progressSweepAngle() = viewModel.animValue / viewModel.totalTime * 360
    override fun completedString() = ""
}