down-wildfly:
	docker rm -f wildfly

up-wildfly:
	@# Resource Adapter（MDBの方かも？）を試すためにstandalone-full.xmlを使っている。詳しいことはわからん。
	docker run -d --name wildfly -p 8080:8080 -p 9990:9990 jboss/wildfly /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0 -c standalone-full.xml
	@while [ "$$(docker logs wildfly|grep WFLYSRV0025)" = "" ]; do\
		sleep 1;\
	done
	docker exec wildfly /opt/jboss/wildfly/bin/add-user.sh admin secret --silent

logs-wildfly:
	docker logs -f --since=0m wildfly

exec-bash-wildfly:
	docker exec -it wildfly bash

