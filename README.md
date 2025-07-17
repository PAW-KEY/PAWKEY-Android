# PAWKEY
### 36th AT SOPT - PAWKEY 안드로이드 레포지토리   `📅 2025.06.21 ~ ing 📅`
<br>

##  PAWKEY
**반려동물과 보호자의 일상적인 산책을 더 다채롭고 즐거운 경험으로 바꿔주는 위치 기반 산책 큐레이션 플랫폼**


### **📍 1. 지역 기반 추천 루트 탐색**

내 위치와 선호 조건에 따라 **인기 산책 루트**를 추천받아요.

풍경이 좋은 길, 카페를 지나는 길, 공원 중심 루트 등 **다양한 스타일의 산책 코스**를 탐험할 수 있어요.

괜찮은 루트는 ‘좋아요’를 눌러 저장해두고, 나중에 다시 꺼내볼 수 있어요.

---

### **🏃‍♂️ 2. 나만의 산책 루트 기록 & 리뷰**

산책을 시작하면 **GPS로 자동 기록**되고,

산책 후에는 루트에 대한 소감 + 체크리스트(안전성, 경치, 편의성 등)를 선택해 리뷰를 남길 수 있어요.

공유하거나 **비공개로 아카이빙**해둘 수도 있어서 유연하게 사용할 수 있습니다.

---

### **👯 3. 실시간 친구와 산책 연결**

**친구 추가 기능**을 통해 내 지인이나 이웃의 **실시간 산책 상태**를 확인할 수 있어요.

같은 시간에 산책 중이라면 **함께 걷자고 제안**할 수도 있어요.

함께 산책하면 루틴도 즐겁고, 소셜 경험도 생깁니다

---



<br><br>

## 🖥️ Contributors
| 👑[손민성](https://github.com/sonms) | [손주완](https://github.com/vvan2) | [송지우](https://github.com/JiWoo1261) |
|:------------------------------------:|:-------------------------:|:------------------------------:|
| <img src="https://github.com/user-attachments/assets/768c5d45-7bf4-4f6d-89a3-760d210a0f48" alt="" width="210"/> | <img src="https://github.com/user-attachments/assets/beee5163-e3bf-4381-b341-1848845ed49a" alt="" width="210"/> | <img src="https://github.com/user-attachments/assets/004db389-122d-4a8b-9974-3edce104ee61" alt="" width="210"/> |
|`트랙킹`  `산책기록하기`  `산책 탭`<br> `루트 상세보기`  `공유루트`  `후기 작성`| `스플래시`  `온보딩`  `회원가입/정보입력`<br>`로그인` `홈`  `옵션선택/경로추천`  | `마이페이지`  `저장한 산책 루트` <br> `유저/반려견 프로필`  `내가 기록한 산책 루트`        |

<br>

## 🛠️ Tech Stacks
| **Title**                 | **Content**                                      |
|--------------------------|--------------------------------------------------|
| **Architecture**         | Google Recommended Architecture, MVVM           |
| **Module**               | Single Activity Architecture                     |
| **UI Framework**         | Jetpack Compose                                 |
| **Dependency Injection** | Hilt                                             |
| **Navigation**           | Jetpack Navigation                              |
| **Network**              | Retrofit2, OkHttp                               |
| **Asynchronous Processing** | Coroutine, Flow                             |


> 🛠 **Tech Stack 소개** <br><br>

1️⃣ **Architecture: Google Recommended Architecture, MVVM**<br>
뷰 로직과 비즈니스 로직을 분리하여 생산성, 가독성을 높힐 수 있습니다.또한 Jetpack와 Hilt와의 높은 호환성.<br><br>

2️⃣ **Dependency Injection: Hilt**<br>
구글이 공식 지원하는 Hilt로 보일러플레이트 코드를 최소화하면서도 강력한 DI 기능을 제공합니다. Android 컴포넌트들과의 완벽한 통합으로 테스트 가능한 코드베이스를 구축했습니다.<br><br>

3️⃣ **Network: Retrofit2, OkHttp**<br>
Retrofit2의 선언적 API 정의와 OkHttp의 강력한 인터셉터 기능으로 안정적이고 효율적인 네트워크 계층을 구현했습니다.<br>

<br>



## 💡 Convention
#### 🐾 Git Convention
[Git Convention](https://shadow-impatiens-f13.notion.site/Git-Convention-222564d8d2a780aa9050f7a55fb93e0e?source=copy_link)

#### 🪵 Branch Convention
[Branch Convention](https://shadow-impatiens-f13.notion.site/Branch-Convention-223564d8d2a7804f8cb4d754a8e66a99?source=copy_link)

#### 🛠 Coding Convention
[Coding Convention](https://shadow-impatiens-f13.notion.site/Coding-Convention-222564d8d2a7809e9346da547bee6cc2?source=copy_link)

#### 📂 Package Convention
[Package Convention](https://shadow-impatiens-f13.notion.site/Packaging-Convention-222564d8d2a780b38d54f770fca1718c?source=copy_link)


<br>

## 🎨 프로젝트 설계
