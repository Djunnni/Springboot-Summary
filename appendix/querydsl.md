# 1. Querydsl

## Querydsl을 통한 동적 SQL 처리법

@Querydsl 을 사용하기 위해서는 3가지 단계를 거처야 한다.

1. pom.xml 의 라이브러리 + Maven 설정 변경 및 실행
2. Predicate 개발
3. Repository를 통한 실행

개발자는 SQL을 직접 처리하지 않고 , Querydsl을 통해서 필요조건을 처리할 수 있다.

## 설치법

[maven repository](https://mvnrepository.com/) 에서 querydsl을 찾는데

여기서 패키지 'com.querydsl' 버전을 이용한다.

pom.xml에 를 아래처럼 추가한다.

```text
<!-- https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa -->
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-jpa</artifactId>
            <version>4.1.4</version>
        </dependency>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <version>4.1.4</version>
            <scope>provided</scope>
        </dependency>
```

&lt;최근 4.2.1 이 나왔는데 4.1.4도 사용자가 많아 이걸로 한다&gt;

Querydsl은 JPA를 처리하기 위해 Entity class를 생성하는 방식이다. 따라서 Qdomain 클래스를 생성하는 작업을 위한 코드생성기가 필요한데 pom.xml에서 을 추가해준다.

```text
<!-- ADD FOR Querydsl -->
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <goals>
                <goal>process</goal>
            </goals>
            <configuration>
                <outputDirectory>target/generated-sources/java</outputDirectory>    
                <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>    
            </configuration>
        </execution>
    </executions>
</plugin>
```

\*\* Querydsl plugin에서 execution 오류 처리법

* 오류내용

  ```text
  Execution default of goal com.mysema.maven:apt-maven-plugin:1.1.3:process failed: Plugin com.mysema.maven:apt-maven-plugin:1.1.3 
  or one of its dependencies could not be resolved: Failed to collect dependencies at com.mysema.maven:apt-maven-plugin:jar:1.1.3 -> 
  org.apache.commons:commons-io:jar:1.3.2 (com.mysema.maven:apt-maven-plugin:1.1.3:process:default:generate-sources)
  ```

* 처리과정

  \(1번과정은 생략해도 됩니다.\)

* 메이븐 리소스 플러그인 추가

  ```text
  <!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-resources-plugin -->
   <dependency>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-resources-plugin</artifactId>
       <version>3.0.2</version>
   </dependency>
  ```

* 프로젝트 우클릭 이후 RunAs -&gt; Maven install\(build\) 해도 됨 , Maven -&gt; update project

## 실행 Test 코드

```text
@Test
public void testPredicate() {
    String type="t";
    String keyword="17";

    BooleanBuilder builder = new BooleanBuilder();

    QBoard board = QBoard.board;

    if(type.equals("t")) {
        builder.and(board.title.like("%"+keyword+"%"));
    }
    builder.and(board.bno.gt(0L));

    Pageable pageable = PageRequest.of(0, 10);
    Page<Board> result = repo.findAll(builder, pageable);
    System.out.println(result.getSize());
    List<Board> list = result.getContent();
    list.forEach(b->System.out.println(b));
}
```

실행 Console 내용

```text
Hibernate: select board0_.bno as bno1_0_, board0_.content as content2_0_, board0_.regdate as regdate3_0_, board0_.title as title4_0_, board0_.updatedate as updateda5_0_, board0_.writer as writer6_0_ from tbl_boards board0_ where (board0_.title like ? escape '!') and board0_.bno>? limit ?
Hibernate: select count(board0_.bno) as col_0_0_ from tbl_boards board0_ where (board0_.title like ? escape '!') and board0_.bno>?
```

