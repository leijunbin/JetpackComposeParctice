package com.example.jetpackcomposedemo1

object TimeFormatUtils {
    fun formatTime(time: Long): String{
        var value = time
        val seconds = value % 60
        value /= 60
        val minutes = value % 60
        value /= 60
        val hours = value % 60
        return hours.toString() + ":" + minutes.toString() + ":" +seconds.toString()
    }
}