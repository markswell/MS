#!/bin/bash
cd auth
mvn clean package -P prod -DskipTests
cd ..
cd cadastro
mvn clean package -P prod -DskipTests
cd ..
cd gateway
mvn clean package -P prod -DskipTests
cd ..
cd registry
mvn clean package -P prod -DskipTests
cd ..
cd venda
mvn clean package -P prod -DskipTests
cd ..
cd auth
docker build .
INPUT=$(echo $(docker images | grep '<none>'))
IMAGE=$(echo $INPUT| cut -d' ' -f 3)
docker tag $IMAGE markswell/auth-ms:1.0
docker tag $IMAGE markswell/auth-ms:latest
cd ..
cd cadastro
docker build .
INPUT=$(echo $(docker images | grep '<none>'))
IMAGE=$(echo $INPUT| cut -d' ' -f 3)
docker tag $IMAGE markswell/cadastro-ms:1.0
docker tag $IMAGE markswell/cadastro-ms:latest
cd ..
cd gateway
docker build .
INPUT=$(echo $(docker images | grep '<none>'))
IMAGE=$(echo $INPUT| cut -d' ' -f 3)
docker tag $IMAGE markswell/gateway-ms:1.0
docker tag $IMAGE markswell/gateway-ms:latest
cd ..
cd registry
docker build .
INPUT=$(echo $(docker images | grep '<none>'))
IMAGE=$(echo $INPUT| cut -d' ' -f 3)
docker tag $IMAGE markswell/registry-ms:1.0
docker tag $IMAGE markswell/registry-ms:latest
cd ..
cd venda
docker build .
INPUT=$(echo $(docker images | grep '<none>'))
IMAGE=$(echo $INPUT| cut -d' ' -f 3)
docker tag $IMAGE markswell/venda-ms:1.0
docker tag $IMAGE markswell/venda-ms:latest
cd ..