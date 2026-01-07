package com.example.cigarrotracker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun SimpleLineChart(
    values: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    gridColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
) {
    val safeValues = if (values.isEmpty()) listOf(0f) else values
    val maxValue = max(0f, safeValues.maxOrNull() ?: 0f)
    val denom = if (maxValue == 0f) 1f else maxValue

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val gridLines = 3
        for (i in 1..gridLines) {
            val y = height * i / (gridLines + 1)
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        if (safeValues.size == 1) {
            val y = height - (safeValues[0] / denom) * height
            drawLine(
                color = lineColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
            return@Canvas
        }

        val stepX = width / (safeValues.size - 1)
        val path = Path()
        safeValues.forEachIndexed { index, value ->
            val x = stepX * index
            val y = height - (value / denom) * height
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
fun SimpleBarChart(
    values: List<Float>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.tertiary,
    gridColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
) {
    val safeValues = if (values.isEmpty()) listOf(0f) else values
    val maxValue = max(0f, safeValues.maxOrNull() ?: 0f)
    val denom = if (maxValue == 0f) 1f else maxValue

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val gridLines = 3
        for (i in 1..gridLines) {
            val y = height * i / (gridLines + 1)
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        val count = safeValues.size
        if (count == 0) return@Canvas
        val step = width / count
        val barWidth = step * 0.6f
        val radius = 6.dp.toPx()

        safeValues.forEachIndexed { index, value ->
            val barHeight = (value / denom) * height
            val left = step * index + (step - barWidth) / 2f
            val top = height - barHeight
            drawRoundRect(
                color = barColor,
                topLeft = Offset(left, top),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(radius, radius)
            )
        }
    }
}

@Composable
fun ChartLabelsRow(
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        labels.forEach { label ->
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
