package com.paw.key.presentation.ui.course.walkcomplete

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walk.component.WalkRecordRow
import com.paw.key.presentation.ui.course.walkcomplete.component.WalkCompleteHeader
import com.paw.key.presentation.ui.course.walkcomplete.viewmodel.WalkCompleteViewModel

@Composable
fun WalkCompletionRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: WalkCompleteViewModel = hiltViewModel(),
) {
    val bitmap by viewModel.savedMapBitmap.collectAsStateWithLifecycle()

    WalkCompletionScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        bitmap = bitmap,
        modifier = modifier,
    )
}

@Composable
fun WalkCompletionScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "산책 완료",
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.head22B,
            modifier = modifier
                .align(Alignment.Start)
                .padding(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = "포비와 함께한 산책한 루트에요.",
            color = PawKeyTheme.colors.gray300,
            style = PawKeyTheme.typography.head18Sb,
            modifier = modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

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
                    .padding(top = 6.dp)
            )
        }

        WalkRecordRow(
            totalDistance = "2.2",
            totalTime = "30:00",
            currentSteps = 12345,
            modifier = modifier
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        PawkeyButton(
            text = stringResource(R.string.course_complete_button_text),
            onClick = navigateNext,
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}