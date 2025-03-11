## 필요 환경

- JDK 21
- MySQL 8버전
- Intellij Ultimate Version
---
# ⚙️ 데이터베이스 로컬 설정 ⚙️
- MySQL 로컬 데이터베이스에 'babpat'이라는 데이터베이스가 존재해야 함 (workbench 상에서 아래의 쿼리 실행 -> 맥 기준 `command + enter` 하면 실행됩니다.)

```sql
CREATE DATABASE babpat;

USE babpat;

CREATE USER 'babpat'@'localhost' IDENTIFIED BY 'babpat1!';

GRANT ALL ON babpat.* TO 'babpat'@'localhost';

FLUSH PRIVILEGES;
```

설명
- `CREATE USER 'babpat'@'localhost' IDENTIFIED BY 'babpat1!';` 에서 `babpat` 및 `babpat1!` 은 `username` 및 `password` 이므로 원하시는대로 진행하시면 됩니다.
- `GRANT ALL ON babpat.* TO 'babpat'@'localhost';` 여기서 `TO` 다음 `babpat`의 경우 위의 줄에서 `'babpat'`과 똑같이 적어주시면 됩니다.
- 저 sql문을 그대로 실행하면 똑같은 환경에서 진행할 수 있습니다. (추천)

</br></br>

# ⚙️ 인텔리제이 환경 설정 ⚙️
JDK 21을 로컬에 설치한 후 인텔리제이의 다음 설정을 확인합니다.

### 1. Project structure (단축키 : `command + ;`) 
- SDK 항목을 `21`로 맞추시면 됩니다. (꼭 화면의 값과 똑같지 않으셔도 됩니다.)
<img src="https://github.com/user-attachments/assets/86ff2061-b59f-4fc9-914b-bc7769d89cab" width="600">
</br>

### 2. Java Compiler (단축키 : `command + ,`)
- `command + ,` 후 검색창에 java compiler 를 검색하시면 됩니다.
- Java Compiler의 버전을 21로 해주시면 됩니다.
<img src="https://github.com/user-attachments/assets/1289f81e-c397-4d4a-9785-0d749051aeaa" width="600">
</br>

### 3. Gradle JVM (단축키 : `command + ,`)
- `command + ,` 후 검색창에 gradle 검색하시면 됩니다.
- Gradle JVM을 `corretto-21` 로 해주시면 됩니다.
- 만약 `corretto-21` 이 없다면 아래로 살짝 내려주세요~!
<img src="https://github.com/user-attachments/assets/256907bc-f45a-41ae-99d6-1986916bc238" width="600">
</br>

>### `corretto-21` 없는경우
>1. Gradle JVM 에서 Download JDK 선택
><img src="https://github.com/user-attachments/assets/74cdee32-d75c-4668-b9e0-1e2204a7a268" width="500">
>
>2. Version을 21로 해주고 Amazone Corretto 21.0.6 선택후 다운로드
><img src="https://github.com/user-attachments/assets/b9b53db4-5eba-4608-b84f-e6040d7f9f57" width="500">
</br></br>

# 🗂️ application.yml 설정파일 추가 🗂️
- 해당 파일은 gitignore 되어있기 때문에 노션을 참고해주세요. (기능별 메모장 페이지 참조!)
- 이후 사진의 폴더(`resources`) 아래에 파일(`application.yml`) 추가해주시면 됩니다.
<img src="https://github.com/user-attachments/assets/adf3a0e5-0254-48b1-a435-cca7990f8beb" width="500">
</br></br>

# 🏃 클론 진행 🏃
- 해당 레파지토리를 클론 후 `build.gradle` 파일로 이동 (자동 빌드 될수도 있습니다)
- 사진상 파란 코끼리 눌러주시면 build 됩니다!
![image](https://github.com/user-attachments/assets/8b0e5579-a2c8-488e-bc68-2cca7201cfb6)
</br></br>

# 👏 프로젝트 실행 👏
- 가장 간단한 방법은 intellij 상단의 실행 버튼(세모버튼) 클릭
  
![image](https://github.com/user-attachments/assets/58e5f272-b96c-4ed6-80b7-4cdd6383a055)







