# Catalog Service – Product APIs 

> This README assumes you will run the service via **Docker Compose** from the [infra repo](https://github.com/ecommerce-lifecycle/infra).  
> Spring Boot service for managing products. Runs as part of the **infra stack** (Postgres + Kafka + Zookeeper + pgAdmin).

---

## Quick Start (via Infra)

1) **Clone both repos in a parent folder:**
```bash
# Parent folder
mkdir -p ~/ecommerce-lifecycle && cd ~/ecommerce-lifecycle

# Clone repos
git clone https://github.com/ecommerce-lifecycle/catalog-service.git
git clone https://github.com/ecommerce-lifecycle/infra.git
````

2. **Run stack (from infra repo)**

```bash
cd infra
docker compose up -d --build
```

3. **Verify service**

```bash
docker compose ps
docker logs -f catalog-service
```

4. **Open APIs**

   * Base URL: [http://localhost:8081/api/v1/products](http://localhost:8081/api/v1/products)
   * Swagger UI: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
   * Health: [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health)

---

## Endpoints (Samples)

### ➡️ Create Product

`POST /api/v1/products`

```json
{
  "name": "OnePlus Nord CE 4",
  "description": "OnePlus latest phone",
  "price": 34999,
  "category": "Mobile"
}
```

*Response (201):*

```json
{
  "status": "SUCCESS",
  "code": 201,
  "message": "Product created successfully",
  "data": {
    "id": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
    "name": "iPhone 13",
    "description": "Apple latest phone",
    "price": 59999.99,
    "category": "Mobile",
    "active": true,
    "createdAt": "2025-08-26T18:45:32.015",
    "updatedAt": "2025-08-26T18:45:32.015"
  }
}
```

### ➡️ Get All Products

`GET /api/v1/products`

### ➡️ Get Product by ID

`GET /api/v1/products/{id}`

### ➡️ Update Product

`PUT /api/v1/products/{id}`

```json
{
  "name": "iPhone 13",
  "price": 59999.99,
  "category": "Mobile"
}
```

### ➡️ Deactivate Product

`PATCH /api/v1/products/{id}/deactivate`

---

## Database Notes

* Current config:

  ```properties
  spring.jpa.hibernate.ddl-auto=none
  ```
* Tables **will not auto-create**.
* Schema & initial data come from `infra/postgres/init.sql`.
* If you drop DB or volumes → `init.sql` runs automatically to recreate schema + seed data.

---

## Dev Notes

* `active` defaults to **true** if not passed in request.
* Health check: `/actuator/health`.
* For code changes, re-run: `docker compose up -d --build` from `infra`.

---

## 🔄 Development Workflow

#### 1. Fast local iteration (Recommended for dev)

For small code changes, avoid rebuilding Docker images:

```bash
mvn spring-boot:run
```

👉 Before running locally, **delete infra volumes** (to avoid Kafka/Postgres state conflicts):

```bash
rm -rf infra/kafka-data infra/zookeeper-data infra/postgres-data
```

#### 2. Full rebuild (when dependencies change)

```bash
cd infra
docker compose down -v
docker compose up -d --build
```

---

### Run Locally (Without Docker)

* If you want to run the catalog-service as a plain Spring Boot application without Docker/Kafka,
* check out the [`standalone-catalog-service`](https://github.com/your-repo/catalog-service/tree/standalone-catalog-service) branch.

* That branch:
	- Does not require Kafka or Docker.
	- Uses `spring.jpa.hibernate.ddl-auto=update` so schema is auto-created.
	- Only supports basic APIs (`GET`, `POST`, etc.) without event streaming.

```