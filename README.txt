
Combined Train Booking Project (Using your backend code)

Location: backend/ (Spring Boot project)
Run steps (backend + frontend served together):

1) Prerequisites:
   - Java 17+ installed (java -version)
   - Maven installed (mvn -version)

2) Build & run:
   cd backend
   mvn clean package
   mvn spring-boot:run
   OR
   java -jar target/train-booking-1.0.0.jar

3) Open in browser:
   http://localhost:8080/
   (The frontend files are served from Spring Boot static resources)

Notes:
- This project uses your original TicketBooker and Passenger logic (adapted into the Spring project).
- Data is in-memory; restart the app to reset.
- Endpoints:
   POST /api/train/book  (body: {name, age, berthPreference})
   DELETE /api/train/cancel/{id}
   GET /api/train/available
   GET /api/train/booked
