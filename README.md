# Bolão Copa 2018

## Este aplicativo foi desenvolvido para um bolão interno.


As configurações para acesso ao bd estão em src/main/resources/config/application-<profile>.yml

Build para o profile de desenvolvimento:

```
$ yarn install
$ ./mvnw

```


Build para o profile de produção:

```
$ yarn install
$ ./mvnw -Dprod

```

Rodar no docker:

```
$ ./mvnw package -Pprod -DskipTests dockerfile:build
$ docker-compose -f src/main/docker/app.yml up -d

```





