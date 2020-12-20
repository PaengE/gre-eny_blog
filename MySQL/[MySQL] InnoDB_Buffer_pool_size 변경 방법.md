> 대용량 데이터를 다룰 때, mysql 쿼리문의 속도가 느리는 것을 넘어서 실행조차 안될 때가 있다.
> 이럴 때에는 mysql의 buffer pool의 사이즈를 조정하면 된다.
> 너무 크게 하면 하드디스크를 가상 메모리로 쓰는 작업(스와핑)이 잦아 매우 느려지는 원인이 될 수도 있다고 하니 제대로 사용할 것이라면 관련 문서를 찾아보길 바란다.
> 개인장비에서 사용 할 때에는 크게 부담이 없을 것 같긴 하다.
>
> 나는 25G가 넘는 sql파일을 실행할 때 실행조차 되지 않아서 버퍼 풀 사이즈를 늘렸었다.

# InnoDB_Buffer_pool_size 변경 방법

### 다음 명령어로 현재 buffer pool size를 확인 할 수 있다.

```mysql
mysql> SHOW STATUS LIKE '%innodb_buffer_pool%';
+---------------------------------------+--------------------------------------------------+
| Variable_name                         | Value                                            |
+---------------------------------------+--------------------------------------------------+
| Innodb_buffer_pool_dump_status        | Dumping of buffer pool not started               |
| Innodb_buffer_pool_load_status        | Buffer pool(s) load completed at 200921 16:04:14 |
| Innodb_buffer_pool_resize_status      |                                                  |
| Innodb_buffer_pool_pages_data         | 275                                              |
| Innodb_buffer_pool_bytes_data         | 4505600                                          |
| Innodb_buffer_pool_pages_dirty        | 0                                                |
| Innodb_buffer_pool_bytes_dirty        | 0                                                |
| Innodb_buffer_pool_pages_flushed      | 36                                               |
| Innodb_buffer_pool_pages_free         | 7917                                             |
| Innodb_buffer_pool_pages_misc         | 0                                                |
| Innodb_buffer_pool_pages_total        | 8192                                             |
| Innodb_buffer_pool_read_ahead_rnd     | 0                                                |
| Innodb_buffer_pool_read_ahead         | 0                                                |
| Innodb_buffer_pool_read_ahead_evicted | 0                                                |
| Innodb_buffer_pool_read_requests      | 1131                                             |
| Innodb_buffer_pool_reads              | 242                                              |
| Innodb_buffer_pool_wait_free          | 0                                                |
| Innodb_buffer_pool_write_requests     | 325                                              |
+---------------------------------------+--------------------------------------------------+
18 rows in set (0.01 sec)

mysql> select @@innodb_buffer_pool_size;
+---------------------------+
| @@innodb_buffer_pool_size |
+---------------------------+
|                 134217728 |
+---------------------------+
1 row in set (0.01 sec)

mysql> select @@innodb_buffer_pool_chunk_size;
+---------------------------------+
| @@innodb_buffer_pool_chunk_size |
+---------------------------------+
|                       134217728 |
+---------------------------------+
1 row in set (0.00 sec)

mysql> select @@innodb_buffer_pool_instances;
+--------------------------------+
| @@innodb_buffer_pool_instances |
+--------------------------------+
|                              1 |
+--------------------------------+
1 row in set (0.00 sec)
```

여러 내용이 있지만 간단하게 사용할거면 버퍼풀 사이즈를 변경할 때
innodb_buffer_pool_size, innodb_buffer_pool_chunk_size, innodb_buffer_pool_instances만 신경 쓰면 될 것 같다.

innodb_buffer_pool_size / innodb_buffer_pool_instances 한 값이 innodb_buffer_pool_chunk_size가 자동으로 저장 되는데
innodb_buffer_pool_size / innodb_buffer_pool_chunk_size 한 값이 1000을 넘지 않도록 설계하는 것이 좋다고 한다 (느려질 수 있기 때문에)

또한 innodb_buffer_pool_size를 변경 하더라도 innodb_buffer_pool_instances *innodb_buffer_pool_chunk_size의 배수가 되도록 자동 조정 된다. 

### 다음 명령어로 버퍼 풀 사이즈를 변경 할 수 있다.

버퍼 풀 사이즈 변경은 3가지 방법이 있다.

 \1. shell 단에서 하는 경우

```
shell> mysqld --innodb-buffer-pool-size=8G --innodb-buffer-pool-instances=16
```

- innodb_buffer_pool_chunk_size는 기본값이 128M이다. 8G가 128M * 16 = 2G의 배수이므로 8G는 유효한 값이다.

```
shell> mysqld --innodb-buffer-pool-size=9G --innodb-buffer-pool-instances=16
```

- 9G는 2G의 배수가 아니므로 mysql에서 자동으로 innodb_buffer_pool_size를 10G로 조정한다.

 \2. MySQL 구성 파일에서 변경하는 경우

- mysql 구성 파일에서 [mysqld] 밑에서 직접 바꿔 주면 된다.
- 이 때에도 위에서 말한 것과 같이 자동 조정 활동이 일어난다.

 \3. mysql 단에서 하는 경우

```
mysql> SET global innodb_buffer_pool_size = 4294967296;
```

- 다음 명령어를 사용하여 직접 변수 값을 변경 하는 경우가 있다.
- 이 때에는 4G가 아니라 4294967296(실제 바이트 값)으로 사용하여야 한다.