package com.paw.key.presentation.ui.course.walkcomplete

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.course.walk.component.WalkRecordRow
import com.paw.key.presentation.ui.course.walk.formatDistance
import com.paw.key.presentation.ui.course.walk.formatTime
import com.paw.key.presentation.ui.course.walkcomplete.component.WalkCompleteHeader
import com.paw.key.presentation.ui.course.walkcomplete.component.WalkCompletionRecordRow
import com.paw.key.presentation.ui.course.walkcomplete.viewmodel.WalkCompleteViewModel

@Composable
fun WalkCompletionRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WalkCompleteViewModel = hiltViewModel(),
    isSharedWalk : Boolean = false
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val walkRecordList = listOf(R.string.course_record_distance, R.string.course_record_time, R.string.course_record_step)

    LaunchedEffect(Unit) {
        viewModel.loadWalkResult()
        // 디버깅용
        viewModel.debugRepositoryState()
    }

    WalkCompletionScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        bitmap = state.bitmap,
        walkRecordList = walkRecordList,
        totalDistance = state.totalDistance,
        totalTime = state.totalTime,
        totalSteps = state.totalSteps,
        isSharedWalk = isSharedWalk,
        modifier = modifier,
    )
}

@Composable
fun WalkCompletionScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    bitmap: Bitmap?,
    walkRecordList: List<Int>,
    totalDistance: Float,
    totalTime: Long,
    totalSteps: Int,
    isSharedWalk: Boolean,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .background(PawKeyTheme.colors.white2),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "포비와 함께한 산책한 루트에요.",
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.head20B2,
            modifier = modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Column (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .background(
                    color = PawKeyTheme.colors.white1,
                    shape = RoundedCornerShape(12.dp)
                )
        ){
            WalkCompleteHeader(
                bitmap = null,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            bitmap?.asImageBitmap()?.let {
                Image(
                    bitmap = it,
                    contentDescription = "My Image",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 6.dp)
                )
            }

            HorizontalDivider(
                thickness = 3.dp,
                color = PawKeyTheme.colors.gray50,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 10.dp)
            )

            WalkCompletionRecordRow(
                totalDistance = formatDistance(totalDistance),
                totalTime = formatTime(totalTime),
                currentSteps = totalSteps,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        val buttonTextRes = if (isSharedWalk) {
            R.string.course_shared_complete_button_text
        } else {
            R.string.course_complete_button_text
        }

        Spacer(modifier = Modifier.weight(1f))

        PawkeyButton(
            text = stringResource(buttonTextRes),
            onClick = navigateNext,
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 60.dp)
        )
    }
}