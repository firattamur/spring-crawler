# 🕷️ Spring-Crawler

Spring-Crawler is a fun showcase project demonstrating web crawling capabilities for products from hepsiburada.com, with data persistence in a PostgreSQL database.

## 🚀 Features

- Crawl product data from hepsiburada.com
- Save product information to PostgreSQL database
- Distributed crawling using Redis for message queuing
- RESTful API endpoints for submitting URLs and checking product status

## 🛠️ Tech Stack

- Spring Boot
- PostgreSQL
- Redis
- Java
- Docker

## 🔄 How It Works

1. Submit URLs through the `/submit` endpoint
2. Workers listen for messages via Redis
3. When a message arrives, workers:
   - Read the product URL from the database
   - Parse the product data
   - Save the updated information to the database
4. Check product status using the `/product` endpoint

## 📊 Product Statuses

- PENDING: URL submitted, waiting to be processed
- IN_PROGRESS: Currently being crawled
- COMPLETED: Crawling finished, data saved
- FAILED: Crawling failed, unable to save data

## 🔗 API Endpoints

1. `/submit`: Submit a product URL for crawling
   - Method: POST
   - Body: `{ "url": "https://www.hepsiburada.com/product-url" }`

2. `/product`: Get product crawling status
   - Method: GET
   - Query Param: `id` (product ID)

## 🚀 Getting Started

1. Clone the repository
2. Run `docker-compose up` to start the PostgreSQL and Redis containers
3. Run the Spring Boot application
4. Submit a product URL using the `/submit` endpoint
5. Check the product status using the `/product` endpoint

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📝 License

This project is licensed under the MIT License.