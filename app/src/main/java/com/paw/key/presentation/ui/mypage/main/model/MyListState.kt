package com.paw.key.presentation.ui.mypage.main.model

import androidx.compose.runtime.Immutable
import com.paw.key.R
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class MyListState(
    val myList: List<MyList> = MyList.entries.toPersistentList(),
    val settingList: List<SettingList> = SettingList.entries.toPersistentList(),
)

enum class MyList(val title: String, val iconRes: Int) {
    MyPost("내가 기록한 산책", iconRes = R.drawable.ic_mypage_edit),
    MyComment("저장목록", iconRes = R.drawable.ic_mypage_heart),
    MyLike("내가 남긴 후기", iconRes = R.drawable.ic_mypage_mypost),
}

enum class SettingList(val title: String, val iconRes: Int) {
    ActiveRange(title = "활동 범위 설정", iconRes = R.drawable.ic_range_setting),
    AppInfo(title = "앱 정보", iconRes = R.drawable.ic_mypage_app_inf),
    Logout(title = "로그아웃", iconRes = R.drawable.ic_mypage_logout),
    Delete(title = "회원탈퇴", iconRes = R.drawable.ic_mypage_delete)
}