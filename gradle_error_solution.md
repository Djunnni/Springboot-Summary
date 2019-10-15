## Gradle 환경 변경시 문제점 솔루션

1. Support for clients using a tooling API version older than 3.0 was removed in Gradle 5.0

Gradle의 버전에 의해 build를 할수 없는 상황이다. 

이럴 경우 project > gradle > wrapper > gradle-wrapper.properties 파일에서
distributionUrl을 바꿔주어야 한다.
