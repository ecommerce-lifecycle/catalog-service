# Catalog Service - Product APIs

Base URL: http://localhost:8081/api/v1/products

---
Swagger UI: http://localhost:8081/swagger-ui/index.html#/

---

## Endpoints

### ➡️ Create Product
**POST** `/api/v1/products`

Request Body:
```json
{
  "name": "iPhone 13",
  "description": "Apple latest phone",
  "price": 59999.99,
  "category": "Mobile"
}
```

Response:
```json
{
  "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
  "name": "iPhone 13",
  "description": "Apple latest phone",
  "price": 59999.99,
  "category": "Mobile",
  "active": true,
  "createdAt": "2025-08-26T18:45:32.015",
  "updatedAt": "2025-08-26T18:45:32.015"
}
```

➡️ Get All Products

**GET** `/api/v1/products`

Response:
```json
[
  {
    "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
    "name": "iPhone 13",
    "description": "Apple latest phone",
    "price": 59999.99,
    "category": "Mobile",
    "active": true,
    "createdAt": "2025-08-26T18:45:32.015",
    "updatedAt": "2025-08-26T18:45:32.015"
  }
]
```

➡️ Get Product by ID

**GET** `/api/v1/products/{id}`

Response:
```json
{
  "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
  "name": "iPhone 13",
  "description": "Apple latest phone",
  "price": 59999.99,
  "category": "Mobile",
  "active": true,
  "createdAt": "2025-08-26T18:45:32.015",
  "updatedAt": "2025-08-26T18:45:32.015"
}
```

➡️ Update Product

**PUT** `/api/v1/products/{id}`

Request Body:
```json
{
  "name": "iPhone 13 Pro",
  "description": "Apple flagship phone",
  "price": 69999.99,
  "category": "Mobile"
}
```

Response:
```json
{
  "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
  "name": "iPhone 13 Pro",
  "description": "Apple flagship phone",
  "price": 69999.99,
  "category": "Mobile",
  "active": true,
  "createdAt": "2025-08-26T18:45:32.015",
  "updatedAt": "2025-08-26T18:50:12.152"
}
```

➡️ Deactivate Product

**PATCH** `/api/v1/products/{id}/deactivate`

Response:
HTTP 200 (no body)
Product will be marked as inactive (active=false)