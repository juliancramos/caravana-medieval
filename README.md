# Caravana Medieval 

**Caravana Medieval** is a multiplayer web-based trading game set in the Middle Ages. It was developed as a university project by Julian Ramos and Juan Rozo, and allows players to lead caravans across different maps and cities, making strategic decisions around commerce, survival, dangerous routes, and resource management.

Each user can maintain up to **three active games simultaneously**, enabling them to run parallel campaigns, such as a solo adventure and others in multiplayer mode without losing progress. Each game session is configured from a **preloaded map** (stored in the PostgreSQL database), an initial **caravan**, a chosen **difficulty level**, and starting conditions such as gold, health, and initial city (depending on the user choices).

From a technical perspective, the frontend is built using **Angular**, while the backend is developed with **Java + Spring Boot**, connected to a **PostgreSQL** database (hosted on Neon). All visual resources like images are stored in a cloud service. The system includes automated tests over the core services.

A key feature is the **real multiplayer mode**. When creating a new game, a player can generate a temporary join code (valid for 30 minutes) to invite others to join their caravan. Once connected, each player takes on a **functional role**:

* **Merchant**: can buy and sell goods
* **Caravanner**: can move between cities and purchase services

Commerce mechanics are based on city-specific products and services. Purchase and sale prices follow formulas tied to stock, demand, and supply (following the instructions in the statement) . Services allow players to repair the caravan, upgrade its speed or cargo capacity, or hire guards to reduce damage from dangerous routes. All decisions affect the outcome of the game, which has a time limit and a required economic goal. Losing all gold, health, or failing to reach the goal in time results in game loss.

Currently, the repository is structured into two main modules:

* `backend/`: Business logic, REST services, authentication, and data persistence.
* `frontend/`: Angular application with routing and visual interface.

The **backend architecture** follows a layered approach:

* **Controllers** handle HTTP requests and validate inputs.
* **Services** implement the business logic.
* **Repositories (JPA)** handle communication with the PostgreSQL database.
* **Entities (models)** represent domain objects and are mapped with JPA annotations.

To run the project locally:

**Backend:**

```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**

```bash
cd frontend
npm install
ng serve --proxy-config proxy.conf.json
```

The frontend will be served at `http://localhost:4200`, while the backend runs at `http://localhost:8080`.

