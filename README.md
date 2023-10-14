# Stat buddy service

Service for backing up statistic subsystem.

[![Java version](https://img.shields.io/static/v1?label=Java&message=17&color=blue)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_stat_buddy_service)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_stat_buddy_service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_stat_buddy_service)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_stat_buddy_service&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_stat_buddy_service)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_stat_buddy_service&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_stat_buddy_service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_stat_buddy_service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_stat_buddy_service)

## Table of Contents
- [Features](#features)
- [Documentation](#documentation)
- [Deployment](#deployment)

## Features
* Statistics for regions

## Documentation

All the service's documentation can be found in it's [docs folder](docs)
There you can find:
- Data model


## Deployment

Service can be deployed locally or using docker. Required tools:
* [Java 17 SDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Gradle >= 8](https://gradle.org/install/)

Also, there should be several side systems. Following is the list of that with related config's env names (default in brackets):
- Postgres (v. >= 15):
    - host: DB_HOST (localhost)
    - port: DB_PORT (5432)
    - name: DB_DATABASE (stat_buddy_service_db)
    - schema: DB_DATABASE_SСHEMA (stat_buddy_service)
    - user: DB_USER_NAME (stat_buddy_service)
    - password: DB_PASSWORD (stat_buddy_service)
  
В стандартной конфигурации сервис держит открытым для http соединений порт 8080.
Настройку можно изменить, добавив `HTTP_PORT` системные переменные.

`Liveness` и `readiness` API доступны по `actuator/health/liveness` и `actuator/health/readiness` путям.  
Уровень логирования можно менять через системную переменную `logging.level.root` (info, debug, etc.).
Если нет необходимости смотреть ВСЕ debug логи, можно ограничить debug уровень конкретными пакетами,
установив переменную `logging.level.<PACKAGE_TO_LOG>`.

Метрики для Prometheus доступны по адресу `actuator/prometheus`.

### Локальная развертка

Необходимо установить системную переменную spring.profiles.active=dev.
Приложение будет доступно по `11000` порту (http)

Можно разворачивать либо конфигурацией из IntellijIDEA, либо вручную командой в терминале
```bash
$ .\gradlew bootRun --args='--spring.profiles.active=dev'
```

#### Локальный Docker

Необходимо использовать `docker-compose-local.yml` чтобы собрать образ и стартовать контейнер.
В стандартной конфигурации сервис будет использовать `application-dev.yml` файл свойств.


## Публикация

Для каждой новой версии сервиса (можно завязать выкат на релиз, можно ка каждый коммит в мастер и т.д.)
необходимо проводить публикацию API модуля в репозитории пакетов maven.
Для этого используется задача publish
```
.\gradlew publish
```

Для ее корректной работы необходимо указать несколько системных переменных:
- CI_ARTIFACT_REPO_HOST - хост nexus репозитория
- CI_ARTIFACT_REPO_NAME - логин для авторизации в nexus
- CI_ARTIFACT_REPO_TOKEN - пароль для авторизации в nexus

## Quick Start

Application will run by default on port `11000`

Configure the port by changing `server.port` in __application.yml__

### Run Local

Depending on which environment you want to launch the service you should choose
spring profile:
* To launch service in dev contour, user `dev` profile

You can run application either via Intellij launch configuration (preferred way) or
manually
```bash
$ .\gradlew bootRun --args='--spring.profiles.active=dev'
```

### Run Docker

Use `docker-compose-local.yml` to build the image and create a container.
Note, that by default container will run using `application-dev.yml`

### Run code quality assurance tasks

Когда проект собирается с использованием `build` задачи gradle detekt и ktlint проверки проходят автоматически,
и detekt xml отчет формируется по путям `stat_buddy_app/build/report/detekt`
и `stat_buddy_api/build/report/detekt`. Также есть возможность запускать проверки вручную командой
```
.\gradlew detekt
```

Для получения процента покрытия необходимо:
```
.\gradlew test jacocoTestReport coverage
.\gradlew sonar -D"sonar.host.url"="https://sonarcloud.io" -D"sonar.token"="YOUR_LOGIN" -D"sonar.projectKey"="KEY" -D"sonar.organization"="ORG" 
```
Отчет по покрытию будет сгенерирован в `stat_buddy_app/build/report/jacoco`
Кроме того, при вызове sonar с помощью gradle задачи сгенерированный detekt отчет будет добавлен к анализу.

## API

### Web endpoints
All the service's web endpoints specification can be found in swagger page /swagger-ui.html
