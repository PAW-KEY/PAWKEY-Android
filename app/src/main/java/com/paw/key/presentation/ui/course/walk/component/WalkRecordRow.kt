package com.paw.key.presentation.ui.course.walk.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.DistanceRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.StepsRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.TimeRecord

@Composable
fun WalkRecordRow(
    totalDistance: String,
    totalTime: String,
    currentSteps: Int,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .padding(top = 16.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.green500,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val recordItems = listOf(
            DistanceRecord,
            TimeRecord,
            StepsRecord,
        )

        recordItems.forEach { record ->
            when (record) {
                DistanceRecord -> WalkRecordItem(
                    recordTitle = record.titleResId,
                    recordContent = totalDistance,
                    modifier = Modifier
                        .weight(1f),
                )

                TimeRecord -> WalkRecordItem(
                    recordTitle = record.titleResId,
                    recordContent = totalTime,
                    modifier = Modifier
                        .weight(1f),
                )

                StepsRecord -> WalkRecordItem(
                    recordTitle = record.titleResId,
                    recordContent = currentSteps.toString(),
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }
    }
}