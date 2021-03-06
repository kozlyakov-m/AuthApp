1. Разобраться с основными понятиями баз данных (60 мин):
    1. Вопросы:
        * Что такое таблица (5 мин)
        * Как к ней осуществляестся доступ (требуется ли особая авторизация) (10 мин)
        * Что такое индексы (10 мин) 
        * Что такое внешний ключ (5 мин) 
        * Что такое уникальный ключ (5 мин) 
        * Что такое схема данных (10 мин)  
        * Что такое инициализация схемы данны (5 мин)
        * Как происходит миграция (10 мин)
    2. Ожидаемый результат:        
        * Определим схему базы данных
        
2. Ознакомиться с документацией [H2](http://www.h2database.com/html/main.html) (55 мин): 
    1. Вопросы:
        * Как она работает (10 мин)
        * Какие есть возможности (15 мин)
        * Как её подключить (10 мин)
        * Как создавать сущности в БД (20 мин)
    2. Ожидаемый результат:
        * Сможем дополнить раздел подключения H2

3. Ознакомитсья с понятиями DAO, DAL (70 мин)
    1. Вопросы:
        * Где и когда используется DAO (15 мин)
        * Применение DAL (10 мин)
        * Что входит в DAL (10 мин)      
        * Чем DAO отличается от DAL (10 мин)
        * Входят ли DAO, DAL в Anemic Domain Model (10 мин)
    2. Ожидаемый результат:
        * Сможем провести рефакторинг на использование DAO, DAL

4. Изучить SQL Injection (55 мин): 
    1. Вопросы:
        * Чем она вызвана (20 мин)
        * Способы предотвращения SQLI в общем (20 мин)
        * Какие есть классы/методы в Java/Kotlin для предотвращения SQLI (15 мин)
    2. Ожидаемый результат:
        * Сможем создать меры предотвращения SQL Injection

5. Ознакомиться с документацией [FlyWayDB](http://flywaydb.org/getstarted/firststeps/api.html) (45 мин)
    1. Вопросы:
        * Для чего используется (15 мин)
        * Как она помогает с миграцией БД (15 мин)
        * Как хранить тестовые данные до миграции (10 мин)
    2. Ожидаемый результат:
        * Сможем наполнить базу тестовыми данными

6. Подключить H2: (R3.3; 55 мин)
    1. Представить тестовые данные как Mock объекты для (15 мин):
        * базы пользователей
        * ресурсов и ролей
        * активности
    2. Создать схему данных (R3.4; 20 мин)
    3. Проверить наличие файла БД приложения (`somename.db`) (20 мин)
        * Если он присутствует - инициализировать схему (R3.5)
        * Если отсутствует - создать и заполнить его тестовыми данными (R3.6) 
    4. Добавить методы защиты от SQL Injection (R3.7, R3.8; 15 мин)
    5. Изменить способ доступа к БД на `System.getenv()` (R3.12; 5 мин)
7. Сделать рефакторинг на использование DAO и DAL (R3.9, R3.11; 40 мин)
8. Проверить соостветствие принципу SOLID, если есть несоотсветсвие - изменить (R3.10; 40 мин)
9. Добавить логгирование (R3.1; 30 мин)
    1. Создать `object Logger`, использующий `log4j.logger` (5 мин)
    2. Добавить `logger.log()` в БЛ для: (20 мин)
        * вывода введёных аргументов (debug)
        * выявления причины неудачи для проверки логина, пароля, ресурса, роли
        * в блоки catch добавить вывод stacktrace в лог-файл
    3. Настроить `log()` на запись также и в файл (R3.2; 5 мин)
10. Создать новые тестовые сценарии на проверку невозможности SQL Injection (R3.7, R3.8; 20 мин)
11. Подчистка (где требуется) - 30 мин

№ | Оценка <br/> времени | Фактическое <br/> время
--- | :---: | :---:
**1** | 60 мин |
**2** | 55 мин |
**3** | 70 мин |
**4** | 55 мин |
**5** | 45 мин |
**6** | 55 мин |
6.i | 15 мин |
6.ii | 20 мин |
6.iii | 15 мин  |
6.iv | 5 мин  |
**7** | 40 мин |
**8** | 40 мин |
**9** | 30 мин  |
9.i | 5 мин |
9.ii | 20 мин |
9.iii | 5 мин |
**10** | 20 мин |
**11** | 30 мин |
**Итого** | 510 мин <br/> (8.5 ч) |