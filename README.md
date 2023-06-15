[![Java CI with Maven](https://github.com/Dima-Stepanov/my_dreamjob/actions/workflows/maven.yml/badge.svg)](https://github.com/Dima-Stepanov/my_dreamjob/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/Dima-Stepanov/my_dreamjob/branch/master/graph/badge.svg?token=CZPUaHNoWs)](https://codecov.io/gh/Dima-Stepanov/my_dreamjob)

<h1>Проект "Работа мечты"</h1>

<h3>Описание проекта</h3>
Приложение представляет сайт по размещению вакансий с предложением трудоустройства, <br>
а также резюме кандидатов на различные должности для работодателя.<br>
Вакансия состоит из названия, описания, логотипа компании, города размещения, <br>
и признака размещена вакансия или нет. <br>
Резюме кандидата состоит из имени кандидатов, описания желаемой должности, <br>
фото кандидата и города проживания.<br>
Так же реализована страница регистрация пользователей в системе и последующая их авторизация.<br>
Все функции приложения доступны только зарегистрированным пользователям.<br>

<h3>Стек технологий</h3>
Java 17 <br>
PostgreSQL 14 <br>
Spring BOOT 2.7.4 <br>
Liqubase 4.15 <br>
Sql2o 1.6 <br>
Bootstrap 5.0.2 <br>
Thymeleaf 3.1 <br>

<h3>Требования к окружению</h3>
Java 17,
Maven 3.8,
PostgreSQL 14.

<h3>Запуск приложения</h3>

1. Создайте базу данных my_dreamjob при помощи консоли PostgreSQL или терминала pgAdmin:<br>
   """CREATE DATABASE my_dreamjob"""
2. Скопировать проект из репозитория по ссылке:
   <a href=https://github.com/Dima-Stepanov/my_dreamjob.git><b>Проект работа мечты</b></a>
3. Перейдите в корень проекта и при помощи Maven соберите проект командой:<br>
"""mvn install -Pproduction -Dmaven.test.skip=true"""
4. После успешной сборки проекта перейдите в каталог собранного проекта <b>target</b> и запустите приложение командой:<br>
"""java -jar my_dreamjob-1.0-snapshot.jar"""
5. В браузере перейдите по ссылке http://localhost:8080/index

<h3>Взаимодействие с приложением</h3>

![login.jpg](img%2Flogin.jpg) <br>

Рисунок 1. Авторизация пользователя.

![register.jpg](img%2Fregister.jpg) <br>

Рисунок 2. Регистрация пользователя.

![index.jpg](img%2Findex.jpg) <br>

Рисунок 3. Главная страница.

![vacancies_list.jpg](img%2Fvacancies_list.jpg) <br>

Рисунок 4. Список всех вакансий.

![candidates_list.jpg](img%2Fcandidates_list.jpg) <br>

Рисунок 5. Список всех кандидатов.

![vacancies_create.jpg](img%2Fvacancies_create.jpg) <br>

Рисунок 6. Создание новой вакансии.

![candidates_create.jpg](img%2Fcandidates_create.jpg) <br>

Рисунок 7. Создание нового кандидата.

![vacancies_one.jpg](img%2Fvacancies_one.jpg) <br>

Рисунок 8. Редактирование вакансии.

![candidates_one.jpg](img%2Fcandidates_one.jpg) <br>

Рисунок 9. Редактирование кандидата.

![error.jpg](img%2Ferror.jpg) <br>

Рисунок 10. Страница ошибки.

### Контакты

> email: [haoos@inbox.ru](mailto:haoos@inbox.ru) <br>
> tl: [Dima_software](https://t.me/Dima_software) <br>
> github.com: [Dima-Stepanov](https://github.com/Dima-Stepanov)
