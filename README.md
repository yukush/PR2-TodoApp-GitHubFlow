# Todo CLI — ПР № 2

Практическая работа «Организация проекта с системой контроля версий и CI/CD». В методичке требуется консольное Java-приложение; это не Android-приложение и оно не требует Android SDK.

**Автор:** Якушев Антон Игоревич · **Вариант:** 22 · **Группа/обозначение:** ПР-23.101

> Публикация в GitHub ещё не выполнена: архив подготовлен локально, а ссылка на удалённый репозиторий добавляется после создания репозитория в аккаунте GitHub.

## Быстрый старт

Нужны JDK 21 и доступ в интернет для первого запуска сборки. Запуск из корня проекта:

- Windows: `gradlew.bat run`
- Linux/macOS: `./gradlew run`

Проверка и сборка:

- Windows: `gradlew.bat clean build`
- Linux/macOS: `./gradlew clean build`

Исполняемый fat-JAR появится в `build/libs/todo-app-<version>.jar`.

## Команды приложения

- `add <текст>` — добавить задачу; внешние пробелы удаляются, пустая строка игнорируется.
- `list` — показать список задач и их статусы.
- `remove <index>` — удалить задачу по индексу, начиная с `0`.
- `done <index>` — пометить задачу выполненной.
- `search <строка>` — поиск по подстроке без учёта регистра.
- `clear` — удалить все задачи.
- `help` — показать справку.
- `exit` — завершить работу.

Список хранится в памяти процесса и очищается после закрытия приложения.

## Проверки и CI/CD

- JUnit 5: тесты добавления и нормализации, удаления и границ индекса, защитной копии списка, `clear`, `done` и `search`.
- `.github/workflows/ci.yml`: проверки на Pull Request и при `push` в `main`; успешный push в `main` публикует fat-JAR в GitHub Releases.
- `.github/workflows/publish-package.yml`: запускается после успешного `CI / Build & Release` на `main` и публикует Maven-артефакт в GitHub Packages.
- `.github/workflows/nightly.yml`: ночная сборка и тесты по расписанию без создания релиза.

GitHub Packages берёт имя владельца и репозитория из `GITHUB_REPOSITORY`; логин и токен передаются средой GitHub Actions. Секреты не хранятся в исходном коде.

## Публикация на GitHub

1. Создайте на GitHub пустой репозиторий `pr2-todo-app` (без автоматически добавляемого README/license).
2. Откройте терминал в этой папке и настройте собственную идентичность Git. Уже созданные коммиты имеют имя автора «Якушев Антон Игоревич», но технический временный email example.invalid; при необходимости привязать их к аккаунту GitHub перед первой публикацией нужно переписать метаданные коммитов на ваш подтверждённый GitHub email.

   `git config user.name "Якушев Антон Игоревич"`

   `git config user.email "ВАШ_EMAIL_ОТ_GITHUB"`

3. Добавьте удалённый репозиторий и отправьте main, сохранённые feature-ветки и тег:

   `git remote add origin https://github.com/ВАШ_НИК/pr2-todo-app.git`

   `git push -u origin main`

   `git push origin --all`

   `git push origin --tags`

4. В GitHub откройте **Settings → Actions → General** и убедитесь, что workflow разрешено создавать releases и публиковать packages (разрешения `contents: write` и `packages: write` задаются в workflow). Разрешите workflow запускаться при `push` в `main`.
5. Проверьте вкладки **Actions**, **Releases** и **Packages**. В README и отчёте добавьте реальные ссылки после первой успешной публикации.

## Структура

```text
.github/workflows/ci.yml
.github/workflows/publish-package.yml
.github/workflows/nightly.yml
gradle/wrapper/gradle-wrapper.properties
gradlew
gradlew.bat
src/main/java/org/example/TodoApp.java
src/main/java/org/example/TodoList.java
src/test/java/org/example/TodoListTest.java
build.gradle
settings.gradle
gradle.properties
```

## Примечание о Gradle bootstrap

Среда подготовки была без доступа к сети и без установленного Gradle, поэтому вместо официального `gradle-wrapper.jar` включён небольшой bootstrap-лаунчер: он скачивает зафиксированный Gradle 8.14.6 и проверяет SHA-256 архива. Он поддерживает команды `gradlew` / `gradlew.bat`, но это не официальный Wrapper JAR. Если преподаватель требует стандартный Gradle Wrapper, после установки Gradle выполните `gradle wrapper --gradle-version 8.14.6`, проверьте сгенерированные файлы и закоммитьте их. Workflow использует этот launcher и ожидает доступ к `services.gradle.org` и Maven Central.

## Отчёт

Отчёт в формате DOCX находится в `docs/Отчет_ПР2_Якушев_Антон.docx`. В нём отдельно отмечены результаты локальной проверки и то, что требуется подтвердить запуском GitHub Actions после публикации.

## Дополнительное задание (стр. 52 методички)

Реализованы `clear`, `done <index>` и `search <строка>`. Для расширенного функционала добавлены тесты на очистку списка, отметку выполнения, недопустимые индексы и поиск без учёта регистра. Отдельный `Nightly Build & Test` запускается по cron ежедневно в 01:17 UTC и вручную через `workflow_dispatch`; он собирает проект и тестирует его, но не публикует Release.

## Ожидаемые ссылки после публикации

- Repository: `https://github.com/<OWNER>/pr2-todo-app`
- Actions: `https://github.com/<OWNER>/pr2-todo-app/actions`
- Releases: `https://github.com/<OWNER>/pr2-todo-app/releases`
- Packages: `https://github.com/<OWNER>?tab=packages`
