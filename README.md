# Social Media API

Это проект представляет собой API для социальной сети, позволяющий пользователям взаимодействовать, создавать посты, отправлять сообщения, управлять друзьями и подписками, а также просматривать ленту активности.

## Требования

- Java 8 или выше
- Spring Boot
- PostgreSQL

## Установка

1. Клонируйте репозиторий:

```shell
git clone https://github.com/yourusername/social-media-api.git
```

2. Установите зависимости и соберите проект:
```shell
cd social-media-api
mvn clean install
```

3. Создайте базу данных PostgreSQL и настройте соответствующие параметры подключения в файле application.properties.
4. Запустите приложение:
```shell
mvn spring-boot:run
```

## Использование
API документация доступна по адресу: http://localhost:8080/swagger-ui.html
Документация классов доступна по адресу: 

## Функциональность
- Аутентификация и регистрация пользователей
- Управление друзьями и подписками
- Создание, редактирование и удаление постов
- Отправка сообщений между пользователями
- Просмотр ленты активности с пагинацией и сортировкой

## Автор
[MrGreenNV] - [averkiev@gmail.com]