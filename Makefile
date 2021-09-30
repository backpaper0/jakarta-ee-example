down:
	docker rm -f wildfly
	docker rm -f db

up:
	docker run -d --name db -e POSTGRES_DB=demodb -e POSTGRES_USER=demouser -e POSTGRES_PASSWORD=secret postgres -c log_statement=all
	@# Resource Adapter（MDBの方かも？）を試すためにstandalone-full.xmlを使っている。詳しいことはわからん。
	docker run -d --name wildfly --link db -p 8080:8080 -p 9990:9990 jboss/wildfly /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0 -c standalone-full.xml
	@while [ "$$(docker logs wildfly|grep WFLYSRV0025)" = "" ]; do\
		sleep 1;\
	done
	docker exec wildfly /opt/jboss/wildfly/bin/add-user.sh admin secret --silent
	./mvnw wildfly:deploy-artifact
	docker exec wildfly /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=datasources/data-source=PostgresDS:add(\
		jndi-name=java:/PostgresDS, driver-name=postgresql, driver-class=org.postgresql.Driver, \
		connection-url=jdbc:postgresql://db:5432/demodb, user-name=demouser, password=secret)'

logs-wf:
	@clear
	docker logs -f --since=0m wildfly

logs-db:
	@clear
	docker logs -f --since=0m db

bash:
	docker exec -it wildfly bash

jboss-cli:
	docker exec -it wildfly /opt/jboss/wildfly/bin/jboss-cli.sh --connect

psql:
	docker exec -it db psql -U demouser demodb

