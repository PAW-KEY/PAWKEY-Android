package com.paw.key.presentation.ui.course.walkcourse.walkprepare.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun WalkPrepareBody(
    modifier: Modifier = Modifier,
    itemList : ImmutableList<WalkPrepareItemModel> = persistentListOf(),
) {
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }

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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = itemList,
                key = { _, item -> item.id }
            ) { index, item ->
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
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
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
    itemModel: WalkPrepareItemModel,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .background(
                color = if (isSelected) {
                    PawKeyTheme.colors.primary
                } else {
                    PawKeyTheme.colors.defaultButton
                },
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable {
                onCheckBoxClick(!isSelected)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckBox(
            isSelected = isSelected,
            onCheckClick = onCheckBoxClick,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = itemModel.walkItem,
            style = PawKeyTheme.typography.subButtonDefault,
            color = if (isSelected) {
                PawKeyTheme.colors.background
            } else {
                PawKeyTheme.colors.defaultMiddle
            },
            textAlign = TextAlign.Start
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
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.defaultButton
    }

    Box(
        modifier = modifier
            .size(15.dp)
            .background(
                color = PawKeyTheme.colors.background,
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