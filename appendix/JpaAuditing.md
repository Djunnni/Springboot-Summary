# 9. JpaAuditing을 통한 시간 동기화

Jpa 와 Hibernate를 이용해 Mysql을 사용하고 있다.

그런데? 문제가 발생 -> 데이터를 수정했는데 시간이 바뀌질 않아?? -> 무엇이 문제였을까?

## 1. 문제점

1. Query를 Repository에서 바로 구현해서 쓰면 시간이 고쳐지질 않았다.
=> Jpa를 이용해 save 방식으로 해결했다.

## 2. Entity 설정
시간에 대해서는 어떠한 Domain에서도 모두 사용하기에 아래와 같이 구성했다.
LocalDateTime을 사용했으므로 [8장](https://github.com/Djunnni/Springboot-Summary/blob/master/Appendix/Thymeleaf_LocalDateTime.md)을 참고해라.
이 때 EntityListener로 AuditingEntityListener를 가져와서 쓸 것이다. 
~~~
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
	
	@CreatedDate
	private LocalDateTime regdate;
	
	@LastModifiedDate
	private LocalDateTime modifieddate;
}
~~~
## 3. 예시 Domain 설정
equalsAndHashCode를 사용할 경우 extends 로 받은 BaseTimeEntity로 인해 warning이 발생할 것이다.
callSuper를 통해 문제를 해결할 수 있는데 이 callSuper는 부모 클래스까지 비교해서 참과 거짓을 분간할 건지 의사를 묻는다고 보면 된다.
~~~
@EqualsAndHashCode(of="ano",callSuper = false)
public class Activity extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ano;

	// =========== user list =========== //
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="act_no")
	private List<User> users;
	
}
~~~
## 4. AppApplication.class 설정
아래처럼 @EnableJpaAuditing 과 EntityListener가 run과 함께 동작하도록 설정
~~~
@SpringBootApplication
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class TestAppApplication {
	
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
  
	public static void main(String[] args) {
		SpringApplication.run(AttendenceAppApplication.class, args);
	}

}
~~~
## 5. 예시를 통한 결과 확인
예시처럼 Repository를 통해 하나의 Activity를 받고 @setter를 통해 save하면 시간이 변경된다.
~~~
@PostMapping(value= {"/test"})
@Transactional
@ResponseBody
public int testActivity(@PathVariable String url) {
    Activity act = actRepo.findOneByURLLike(url);
    act.setFinished(!act.isFinished());
    try {
    actRepo.save(act);
    } catch(Exception e) {
        return 500;
    }
    return 200;
}
~~~

## 6. 결과 예시
+-----+---------------------+---------------------+-------+-----------------------------+----------+--------+
| ano | modifieddate        | regdate             | aname | url                         | finished | mem_no |
+-----+---------------------+---------------------+-------+-----------------------------+----------+--------+
|   1 | 2019-12-02 21:24:40 | 2019-12-02 21:18:07 | asd   | XXXXXXXXXXXXXXXXXXX         |        0 |      1 |
+-----+---------------------+---------------------+-------+-----------------------------+----------+--------+
1 row in set (0.00 sec)

