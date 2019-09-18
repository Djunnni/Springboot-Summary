## Heroku - SpringBoot Deploy 하는 방법

### jawsDB 생성하기 (한글 지원)

   - Resources에서 jawsDB를 설치한다.
   - heroku config -a {app 이름}으로 jawsDB의 설정을 확인하고 연결한다.
~~~
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://{DB 설정} ?reconnect=true&useUnicode=true&characterEncoding=UTF-8
spring.datasource.username={id}
spring.datasource.password={password}
spring.datasource.sql-script-encoding=UTF-8
~~~

### clearDB 생성하기 (DB에 한글 안쓰고 영어만 쓸경우에 추천)

   - heroku에 가입하고 Resources 에서 clearDB를 설치합니다. -> 카드를 등록해야만 사용가능함
   - clearDB를 설치한 이후에 계정에 대한 정보를 얻으려면 아래와 같이 수행

   1) 자신의 콘솔에서는 heroku에 대한 설치후 사용가능 
~~~
heroku config --app <App 이름>
~~~
   => clearDB에 대한 url , name , passwd 확인가능
   
   2) heroku Resources에 생성된 clearDB 옆에 설정버튼을 클릭
   아이디와 비밀번호를 확인할 수 있음

   * JPA 와 사용법
~~~
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.url = jdbc:mysql:<url + DB name 넣기>?reconnect=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&useSSL=false&useUnicode=true&characterEncoding=UTF-8
  spring.datasource.username=<id>
  spring.datasource.password=<passwd>
~~~
 
### Heroku Deploy 설정법

   본인의 경우 STS에서 실행하였으므로 다음방법으로 실행하였습니다.

   STS에서 Project -> Export -> War -> 생성된 war 파일의 경로에서 
   ~~~
   heroku war:deploy {war 파일이름}.war  --app {heroku에 생성된 앱 이름}
   ~~~
   이렇게 진행하면 자동으로 deploy가 되어 실행가능

   ** 혹시 github으로 push해서 사용하신분은 연락좀 주세요. 하다가 오류가 발생했었습니다.

     && 같이 이 문제를 해결해보실 분도 연락주세요 &&

### Heroku vim 설치 및 사용법

   헤로쿠에서 vim을 쓰려고 하면 고질적으로 문제가 발생할 때가 있다.

   그래서 사람들이 찾아보션 nano 쓰라고 하는데 본인은 vi, vim이 너무 편해 이 문제를 해결해보고 싶었다.

   자신의 콘솔
~~~
1. heroku plugins:install @jasonheecs/heroku-vim -app {앱 이름}
2. heroku vim -a {앱 이름}
~~~
   이렇게 진행하면 바로 될 때도 있고 안될때도 있다.

~~~
mkdir ~/vim
cd ~/vim
curl 'https://s3.amazonaws.com/bengoa/vim-static.tar.gz' | tar -xz
export VIMRUNTIME="$HOME/vim/runtime"
export PATH="$HOME/vim:$PATH"
cd -
bash
on ⬢ {앱이름}... up, run.3055 (Free)
~~~

   1Dyno 만 지원하기 때문에 console에 접속은 1개만 가능하다.

   nano 사용법 참고: http://www.compulsivecoders.com/tech/how-to-edit-a-file-on-heroku-dynos-using-nano-or-vim/

### clearDB mysql의 my.cnf 변경하기(실패)

   my.cnf가 latin으로 잡혀서 한글 텍스트가 깨지는 경우가 발생한다. ??? 이렇게 나오는 데 일단 database의 config를 확인해보았다.

명령어 확인 하는 방법 ! 
~~~
mysql > show variables where Variable_name Like 'c%';
~~~

heroku 결과
~~~
+--------------------------+----------------------------+
| Variable_name            | Value                      |
+--------------------------+----------------------------+
| character_set_client     | utf8mb4                    |
| character_set_connection | utf8mb4                    |
| character_set_database   | latin1                     |
| character_set_filesystem | binary                     |
| character_set_results    | utf8mb4                    |
| character_set_server     | latin1                     |
| character_set_system     | utf8                       |
| character_sets_dir       | /usr/share/mysql/charsets/ |
| collation_connection     | utf8mb4_general_ci         |
| collation_database       | latin1_swedish_ci          |
| collation_server         | latin1_swedish_ci          |
| completion_type          | NO_CHAIN                   |
| concurrent_insert        | AUTO                       |
| connect_timeout          | 10                         |
+--------------------------+----------------------------+
~~~

본인의 MAC 결과
~~~
| character_set_client                         | utf8mb4                                              |
| character_set_connection                     | utf8mb4                                              |
| character_set_database                       | utf8mb4                                              |
| character_set_filesystem                     | binary                                               |
| character_set_results                        | utf8mb4                                              |
| character_set_server                         | utf8mb4                                              |
| character_set_system                         | utf8                                                 |
| character_sets_dir                           | /usr/local/Cellar/mysql/8.0.17/share/mysql/charsets/ |
| check_proxy_users                            | OFF                                                  |
| collation_connection                         | utf8mb4_general_ci                                   |
| collation_database                           | utf8mb4_general_ci                                   |
| collation_server                             | utf8mb4_general_ci                                   |
| completion_type                              | NO_CHAIN                                             |
| concurrent_insert                            | AUTO                                                 |
| connect_timeout                              | 10                                                   |
| core_file                                    | OFF                                                  |
| create_admin_listener_thread                 | OFF                                                  |
| cte_max_recursion_depth                      | 1000    
~~~

이제, utf8mb4로 변경을 해보자!!

1. 일단 임시방편으로 SET 명령어로 각각을 utf8mb4로 설정했었다. => 결과 : 실패 (입력시 ???)
2. 프로젝트 내에 utf에 대한 설정을 확인 => 결과 : 이상없었음
3. application.properties -> spring.datasource.url 에 DB 뒤에 파라미터 추가 입력 =>  결과 : 실패 (입력시 ???)
   - 입력내용 
~~~
(reconnect=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&useSSL=false&useUnicode=true&characterEncoding=UTF-8)
~~~
4. spring.datasource.tomcat.connection-properties=useUnicode=true;characterEncoding=utf-8; 추가 => 결과 : 실패

* 아무래도 DB의 collation을 latin1 에서 utf8mb4로 바꿀 수 없는 구조이다. 
* Database 의 타임존은 UTC로 되어있으나 heroku localtime 만 바꾸면 자동으로 잡아줌 

### heroku local time 변경하기

처음에 UTC 로 설정되어있기 때문에 deploy할 때, KST로 변경시켜주어야 한다.
~~~
heroku config:add TZ="Asia/Seoul" --app [APPNAME]
~~~
