package com.example.mutualfundexplorationandtrackingplatform.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.NavPoint
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NavLineChart(
    navData: List<NavPoint>,
    modifier: Modifier = Modifier
) {
    if (navData.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", color = Color.Gray)
        }
        return
    }

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val monthYearFormatter = SimpleDateFormat("MMM'\n'yyyy", Locale.getDefault())
    val reversedData = navData.reversed()

    val sampledData = remember(reversedData) {
        val targetPoints = 6
        if (reversedData.size <= targetPoints) {
            reversedData
        } else {
            val step = reversedData.size / (targetPoints - 1)
            buildList {
                for (i in 0 until targetPoints - 1) {
                    add(reversedData[i * step])
                }
                add(reversedData.last())
            }
        }
    }

    val chartEntries = sampledData.mapIndexed { index, point ->
        FloatEntry(
            x = index.toFloat(),
            y = point.nav.toFloatOrNull() ?: 0f
        )
    }

    val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        val index = value.toInt()
        if (index in sampledData.indices) {
            val date = dateFormatter.parse(sampledData[index].date)
            monthYearFormatter.format(date ?: Date())
        } else ""
    }

    val startAxisValueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
        String.format("%.1f", value)
    }

    val axisLabelComponent = textComponent {
        color = Color.Black.toArgb()
        textSizeSp = 10f
        lineCount = 2
    }

    ProvideChartStyle {
        val defaultStyle = currentChartStyle
        val lineSpec = remember(defaultStyle) {
            defaultStyle.lineChart.lines.first().copy(
                lineColor = Color.Blue.toArgb(),
                lineThicknessDp = 3f,
                point = LineComponent(
                    color = Color.Blue.toArgb(),
                    thicknessDp = 10f,
                    shape = Shapes.pillShape
                ),
                pointSizeDp = 10f
            )
        }

        Chart(
            chart = lineChart(lines = listOf(lineSpec)),
            model = entryModelOf(chartEntries),
            startAxis = rememberStartAxis(
                label = axisLabelComponent,
                valueFormatter = startAxisValueFormatter,
                tick = LineComponent(
                    color = Color.Gray.toArgb(),
                    thicknessDp = 1f
                ),
                guideline = LineComponent(
                    color = Color.LightGray.copy(alpha = 0.3f).toArgb(),
                    thicknessDp = 0.5f
                ),
                axis = LineComponent(
                    color = Color.Black.toArgb(),
                    thicknessDp = 1.5f
                )
            ),
            bottomAxis = rememberBottomAxis(
                label = axisLabelComponent,
                valueFormatter = bottomAxisValueFormatter,
                tick = LineComponent(
                    color = Color.Gray.toArgb(),
                    thicknessDp = 1f
                ),
                guideline = null,
                axis = LineComponent(
                    color = Color.Black.toArgb(),
                    thicknessDp = 1.5f
                )
            ),
            chartScrollSpec = rememberChartScrollSpec(
                isScrollEnabled = false
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(start = 40.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}
