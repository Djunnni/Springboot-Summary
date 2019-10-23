# 2. MYSQL 오류 대처법

## 1. mysql Public Key Retrieval is not allowed

해결법 : allowPublicKeyRetrieval=true 을 url에 파라미터로 추가

## 2. mysql KST 설정 문제

해결법 : serverTimezone=Asia/Seoul 추가 또는 mysql server에서 my.ini 파일에

```text
default-time-zone="+9:00" 추가하기
```

## 3. ERROR 2002 \(HY000\): Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' 설정법

환경: OSX

1. brew를 통해서 mysql 을 삭제한다.
2. 해결법

   brew service stop mysql

   brew uninstall mysql

   rm -rf /usr/local/var/mysql rm /usr/local.etc.my.cnf

3. brew install mysql \(설치 이후 HY000 2002 에러가 뜰 수 있는데 이는 디비서버가 열려있지 않아서 발생한문제\)
4. 해결법

   mysql.server start

