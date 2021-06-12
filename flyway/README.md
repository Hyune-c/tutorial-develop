# flyway 를 구성해보자!

## 환경

- Spring boot 2.4.5
- java 8
- mysql - with docker
- flyway

## 설명

1. local mysql 에 `flyway` 스키마를 생성합니다.
    - jdbc:mysql://localhost:3306/flyway
2. `jpa.hibernate.ddl-auto: validate` 설정으로 테이블의 정합성을 검증합니다.
3. `/resources/db/migration` 에 DDL, DML sql 을 정의합니다.
4. 실행한 sql 을 확인할 수 있습니다.
    ```sql
    select * from flyway_schema_history
    ```
   ![image](https://user-images.githubusercontent.com/55722186/117412906-4bddbe80-af50-11eb-94a3-54d2133efd03.png)
5. 서로 다른 환경의 DB (dev - stage - prod) 에서 운영할 때는 `checksum` 과 `version` 이 맞음을 확인해야합니다.
    - 맞지 않으면 어플리케이션이 기동되지 않습니다.

## 참고 자료

- [나만 모르고 있던 - Flyway (DB 마이그레이션 Tool)](https://www.popit.kr/%EB%82%98%EB%A7%8C-%EB%AA%A8%EB%A5%B4%EA%B3%A0-%EC%9E%88%EB%8D%98-flyway-db-%EB%A7%88%EC%9D%B4%EA%B7%B8%EB%A0%88%EC%9D%B4%EC%85%98-tool/)
- [형상관리 라이브러리 flyway 적용해보기](https://closset703.github.io/programming/spring/2020/05/13/flywaydb-howto.html)
