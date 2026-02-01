package com.paw.key.presentation.ui.mypage.courseinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.courseinfo.component.CourseRouteItem
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseData
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType


@Composable
fun CourseInfoRoute(
    courseType: CourseType,
    navigateUp: () -> Unit,
) {
    CourseInfoScreen(
        title = courseType.courseType,
        courses = emptyList(),
        navigateUp = navigateUp,
    )
}


@Composable
fun CourseInfoScreen(
    title: String,
    courses: List<CourseData>,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = PawKeyTheme.colors.background
            )
    ) {
        TopBar(
            title = title,
            onBackClick = navigateUp,
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = PawKeyTheme.colors.defaultButton
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(courses.size) { index ->
                val course = courses[index]
                CourseRouteItem(
                    location = course.location,
                    routeTitle = course.title,
                    routeImage = course.imageUrl,
                    routeDistance = course.distance,
                    routeTime = course.time,
                    routeDate = course.date,
                    modifier = Modifier
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CourseInfoScreenPreview() {
    PawKeyTheme {
        CourseInfoScreen(
            title = "",
            courses = emptyList(),
            navigateUp = { },
        )
    }
}