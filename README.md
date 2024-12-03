
# Документация к домашке

Выполнено 4.5 задания (5 задание выполнено наполовину, поиск есть, но без использования Hibernate 
Search). 

## 1. Получить список всех машин с помощью Entity Graph и возможностью сортировки по цене 
**GET** `/car-api/get-all/graph?priceOrder=DESC`

### Request Parameters (необязательны):
- `priceOrder`: Сортировка по цене (ASC или DESC)

### Response Body (JSON):
```json
[
  {
    "id": 5,
    "model": "C-Class",
    "brand": "Mercedes-Benz",
    "manufactureYear": 2023,
    "price": 85000.0,
    "category": {
      "id": 1,
      "name": "Sedan"
    },
    "showroom": {
      "id": 3,
      "name": "Premium Motors",
      "address": "Sunset Blvd, 20"
    }
  },
  {
    "id": 1,
    "model": "X5",
    "brand": "BMW",
    "manufactureYear": 2022,
    "price": 80000.0,
    "category": {
      "id": 2,
      "name": "SUV"
    },
    "showroom": {
      "id": 1,
      "name": "Luxury Cars",
      "address": "Downtown Street, 10"
    }
  }
]
```

## 2. Получить список всех машин с помощью JOIN FETCH 
**GET** `/car-api/get-all/fetch?priceOrder=DESC`

Request Parameters и Response Body совпадает с запросом 1.

## 3. Получить список всех машин с фильтрацией 
**GET** `/car-api/get-all/filters?brand=bmw&minPrice=80000)`

### Request Parameters (необязательны):
- `brand`: Марка машины
- `category`: Тип кузова
- `manufactureYear`: Год сборки
- `minPrice`: Минимальная цена
- `maxPrice`: Максимальная цена

### Response Body (JSON):
```json
[
  {
    "id": 1,
    "model": "X5",
    "brand": "BMW",
    "manufactureYear": 2022,
    "price": 80000.0,
    "category": {
      "id": 2,
      "name": "SUV"
    },
    "showroom": {
      "id": 1,
      "name": "Luxury Cars",
      "address": "Downtown Street, 10"
    }
  }
]
```

## 4. Добавить машину 
**POST** `/car-api/add-car`

### Request Body (JSON):
```json
{
    "model": "M3 G82",
    "brand": "BMW",
    "manufactureYear": 2024,
    "price": 83000.0
}
```

### Response Body (JSON):
```json
{
  "id": 6,
  "model": "M3 G82",
  "brand": "BMW",
  "manufactureYear": 2024,
  "price": 83000.0,
  "category": null,
  "showroom": null
}
```

## 5. Привязать машину к автосалону 
**POST** `/car-api/assign-showroom/car={carId}&showroom={showroomId}`

### Path Variables (обязательны):
- `carId`: Идентификатор машины
- `showroomId`: Идентификатор автосалона

### Response Body (JSON):
```json
{
  "id": 6,
  "model": "M3 G82",
  "brand": "BMW",
  "manufactureYear": 2024,
  "price": 83000.0,
  "category": null,
  "showroom": {
    "id": 1,
    "name": "Luxury Cars",
    "address": "Downtown Street, 10"
  }
}
```

## 5. Привязать машину к типу кузова 
**POST** `/car-api/assign-category/car={carId}&category={categoryId}`

### Path Variables (обязательны):
- `carId`: Идентификатор машины
- `categoryId`: Идентификатор типа кузова

### Response Body (JSON):
```json
{
  "id": 6,
  "model": "M3 G82",
  "brand": "BMW",
  "manufactureYear": 2024,
  "price": 83000.0,
  "category": {
    "id": 1,
    "name": "Sedan"
  },
  "showroom": {
    "id": 1,
    "name": "Luxury Cars",
    "address": "Downtown Street, 10"
  }
}
```

## 6. Получить список всех машин с пагинацией 
**GET** `/car-api/get-all/pagination?page=3&pageSize=2`

### Request Parameters (обязательны):
- `page`: Номер страницы
- `pageSize`: Размер страницы

### Response Body (JSON):
```json
[
  {
    "id": 5,
    "model": "C-Class",
    "brand": "Mercedes-Benz",
    "manufactureYear": 2023,
    "price": 85000.0,
    "category": {
      "id": 1,
      "name": "Sedan"
    },
    "showroom": {
      "id": 3,
      "name": "Premium Motors",
      "address": "Sunset Blvd, 20"
    }
  },
  {
    "id": 6,
    "model": "M3 G82",
    "brand": "BMW",
    "manufactureYear": 2024,
    "price": 83000.0,
    "category": {
      "id": 1,
      "name": "Sedan"
    },
    "showroom": {
      "id": 1,
      "name": "Luxury Cars",
      "address": "Downtown Street, 10"
    }
  }
]
```
## 7. Получить список всех клиентов 
**GET** `/client-api/get-all`

### Response Body (JSON):
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "contacts": [
      "chillguy@gmail.com",
      "+375291111111"
    ],
    "registrationDate": "2023-01-15",
    "cars": [
      {
        "id": 1,
        "model": "X5",
        "brand": "BMW",
        "manufactureYear": 2022,
        "price": 80000.0,
        "category": {
          "id": 2,
          "name": "SUV"
        },
        "showroom": {
          "id": 1,
          "name": "Luxury Cars",
          "address": "Downtown Street, 10"
        }
      }
    ]
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "contacts": [
      "+375292222222"
    ],
    "registrationDate": "2023-02-10",
    "cars": [
      {
        "id": 2,
        "model": "Corolla",
        "brand": "Toyota",
        "manufactureYear": 2020,
        "price": 20000.0,
        "category": {
          "id": 1,
          "name": "Sedan"
        },
        "showroom": {
          "id": 2,
          "name": "Budget Auto",
          "address": "Main Avenue, 5"
        }
      }
    ]
  }
]
```

## 8. Добавить клиента 
**POST** `/client-api/add-client`

### Request Body (JSON):
```json
{
  "name": "Pavel Kazachenko",
  "contacts": ["contact1@gmail.com", "+375777777777"]
}
```

### Response Body (JSON):
```json
{
  "id": 6,
  "name": "Pavel Kazachenko",
  "contacts": [
    "+375777777777",
    "contact1@gmail.com"
  ],
  "registrationDate": "2024-12-01",
  "cars": []
}
```

## 9. Привязать машину к клиенту 
**POST** `/client-api/buy-car/client={clientId}&car={carId}`

### Path Variables (обязательны):
- `clientId`: Идентификатор клиента
- `carId`: Идентификатор машины

### Response Body (JSON):
```json
{
  "id": 6,
  "name": "Pavel Kazachenko",
  "contacts": [
    "+375777777777",
    "contact1@gmail.com"
  ],
  "registrationDate": "2024-12-01",
  "cars": [
    {
      "id": 6,
      "model": "M3 G82",
      "brand": "BMW",
      "manufactureYear": 2024,
      "price": 83000.0,
      "category": {
        "id": 1,
        "name": "Sedan"
      },
      "showroom": {
        "id": 1,
        "name": "Luxury Cars",
        "address": "Downtown Street, 10"
      }
    }
  ]
}
```

## 10. Получить список всех отзывов 
**GET** `/review-api/get-all`

### Response Body (JSON):
```json
[
  {
    "id": 1,
    "text": "Excellent car!",
    "rating": 5,
    "client": {
      "id": 1,
      "name": "John Doe",
      "contacts": [
        "chillguy@gmail.com",
        "+375291111111"
      ],
      "registrationDate": "2023-01-15",
      "cars": [
        {
          "id": 1,
          "model": "X5",
          "brand": "BMW",
          "manufactureYear": 2022,
          "price": 80000.0,
          "category": {
            "id": 2,
            "name": "SUV"
          },
          "showroom": {
            "id": 1,
            "name": "Luxury Cars",
            "address": "Downtown Street, 10"
          }
        }
      ]
    }
  }
]
```

## 11. Поиск отзывов по тексту и рейтингу 
**GET** `GET /review-api/get-all/search?keyword=reliable&rating=4`

### Request Parameters (обязательны):
- `keyword`: Ключевое слово, или несколько слов
- `rating`: Рейтинг

### Response Body (JSON):
```json
[
  {
    "id": 2,
    "text": "Very reliable.",
    "rating": 4,
    "client": {
      "id": 2,
      "name": "Jane Smith",
      "contacts": [
        "+375292222222"
      ],
      "registrationDate": "2023-02-10",
      "cars": [
        {
          "id": 2,
          "model": "Corolla",
          "brand": "Toyota",
          "manufactureYear": 2020,
          "price": 20000.0,
          "category": {
            "id": 1,
            "name": "Sedan"
          },
          "showroom": {
            "id": 2,
            "name": "Budget Auto",
            "address": "Main Avenue, 5"
          }
        }
      ]
    },
    "car": {
      "id": 2,
      "model": "Corolla",
      "brand": "Toyota",
      "manufactureYear": 2020,
      "price": 20000.0,
      "category": {
        "id": 1,
        "name": "Sedan"
      },
      "showroom": {
        "id": 2,
        "name": "Budget Auto",
        "address": "Main Avenue, 5"
      }
    }
  }
]
```

## 12. Добавить отзыв 
**POST** `/review-api/add-review/client={clientId}/car={carId}`

### Request Parameters (обязательны):
- `clientId`: Идентификатор клиента
- `carId`: Идентификатор машины

### Request Body (JSON):
```json
{
  "text": "Top",
  "rating": 5
}
```

### Response Body (JSON):
```json
{
  "id": 7,
  "text": "Top",
  "rating": 5,
  "client": {
    "id": 1,
    "name": "John Doe",
    "contacts": [
      "chillguy@gmail.com",
      "+375291111111"
    ],
    "registrationDate": "2023-01-15",
    "cars": [
      {
        "id": 1,
        "model": "X5",
        "brand": "BMW",
        "manufactureYear": 2022,
        "price": 80000.0,
        "category": {
          "id": 2,
          "name": "SUV"
        },
        "showroom": {
          "id": 1,
          "name": "Luxury Cars",
          "address": "Downtown Street, 10"
        }
      }
    ]
  },
  "car": {
    "id": 1,
    "model": "X5",
    "brand": "BMW",
    "manufactureYear": 2022,
    "price": 80000.0,
    "category": {
      "id": 2,
      "name": "SUV"
    },
    "showroom": {
      "id": 1,
      "name": "Luxury Cars",
      "address": "Downtown Street, 10"
    }
  }
}
```