package com.paw.key.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PawKeyBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (sheetState: SheetState) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = PawKeyTheme.colors.background,
        modifier = modifier
            .fillMaxWidth(),
        dragHandle = null,
    ) {
        content(sheetState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PawKeyBottomSheetPreview() {
    PawKeyTheme {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        PawKeyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {}
        ) { }
    }
}