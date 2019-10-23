# 7. React 오류 대처법

## 1. 프로젝트를 실행했는데 404 에러가 뜬다?

문제 : jsp를 사용하는데 있어 prefix 와 suffix 설정을 안했다.

해결 방법 : src &gt; main &gt; resources &gt; application.properties 파일에 다음을 추가한다.

```text
# prefix 경로 : src/main/webapp/jsp/ 
spring.mvc.view.prefix=/jsp/
# suffix : .jsp
spring.mvc.view.suffix=.jsp
```

## 2. 서버포트 문제로 8080이 되질 않으면?

해결 방법 : src &gt; main &gt; resources &gt; application.properties 파일에 다음을 추가한다.

```text
server.port = {원하는 숫자로 변경하면 됩니다.}
```

## 3. package.json에 script를 설정했는데 작동하질 않는다?

문제 :

-scripts에 대해 위에서 정의되어 있는데 아래에서 재정의를 할 경우

* scripts에 설정한 명령어가 실제로 존재하지 않을 경우

해결방법 : 따로 실행을 일단 해보고 scripts에 다시 넣어본다.

