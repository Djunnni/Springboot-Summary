# 10. AWS RDS timezone과 charset 변경

### 1. RDS 파라미터그룹 만들기

RDS 콘솔에 들어와 좌측에 보면 파라미터 그룹이 있다.

파라미터 그룹을 생성하고 들어오면

'파라미터 편집' <= 누르기


### 2. 파라미터 편집하기

1. time_zone 검색
=> Asia/Seoul 로 변경

2. char 검색
=> 아래처럼 변경
~~~
character_set_client = utf8mb4
character_set_connection = utf8mb4
character_set_database = utf8mb4
character_set_filesystem = binary
character_set_results = utf8mb4
character_set_server = utf8mb4
~~~

3. collation 검색
=> 아래처럼 변경
~~~
collation_connection = utf8mb4_general_ci
collation_server = utf8mb4_general_ci
~~~

#### 왜? utf8이 아닌 utf8mb4 ??
utf8mb4는 이모티콘까지 추가되어있어 utf8보다 좀 더 추가된 필드를 갖기때문

4. 변경사항 저장

###  3. 파라미터 적용하기

RDS 생성 또는 수정에서 

데이터베이스 옵션 > DB 파라미터 그룹 > [설정한 파라미터그룹 이름 설정]

적용


