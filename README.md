# order-system

## AuthService port 8080
## TicketService 9090

Для тестирования предоставлена Postman коллекция, также предоставлен вспомогательный endpoint(get-stations). Номер созданного заказа выводится в log'ax. Все эндпоинты представлены в Postman коллекции.

Для запуска используем docker-compose up --build, перед этим собираем каждый сервис по отдельности с помощью gradle.

Имитация обработки заказа выполнена с помощью шедулера. Т.е метод вызывается через какой-то промежуток времени и сканирует все заказы, обрабатывая их.

Для валидации запросов используются JWT токены.

Внутри токена передаются email, username, userid.

Для работы с БД используется hibernate. Он автоматически создаст БД, или если она уже была создана, то просто будет работать с ней.

### GLHF