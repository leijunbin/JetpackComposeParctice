package com.example.jetpackcomposedemo1.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.ViewAnimator
import androidx.core.animation.addListener
import com.example.jetpackcomposedemo1.model.TimerViewModel
import com.example.jetpackcomposedemo1.status.CompletedStatus
import com.example.jetpackcomposedemo1.status.NotStartedStatus
import com.example.jetpackcomposedemo1.status.PausedStatus
import com.example.jetpackcomposedemo1.status.StartedStatus

class AnimatorController(private val viewModel: TimerViewModel) {
    companion object{
        const val SPEED = 100
    }
    private var valueAnimator: ValueAnimator ?= null
    fun start(){
        if(viewModel.totalTime == 0L)
            return
        if(valueAnimator == null){
            valueAnimator = ValueAnimator.ofInt(viewModel.totalTime.toInt() * SPEED, 0)
            valueAnimator?.apply{
                interpolator = LinearInterpolator()
                addUpdateListener {
                    viewModel.animValue = (it.animatedValue as Int) / SPEED.toFloat()
                    viewModel.timeLeft = (it.animatedValue as Int).toLong() / SPEED
                }
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        complete()
                    }
                })
            }
        }else{
            valueAnimator?.setIntValues(viewModel.totalTime.toInt() * SPEED, 0)
        }
        valueAnimator?.duration = viewModel.totalTime * 1000L
        valueAnimator?.start()
        viewModel.status = StartedStatus(viewModel)
    }
    fun pause(){
        valueAnimator?.pause()
        viewModel.status = PausedStatus(viewModel)
    }
    fun resume(){
        valueAnimator?.resume()
        viewModel.status = StartedStatus(viewModel)
    }
    fun stop(){
        valueAnimator?.cancel()
        viewModel.timeLeft = 0
        viewModel.status = NotStartedStatus(viewModel)
    }
    fun complete(){
        viewModel.totalTime = 0
        viewModel.status = CompletedStatus(viewModel)
    }
}