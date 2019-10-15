# 3. Spring Ajax CSRF 대처법

## Spring Security Ajax 호출시 CSRF 403 Forbidden 에러 솔루션

Spring Security를 이용할 경우 Ajax 호출 시에 403 Forbidden 에러가 발생합니다.

저같은 경우에는 SecurityConfig의 Path권한 문제인 줄 알았는데 계속해서 확인을 해보니 CSRF쪽 문제같아 여러 사이트를 찾아보고 솔루션을 적어보게 되었습니다.

CSRF : Cross-site Request fogery 의 의미로 A서비스에 로그인하면 브라우저에 A 서비스의 로그인 관련 쿠키정보가 남게 됩니다. 이후 동일 브라우저에서 악의적인 코드가 있는 B서비스에 접속하게 되면 B는 A의 서비스로 임의의 권한이 필요한 Request를 전송할 수 있습니다.

이 문제는 과거 유명 경매 사이트인 옥션에서 발생한 개인정보 유출사건에 사용된 방식입니다. 그렇기에 개발자 분들은 이문제를 CSRF 토큰을 통해서 권한문제를 해결해야 합니다.

보통 http.csrf\(\).disable\(\) 처럼 csrf에 대한 disable에 대해서 언급하는데 이럴 경우에 보안문제가 발생할 수 있기에 추천하지 않습니다.

## CSRF 가 enable 상태에서 403 Forbidden에러가 발생하지 않게 하려면?

Ajax 요청 Header에 CSRF 토큰 정보를 포함해서 전송하면 됩니다.

*  내에 csrf meta tag 추가

  ```text
  <meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
  <meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>
  ```

* ajax 호출 시에 사용하는 xhr에 request header를 이 정보를 이용하도록 설정

```text
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
         xhr.setRequestHeader(header,token);
    });
});
```

