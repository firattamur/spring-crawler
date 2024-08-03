# 🕷️ Spring-Crawler

Spring-Crawler is a fun showcase project demonstrating web crawling capabilities for products from [hepsiburada.com](https://www.hepsiburada.com/), with data persistence in a PostgreSQL database.

> PS: This project is for educational purposes only. Please respect the terms of service of the websites you crawl and do not use this project for unethical purposes. 

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
- Swagger

## 🔄 How It Works

1. Submit URLs via the `/submit` endpoint
   - Only URLs from hepsiburada.com are accepted, others are rejected.
   - Duplicate URLs are saved and crawled only once.
   
2. URLs are stored in PostgreSQL and messages are sent to Redis
3. Workers listen for messages from Redis
4. When notified, workers fetch URLs from PostgreSQL and crawl hepsiburada.com
5. Crawled data is saved back to PostgreSQL
6. Users can check product status via the `/product` endpoint

## 📊 Product Statuses

- PENDING: URL submitted, waiting to be processed
- IN_PROGRESS: Currently being crawled
- COMPLETED: Crawling finished, data saved
- FAILED: Crawling failed, unable to save data

## 🔗 API Endpoints

- Swagger UI: http://localhost:8080/swagger-ui.html

1. `/submit`: Submit a product URL for crawling

   - Method: POST
   - Body: `{ "url": "https://www.hepsiburada.com/product-url" }`
   
2. `/product`: Get product crawling status

   - Method: GET
   - Query Param: `id` (product ID)

3. `/products`: Get all products

   - Method: GET

## 🚀 Getting Started

1. Clone the repository
2. Run the Spring Boot application
3. Access the Swagger UI at http://localhost:8080/swagger-ui.html
4. Submit a product URL using the `/submit` endpoint
5. Check the product status using the `/product` endpoint
6. View all products using the `/products` endpoint

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📝 License

This project is licensed under the MIT License.

> **Note:** This is a really simple showcase and proof of concept for web crawling. In large-scale production environments, web crawling becomes significantly more complex. Many additional factors need to be considered, such as:
>
> - Scalability and distributed crawling
> - Respect for robots.txt and crawl-delay directives
> - IP rotation and proxy management
> - Rate limiting guards and anti-scraping measures
> - Data deduplication and storage optimization
> - Error handling and retry mechanisms
> - Legal and ethical considerations
>
> This project serves as a starting point for understanding basic crawling concepts, but real-world implementations require careful planning and additional infrastructure.
