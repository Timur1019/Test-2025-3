# File Uploader Service

Простой сервис обмена файлами на Java (Javalin) + фронт на Vanilla JS.

## 🚀 Возможности

- Загрузка файлов
- Получение уникальной ссылки для скачивания
- Автоматическое удаление нескачиваемых файлов через 30 дней
- Просмотр статистики (дата последнего скачивания)

## 🧱 Стек

- **Backend**: Java + Javalin
- **Frontend**: HTML + CSS + JavaScript
- **Сборка**: Gradle

## 📁 Структура проекта

file-uploder/

├ ── src/

│ ── main/java/org/com/...

├ ── front-file-uploader/

│ ── index.html

│ ── style.css

│ ── script.js

├ ── build.gradle

└ ── README.md

bash
Копировать
Редактировать

## 🛠 Запуск

1. Перейти в директорию:
   ```bash
   cd file-uploder
Собрать и запустить:

bash
Копировать
Редактировать
./gradlew run
Открыть index.html во вкладке браузера (дважды кликни или:

file:///путь_к_проекту/front-file-uploader/index.html

🌐 API

POST /upload — загрузка файла

GET /download/{id} — скачивание файла

GET /stats — статистика по файлам