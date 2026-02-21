package com.paw.key.presentation.ui.course.walkcourse.walkprepare.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPrepareItemModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun WalkPrepareBody(
    modifier: Modifier = Modifier,
    addWalkItem : () -> Unit = {},
    deleteWalkItem : (Int) -> Unit = {},
    itemList : ImmutableList<WalkPrepareItemModel> = persistentListOf(),
    lastAddedItemId: Int? = null,
    onFocusHandled: () -> Unit = {}
) {
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(itemList.size) {
        if (itemList.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(itemList.lastIndex)
            }
        }
    }

    Column (
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = PawKeyTheme.colors.background
            )
            .padding(16.dp)
    ) {
        Text(
            text = "산책 필수템",
            style = PawKeyTheme.typography.subTitle,
            color = PawKeyTheme.colors.defaultDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            state = listState
        ) {
            itemsIndexed(
                items = itemList,
                key = { _, item -> item.id }
            ) { index, item ->
                Column {
                    WalkPrepareItem(
                        itemModel = item,
                        isSelected = selectedIds.contains(item.id),
                        onCheckBoxClick = { isSelectedNew ->
                            selectedIds = if (isSelectedNew) {
                                selectedIds + item.id
                            } else {
                                selectedIds - item.id
                            }
                        },
                        deleteWalkItem = deleteWalkItem,
                        shouldFocus = lastAddedItemId == item.id,
                        onFocusHandled = onFocusHandled
                    )

                    if (index < itemList.lastIndex) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = PawKeyTheme.colors.defaultBright
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = addWalkItem)
                .background(
                    color = PawKeyTheme.colors.primaryGra1,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = "+ 추가하기",
                style = PawKeyTheme.typography.subButtonActive,
                color = PawKeyTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun WalkPrepareItem(
    isSelected : Boolean,
    onCheckBoxClick: (Boolean) -> Unit,
    deleteWalkItem : (Int) -> Unit,
    itemModel: WalkPrepareItemModel,
    modifier: Modifier = Modifier,
    shouldFocus: Boolean = false,
    onFocusHandled: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val textStyle = when {
        isSelected -> PawKeyTheme.typography.subButtonActive
            .copy(color = PawKeyTheme.colors.defaultDark)
        else -> PawKeyTheme.typography.subButtonDefault
            .copy(color = PawKeyTheme.colors.defaultMiddle)
    }

    LaunchedEffect(shouldFocus) {
        if (shouldFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
            onFocusHandled()
        }
    }

    Row (
        modifier = modifier
            .noRippleClickable {
                onCheckBoxClick(!isSelected)
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckBox(
            isSelected = isSelected,
            onCheckClick = onCheckBoxClick,
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            state = itemModel.walkItem,
            textStyle = textStyle,
            decorator = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (itemModel.walkItem.text.isEmpty()) {
                        Text(
                            text = "준비물을 작성해주세요",
                            style = PawKeyTheme.typography.subButtonDefault,
                            color = PawKeyTheme.colors.defaultMiddle
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .noRippleClickable(onClick = {
                    deleteWalkItem(itemModel.id)
                })
        )
    }
}

@Composable
private fun CustomCheckBox(
    isSelected: Boolean,
    onCheckClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkMarkTint = if (isSelected) {
        PawKeyTheme.colors.background
    } else {
        PawKeyTheme.colors.defaultMiddle
    }

    val checkBackground = if (isSelected) {
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.defaultBright
    }

    Box(
        modifier = modifier
            .size(15.dp)
            .background(
                color = checkBackground,
                shape = RoundedCornerShape(1.dp)
            )
            .noRippleClickable {
                onCheckClick(!isSelected)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_walk_course_check),
            contentDescription = null,
            tint = checkMarkTint,
        )
    }
}

@Preview
@Composable
private fun WalkPrepareBodyPreview() {
    PawKeyTheme {
        WalkPrepareBody(
            itemList = WalkPrepareState().dummyWalkPrepare
        )
    }
}
