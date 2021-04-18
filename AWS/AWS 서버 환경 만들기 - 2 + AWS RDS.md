# AWS 서버 환경 만들기 - 2 + AWS RDS

## RDS instance 생성

> RDS(Relational Database Service)는 AWS에서 지원하는 클라우드 기반 관계형 데이터베이스이다. 
>
> 하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같이 잦은 운영 작업을 자동화하여 개발자가 개발에 집중할 수 있게 지원하는 서비스이다.
>
> `조정 가능한 용량`을 지원하여 예상치 못한 양의 데이터가 쌓여도 추가 비용만 내면 정상적으로 서비스가 가능하다.

RDS 인스턴스 생성 시 사용할 DB 엔진은 `본인이 가장 잘 사용하는 데이터베이스`를 고르면 되지만, 딱히 그런 기준이 없는 경우에서는 `Maria DB`를 선택하는 것이 좋다.

RDS의 가격은 라이센스의 비용에 영향을 받는데, `Maria DB`는 상용 DB인 Oracle, MSSQL 보다 저렴하며, `Amazon Aurora 교체가 용이`하기 때문이다. (또한 공식 자료에서 MySQL, PostgreSQL 보다 성능면에서 우월하다고 함.)

`Amazon Aurora`는 프리티어 대상이 아니며, 최저 비용이 월 10만원 이상이기에, 서비스 규모가 어느정도 커졌을 때 MariaDB에서 Aurora로 이전하면 된다.

1. AWS Console의 RDS에서 `데이터베이스 생성`을 선택한 후, 엔진 옵션에 `MariaDB`를, 템플릿에 `프리티어`를 선택한다.
2. DB 인스턴스와 마스터 사용자 정보를 등록한다. (여기서 사용된 정보로 실제 데이터베이스에 접근하게 되므로 잊지 않게 기억하고 있어야 한다.)
3. 네트워크에서 `퍼블릭 액세스를 허용`하고, 이후 `보안 그룹`에서 `지정된 IP만 접근 허용`하도록 하겠다.
4. 데이터베이스 옵션에서 `데이터베이스 이름`을 설정하고, 나머지는 기본값인 채로 데이터베이스 생성을 마친다.

## RDS 운영환경에 맞는 파라미터 설정

> RDS 인스턴스를 처음 생성시에 몇가지 설정을 필수로 해줘야 한다. 우선 다음 3개의 설정을 바꿔보겠다.
>
> - 타임존
> - Character Set
> - Max Connection

1. 왼쪽 카테고리 `파라미터 그룹`에서 새로운 `파라미터 그룹 생성`을 선택하여 RDS의 DB 엔진 버전과 같은 버전으로 새 파라미터 그룹을 생성한다.
2. 타임존 변경: 생성된 새 파라미터 그룹을 클릭하여 파라미터 편집을 클릭하여, `time_zone`을 검색하여 `Asia/Seoul`로 변경한다.
3. Character Set 변경: 아래 8개 항목 중 character 항목들은 utf8mb4로, collation 항목들은 utf8mb4_general_ci로 변경한다. (utf8과 utf8mb4의 차이는 `이모지 저장 가능 여부`이며 보편적으로 utf8mb4를 많이 사용한다.)
   - `character_set_client`
   - `character_set_connection`
   - `character_set_database`
   - `character_set_filesystem`
   - `character_set_results`
   - `character_set_server`
   - `collation_connection`
   - `collation_server`
4. Max Connection 변경: RDS의 Max Connection은 `인스턴스 사양에 따라 자동으로` 결정되지만, 프리티어 사양에선 약 60개의 커넥션만 가능하다. 좀 더 넉넉한 값으로 지정해주도록 한다.(필자는 150)
5. 이제 다시 왼쪽 카테고리의 `데이터베이스`에 들어가서 새로 생성한 `DB 파라미터 그룹`을 적용시키도록 한다.
   - 수정 클릭 후, DB 파라미터 그룹을 변경하고 계속한 뒤, 즉시 적용을 누른다.

## RDS 보안 그룹 설정

> 로컬 PC에서 RDS로 접근하기 위해 RDS 보안 그룹에 설정을 추가한다.
>
> `EC2에 사용된 보안 그룹의 그룹 ID`와 `본인의 IP`를 `RDS 보안 그룹의 인바운드 규칙`에 추가한다.

인바운드 규칙 유형에서는 MYSQL/Aurora를 선택하면 자동으로 3306 포트가 선택된다.
EC2의 보안 그룹을 추가하면 `EC2와 RDS 간에 접근이 가능`하게 된다. (EC2의 경우 여러대가 될 수도 있는데, 매번 IP를 등록할 수는 없으니 보편적으로 이런식으로 보안 그룹 간에 연동을 진행한다고 한다.)

## 내 PC에서 RDS 접속방법

로컬에서 원격 데이터베이스에 접근할 때, MySQL Workbench 같은 GUI 클라이언트를 많이 사용한다.

Workbench에서도 비슷한 방법으로 접근가능하지만, 여기서는 Intellij Community 버전에서 Database Plugin을 사용하여 접속해 보겠다.

1. Marketplace에서 `Database Navigator`를 설치한다.

2. 설치 후, 프로젝트 왼쪽 사이드바에서 DB Browser의 +버튼을 눌러 MySQL 접속정보를 설정한다.

   - 여기서 Host는 RDS의 엔드포인트를, User와 Password는 RDS 인스턴스 생성시에 입력했던 것을 사용하여야 한다.

3. Test Connection을 수행해 본 후, Apply -> OK 를 차례로 누른다.

4. 아마 기본적으로 콘솔창이 하나 있을텐데, 만약 없다면 새로 생성한다.

5. 쿼리가 수행될 database를 지정한다. 

   ```sql
   use 'AWS RDS 웹 콘솔에서 지정한 데이터베이스명';
   ```

다음 쿼리문으로 `파라미터 그룹`에서 변경한 내용들도 반영되었음을 알 수 있다.

```sql
show variables like 'c%';

select @@time_zone, now();
```

그러나 character_set_database, collation_connection 2가지 항목은 latin1로 되어있는 것을 확인할 수 있다.

이 2개의 항목은 MariaDB에서만 RDS 파라미터 그룹으로는 변경이 안되므로 직접해줘야 한다. 다음 쿼리를 실행시켜서 직접 변경해야한다.

```sql
ALTER DATABASE 데이터베이스명
CHARACTER SET = 'utf8mb4'
COLLATE = 'utf8mb4_general_ci';
```

한글명이 제대로 들어가는지 간단한 테이블과 insert 쿼리를 실행해보아도 정상 동작되는 것을 알 수 있다.

```sql
CREATE TABLE test (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	content varchar(255) DEFAULT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB;

INSERT into test(content) values('테스트');
SELECT * FROM test;
```

## EC2에서 RDS에 접근 확인

> EC2에 SSH 접속을 한 후 RDS에 접근할 수 있는지를 확인해본다.

EC2에 접속한 후, MySQL 접근 테스트를 위해 일단 MySQL CLI를 다음 명령어로 설치한다.

```sh
sudo yum install mysql
```

설치가 다 됐으면 로컬에서 접근하듯이 계정, 비밀번호, 호스트 주소를 사용하여 다음 명령어로 RDS에 접속한다.

```shell
mysql -u 계정 -p -h Host주소(RDS 엔드포인트)
```

위의 명령어로 RDS에 접속했다면, 아까 테스트할 때 있었던 데이터베이스들이 그대로 존재하는지 다음 명령어로 확인해본다.

```mysql
show databases;
```