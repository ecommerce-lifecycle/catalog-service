# Catalog Service - Product APIs

Base URL: http://localhost:8081/api/products

---
Swagger UI: http://localhost:8081/swagger-ui/index.html#/

---

## Endpoints

### ➡️ Create Product
**POST** `/api/products`

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
  "status": "SUCCESS",
  "code": 200,
  "message": "Product fetched successfully",
  "data": {
    "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
    "name": "iPhone 13 Pro",
    "description": "Apple flagship phone",
    "price": 69999.99,
    "category": "Mobile",
    "active": true,
    "createdAt": "2025-08-26T18:45:32.015",
    "updatedAt": "2025-08-26T18:50:12.152"
  },
  "timestamp": "2025-08-26T20:40:00"
}
```

➡️ Get All Products

**GET** `/api/products`

Response:
```json
[
	{
	  "status": "SUCCESS",
	  "code": 200,
	  "message": "Products fetched successfully",
	  "data": {
		"productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
		"name": "iPhone 13 Pro",
		"description": "Apple flagship phone",
		"price": 69999.99,
		"category": "Mobile",
		"active": true,
		"createdAt": "2025-08-26T18:45:32.015",
		"updatedAt": "2025-08-26T18:50:12.152"
	  },
	  "timestamp": "2025-08-26T20:40:00"
	}
]
```

➡️ Get Product by ID

**GET** `/api/products/{id}`

Response:
```json
{
  "status": "SUCCESS",
  "code": 200,
  "message": "Product fetched successfully",
  "data": {
    "productId": "9adfad9a-4c52-40a6-9ea6-80152e70fd5b",
    "name": "iPhone 13 Pro",
    "description": "Apple flagship phone",
    "price": 69999.99,
    "category": "Mobile",
    "active": true,
    "createdAt": "2025-08-26T18:45:32.015",
    "updatedAt": "2025-08-26T18:50:12.152"
  },
  "timestamp": "2025-08-26T20:40:00"
}
```

➡️ Update Product

**PUT** `/api/products/{id}`

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
    "status": "SUCCESS",
    "code": 200,
    "message": "Product updated successfully",
    "data": {
        "productId": "7d121aba-3ba4-4e03-a0c3-e9413df2c341",
        "name": "iPhone 15 Pro",
        "description": "Apple flagship model",
        "price": 99999.99,
        "category": "Mobile",
        "active": false,
        "createdAt": "2025-08-27T05:41:26.699259",
        "updatedAt": "2025-08-27T05:41:26.699259"
    },
    "timestamp": "2025-08-27T05:42:23.259388"
}
```

➡️ Deactivate Product

**PATCH** `/api/products/{id}/deactivate`

Response:
```json
{
    "status": "SUCCESS",
    "code": 200,
    "message": "Product deactivated successfully",
    "data": {
        "productId": "59407794-7f39-40a3-9694-30d17b825ad1",
        "name": null,
        "description": null,
        "price": null,
        "category": null,
        "active": false,
        "createdAt": "2025-08-26T23:46:45.30091",
        "updatedAt": "2025-08-27T05:57:20.1327478"
    },
    "timestamp": "2025-08-27T05:57:20.18359"
}
```