# 8. Thymeleaf LocalDateTime

Java 8에서 TimeStamp보다 LocalDateTime을 쓰라고 권장하고 있다.

이에 따라 수정한 과정을 설명하고 OneToMany에서 수정시간 고치는 방법을 기술한다.

## 1. thymeleaf 추가 Dependency
java8 thymeleaf에서 LocalDateTime 형식을 #date를 써서 가져오려고 하면 Error 가 발생한다. 이를 해결하기 위한
방법이 #temporals를 통해 가져오는데 이를 설정하기 위해 다음과 같은 Dependency가 필요하다.

~~~
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
    <version>3.0.1.RELEASE</version>
</dependency>
~~~

## 2. AppApplication.class 에 추가하기
아래의 Bean을 넣어주어야 작동을 한다.

~~~
 @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
~~~


## 3. thymeleaf 구문
예시) 처럼 #temporals를 기존 #dates 처럼 쓰면 된다.
~~~
<tr th:class="${iterStat.last}? 'border-bottom'">
    <th>출석시간</th>
    <td class="small">[[${#temporals.format(user.regdate)}]]</td>
</tr>
~~~