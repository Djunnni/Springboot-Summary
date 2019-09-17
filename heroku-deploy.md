## Heroku - SpringBoot Deploy 하는 방법

1. clearDB 생성하기

 - heroku에 가입하고 Resources 에서 clearDB를 설치합니다. -> 카드를 등록해야만 사용가능함
 - clearDB를 설치한 이후에 계정에 대한 정보를 얻으려면 아래와 같이 수행
 
 1) 자신의 콘솔에서는 heroku에 대한 설치후 사용가능 
 ~~~
 heroku config --app <App 이름>
 => clearDB에 대한 url , name , passwd 확인가능
 ~~~
 2) heroku Resources에 생성된 clearDB 옆에 설정버튼을 클릭
 아이디와 비밀번호를 확인할 수 있음
 
 * JPA 와 사용법
 ~~~
 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql:<url + DB name 넣기>?reconnect=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&useSSL=false&useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=<id>
spring.datasource.password=<passwd>
 ~~~
 
2. Heroku Deploy 설정법

본인의 경우 STS에서 실행하였으므로 다음방법으로 실행하였습니다.

STS에서 Project -> Export -> War -> 생성된 war 파일의 경로에서 
~~~
heroku war:deploy {war 파일이름}.war  --app {heroku에 생성된 앱 이름}
~~~
이렇게 진행하면 자동으로 deploy가 되어 실행가능

** 혹시 github으로 push해서 사용하신분은 연락좀 주세요. 하다가 오류가 발생했었습니다.

   && 같이 이 문제를 해결해보실 분도 연락주세요 &&
   
3. Heroku vim 설치 및 사용법

http://www.compulsivecoders.com/tech/how-to-edit-a-file-on-heroku-dynos-using-nano-or-vim/
