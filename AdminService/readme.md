# List Api For Admin Service

## Create book:
```shell
curl --location 'http://localhost:8082/api/books' \
--header 'Content-Type: application/json' \
--data '{
    "title": "New",
    "genre": "Hihi",
    "publisher": "quyenqyen",
    "avgRatings": 2.0,
    "ratingCount": 5,
    "language": "Eng"
}'
```

## Get all books:
```shell
curl --location 'http://localhost:8082/api/books'
```

## Update book:
```shell
curl --location --request PUT 'http://localhost:8082/api/books' \
--header 'Content-Type: application/json' \
--data '{
    "id": 4,
    "title": "hello"
}'
```

## Delete book:
```shell
curl --location --request DELETE 'http://localhost:8082/api/books/8'
```