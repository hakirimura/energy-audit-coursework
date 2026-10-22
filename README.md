# Курсова робота: Інформаційна система планування заходів енергоаудиту

**Студент:** Зінзівер Артем Юрійович
**Група:** 202-ТН
**Варіант:** №9

## Технології
- Java 21, Maven
- JavaFX (клієнт)
- Servlet API, Tomcat 9 (сервер)
- MySQL, JDBC
- JUnit 5

## Структура
- `server/` — серверний Maven-проєкт (моделі, DAO, Service, Servlet).
- `client/` — клієнтський Maven-проєкт (JavaFX, HTTP-клієнт, CSV-імпорт).
- `database.sql` — скрипт створення БД та демо-даних.
- `data.csv` — приклад файлу для імпорту.

## Інструкція запуску
1. Створіть базу даних за допомогою `database.sql` (MySQL).
2. Створіть файл `db.properties` у корені проєкту (на рівні з папками `server` та `client`) та вкажіть у ньому пароль від MySQL у форматі `db.password=ваш_пароль`. (Або задайте змінну середовища `DB_PASSWORD`).
3. Зберіть сервер: `cd server && mvn clean package`.
4. Скопіюйте `server/target/server-1.0-SNAPSHOT.war` у папку `webapps` Tomcat 9 під ім'ям `ROOT.war` (попередньо видаливши стару папку `ROOT`).
5. Запустіть Tomcat (`bin/startup.bat`). (Для коректної роботи сервера файл `db.properties` також потрібно скопіювати до папки `bin` Tomcat, або задати змінну середовища).
6. Запустіть клієнт: `cd client && mvn javafx:run`.

## Тестування
Запуск тестів: `cd server && mvn test`. (Тести вимагають запущеної MySQL та створеної бази `energy_audit`).