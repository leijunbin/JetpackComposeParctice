package com.example.jetpackcomposedemo1

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo1.model.TimerViewModel
import com.example.jetpackcomposedemo1.ui.theme.JetpackComposeDemo1Theme
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDemo1Theme {
                MyApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.animatorController.stop()
    }
}

@Composable
private fun MyApp(){
    val viewModel: TimerViewModel = viewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            }) 
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompletedText(viewModel = viewModel)
            TimeLeftText(viewModel = viewModel)
            ProgressCircle(viewModel = viewModel)
            EditText(viewModel = viewModel)
            Row{
               StartButton(viewModel = viewModel)
               StopButton(viewModel = viewModel)
            }
        }
    }
}


@Composable
private fun TimeLeftText(viewModel: TimerViewModel){
    Text(
        text = TimeFormatUtils.formatTime(viewModel.timeLeft),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun EditText(viewModel: TimerViewModel){
    Box (
        modifier = Modifier
            .size(300.dp, 120.dp),
        contentAlignment = Alignment.Center
    ){
        if(viewModel.status.showEditText()){
            TextField(
                modifier = Modifier.size(200.dp, 60.dp),
                value = if(viewModel.totalTime == 0L) "" else viewModel.totalTime.toString(),
                onValueChange = viewModel::updateValue,
                label = { Text(text = "Countdown Seconds")},
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
private fun StartButton(viewModel: TimerViewModel){
    Button(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        enabled = viewModel.totalTime > 0,
        onClick = viewModel.status::clickStartButton
    ) {
       Text(text = viewModel.status.startButtonDisplayString())
    }
}

@Composable
private fun StopButton(viewModel: TimerViewModel){
    Button(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        enabled = viewModel.status.stopButtonEnabled(),
        onClick = viewModel.status::clickStopButton
    ) {
        Text(text = "Stop")
    }
}

@Composable
private fun ProgressCircle(viewModel: TimerViewModel){
    val size = 160.dp
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val sweepAngle = viewModel.status.progressSweepAngle()
            val r = size.toPx() / 2
            val strokeWidth = 12.dp.toPx()
            
            drawCircle(
                color = Color.LightGray,
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(1.dp.toPx(), 3.dp.toPx())
                    )
                )
            )

            drawArc(
                brush = Brush.sweepGradient(
                    0f to Color.Magenta,
                    0.25f to Color.Blue,
                    0.75f to Color.Green,
                    0.75f to Color.Transparent,
                    1f to Color.Magenta
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth
                ),
                alpha = 0.5f
            )
            val angle = (360 - sweepAngle) / 180 * Math.PI
            val pointTailLength = 8.dp.toPx()
            drawLine(
                color = Color.Red,
                start = Offset(r + pointTailLength * sin(angle).toFloat(), r + pointTailLength * cos(angle).toFloat()),
                end = Offset((r - r * sin(angle) - sin(angle) * strokeWidth / 2).toFloat(), (r - r * cos(angle) - cos(angle) * strokeWidth / 2).toFloat()),
                strokeWidth = 2.dp.toPx()
            )
            drawCircle(
                color = Color.Red,
                radius = 5.dp.toPx()
            )
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx()
            )
        }
    }
}

@Composable
private fun CompletedText(viewModel: TimerViewModel){
    Text(
        text = viewModel.status.completedString(),
        color = MaterialTheme.colors.primary
    )
}


