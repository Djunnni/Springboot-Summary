# 6. Gradle 오류 대처법

### Gradle 환경 변경시 문제점 솔루션

STS4 환경에서 실행할 때,

### 1. Support for clients using a tooling API version older than 3.0 was removed in Gradle 5.0

#### 문제점

Gradle의 버전에 의해 build를 할수 없는 상황이다.

buildShip 버전과 Gradle 이 3.0 하위버전에서 지원을 하기 때문에 이럴 때는 Gradle의 레벨을 낮춰야 된다.

#### 해결방법

gradle &gt; wrapper &gt; gradle-wrapper.properties에서 distributionUrl 의 gradle 버전을 낮춘다.

=&gt; 본인이 보기에는 STS4와 Gradle의 버전지원이 낮아 보여서 환경을 vscode \(gradle 5.0써도 오류 x\) 로 바꾸게 되었습니다.

### 2. Could not fetch model of type buildenvironment using gradle installation

#### 문제점

Gradle이 안맞거나 market place에서 gradle pack을 다운로드 받지 않은 환경일 수 있다.

#### 해결방법

eclipse market place에서 gradle을 입력하고

1. Gradle IDE pack 다운로드
2. BuildShip Gradle Integration 다운로드

### 3. Could not install Gradle distribution from ...

#### 문제점

Gradle에 해당주소의 자원이 없거나 https의 오류 또는 방화벽, Gradle의 설정에 문제일 수 있다.

#### 해결방법

1. **1 번 문제처럼  distributionUrl 에서 https -&gt; http로 refresh 해보자**
2. 방화벽 문제일 수 있으니 확인해보자
3. gradle에 대해 설정이 따로 필요할 수 있다.

   오른쪽 버튼 &gt; properties &gt; gradle &gt; override setting &gt; gradle distribution 버전과 java 버전 바꿔주기

### STS4에서 오류가 너무 나서 진행을 못하겠다? =&gt; Visual Studio Code 갈아타기

본인의 경우 STS4 에서 계속 해봐도 쉽게 고쳐지지 않고 Gradle을 다운그레이드 해보고 진행해도 오류가 있어 vscode로 갈아타기로 했다.

* 아직 해보는 중이지만 확실히 오류는 줄은 것 같다.

#### 설치하기

extension에서 아래의 이름을 받는다.

1. Java Extension Pack
2. Spring initializr Java Support 
3. Spring Boot Tools
4. Spring Boot extension Pack
5. Spring Boot DashBoard // DashBoard가 보여서 쉽게 실행할 수 있게 해준다.

#### Project 생성하기

osx 기준 \)

Shift + Commend + p 누른다. 1. &gt; spring initializr: Generate Maven or Gradle 생성 2. &gt; Java, Kotlin, Groovy 중 선택 3. &gt; Package 이름 설정 4. &gt; Artifact Id 설정 5. &gt; SpringBoot version 설정 6. &gt; Dependencies 추가하기 // STS4 만큼 편리하다 7. &gt; 생성

#### Project 실행하기

build.gradle 을 수정하고 나서 console 창에서 \( Commend + '~' 누르면 콘솔창 뜸 \) 1. gradle build 입력 2. gradle bootRun 입력

또는

1. Application.java 에서 run 을 눌러 실행하면 됨

