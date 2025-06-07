# 📱 익명 모바일 게시판

간단하고 직관적인 **모바일 익명 게시판** 애플리케이션입니다.  
사용자 간 자유로운 소통을 익명으로 지원하며, 게시글 작성/조회/수정/삭제 기능을 제공합니다.

<br>

## 🛠️ 주요 기능

📋 **게시글 목록 보기**  
- 전체 게시글 확인  
- 최신순 / 조회수순 정렬  
- 키워드 검색 기능 제공  
- 👉 **RecyclerView**를 사용하여 게시글 리스트를 효율적으로 구현  
- 👉 **ViewModel + Coroutine**을 통해 데이터 비동기 처리 및 UI 상태 관리  
- 👉 서버와의 통신은 **Retrofit2**를 활용해 REST API 연동  
- 👉 백엔드에서는 **Express + Sequelize + MySQL**로 게시글 목록을 정렬, 필터링하여 제공

📝 **게시글 작성 및 조회**  
- 사용자는 새로운 게시글을 작성할 수 있고, 다른 사용자의 게시글 상세 내용을 조회 가능  
- 👉 게시글 작성/조회 시 **Jetpack 구성요소 (LiveData, ViewModel)** 로 UI 상태 관리  
- 👉 비동기 데이터 전송 및 수신은 **Coroutine + Retrofit2** 사용  
- 👉 서버에서는 **Node.js + Express**로 요청 처리, **MySQL**에 게시글 저장  
- 👉 **Sequelize**로 ORM 기반 DB 연동 처리

✏️ **게시글 수정 및 삭제**  
- 사용자는 본인이 작성한 게시글에 한해 수정 및 삭제 가능  
- 👉 수정/삭제 요청은 **Retrofit2 + Coroutine**으로 비동기 처리  
- 👉 서버에서는 사용자 식별 후 **Sequelize**로 해당 게시글 수정/삭제  
- 👉 데이터베이스는 **MySQL**, 비즈니스 로직은 **Node.js**로 구성

<br>

## 💡 사용 기술 스택

### 📱 Android
- Kotlin
- Jetpack (ViewModel, LiveData 등)
- Retrofit2
- Coroutine

### 🖥️ Backend
- Node.js (JavaScript)
- Express
- Sequelize (ORM)
- MySQL
