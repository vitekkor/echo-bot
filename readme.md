# Echo-bot for just-ai

### Бот для ВКонтакте, который умеет отвечать на твои сообщения твоими же сообщениями (только текст).

Попробовать можно [тут](https://vk.me/echo_bot_justai)

## Stack
* #### Spring boot webflux
* #### Kotlin coroutines

## Requires

* jdk-11
* docker (if you want run it in docker)

##How to install

```
git clone https://github.com/vitekkor/echo-bot
cd echo-bot
```
Specify vk api params in `src/main/resources/application.properties`

Requires: 
* vk.api.accessToken - VK API access token
* vk.api.confirmationString - Callback API confirmation string
* vk.api.version - VK API version (default 5.103) 

```
chmod +x build.sh
./build.sh docker - to run it in docker. otherwise do not specify anything
```

### More about test case

Необходимо выполнить интеграцию с BotAPI VK. https://vk.com/dev/bots_docs

В рамках задания нужно создать бота который при его упоминании будет цитировать присланный ему текст. Пример взаимодействия с подобным ботом:

![example](example.jpg)


### Требования к реализации
В качестве решения хотелось бы получить ссылку на git репозиторий в котором находятся исходники Spring Boot приложения выполняющего логику бота.
Все параметры необходимые для корректного запуска и проверки должны задаваться в конфигурационных файлах (необходимо решить какие именно параметры).
Все сущности с помощью которых осуществляется взаимодействие должны быть представлены в виде POJO.
В readme должен быть описан процесс запуска приложения и необходимые параметры конфигурации.
Качество кода и выбранная внутренняя структура компонентов/сервисов также оценивается.
Использование языка Kotlin будет преимуществом (но не является обязательным).

**Важно! Нельзя использовать готовые библиотеки-реализации VkApi для Java.**

При реализации может потребоваться использование внешних https адресов для локальной машины. Для этого можно использовать ngrok.
