# jakarta-ee-example

Jakarta EEのコード例。
Arquillianを使って動作を確認する。

## 準備

PostgreSQLとWildFlyを起動する。

```bash
docker compose up -d
```

WildFlyの管理ユーザーを作成する。

```bash
docker compose exec wildfly /opt/jboss/wildfly/bin/add-user.sh admin secret
```

PostgreSQLのJDBCドライバーをデプロイする。

```bash
./mvnw wildfly:deploy-artifact
```

データソースを作成する。

```bash
docker compose exec wildfly /opt/jboss/wildfly/bin/jboss-cli.sh --connect \
    --command='/subsystem=datasources/data-source=PostgresDS:add(\
    jndi-name=java:/PostgresDS, driver-name=postgresql, driver-class=org.postgresql.Driver, \
    connection-url=jdbc:postgresql://db:5432/demodb, user-name=demouser, password=secret)'
```

## 実行

テストを実行する。`-Dtest`でテストクラスを指定すること。

```bash
./mvnw test -Dtest=HelloWorldServletTest
```

> [!WARNING]
> ぜんぶ一気に流すと途中で止まる。。。
> 原因は調べられていない。。。

## その他

ログを見る。

```bash
docker compose logs -f --since=0m
```

WildFlyの管理CLIを使う。

```bash
docker exec wildfly /opt/jboss/wildfly/bin/jboss-cli.sh --connect
```

psqlでデータベースを見る。

```bash
docker exec db psql -U demouser demodb
```

後始末。

```bash
docker compose down -v
```
