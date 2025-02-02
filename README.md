# Books Management #

## How to start the project

### Database setup
- start mysql server
```
docker compose -f docker-compose-mysql.yml up -d
```
- create table for books management
```
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    INDEX idx_author (author),
    UNIQUE KEY unique_title_author (title, author)
);
```

### Running api server
- build docker image
```
docker build -t books-management:dev .
```
- start container
```
docker-compose -f docker-compose-api.yml up -d
```

### Running integration tests
```
mvn -Dtest=BookControllerTest test
```

### Example api
**endpoint**: `GET /books?author=Author Name&page=0&size=10`

- response:
```json
{
  "status": {
    "code": "2000",
    "message": "Success"
  },
  "data": [
    {
      "id": 1,
      "title": "Title Name",
      "author": "Author Name",
      "publishedDate": "2568-02-01"
    }
  ],
  "paging": {
    "itemCount": 10,
    "totalItems": 1,
    "totalPages": 1,
    "currentPage": 0
  }
}
```

**endpoint**: `POST /books`

- request:
```json
{
    "title": "Title Name",
    "author": "Author Name",
    "publishedDate": "2568-02-01"
}
```
- response:
```json
{
    "id": "1",
    "title": "Title Name",
    "author": "Author Name",
    "publishedDate": "2568-02-01"
}
```

* Note: if you're running on local you can go test a services at http://localhost:8080/swagger-ui/index.html#
