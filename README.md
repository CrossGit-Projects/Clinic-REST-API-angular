**Aplikacja składa się z serwisów – warstwa serwerowa**

- Eureka
- Gateway(Zuul)
- Authorization Service(Oauth2 oraz JWT)
- UserService(Postgresql - lokalnie)
- VisitService(Postgresql - lokalnie)
- Pdf generation service
- Notification service
- RabbitMQ


**Frontend**
Patients App (Angular)

**Podział roli w aplikacji:**
Admin
Doktor
Pacjent

**User-Service**
 - operacje CRUD dla użytkowanika
 - wyświetlenie listy użytkowników(administrator),
 - wyświetlanie listy pacjentów(doktor),
 - Rejestracja użytkownika(Admin, Doktor, Pacjent),
 - Modyfikownie użytkowników(Admin),
 - Usuwanie, dodawanie użytkowników(Admin).

**Visit-Service**

Dodawanie godzin przyjęć(Doktor),
Potwierdzenie wizyty(Doktor),
Zapis na wizytę(Pacjent),
Wystawienie diagnozy(Doktor)

**Pdf-generation-service**
Generowanie E-rachunków.


**Notification-service**

Wysyłanie maili informujących do użytkowników.

**RabbitMQ**
Wysyłanie wiadomości na RabbitMQ podczas rejestracji pacjenta(user-service),
Pobieranie wiadomości z RabbitMQ (notification-service).


**Patients App(Angular)**
Rejestracja użytkownika,
Wyświetlanie listy użytkowników,
Zapisywanie pacjenta na wizytę.
