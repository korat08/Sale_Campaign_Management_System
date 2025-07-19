📊 Sales Campaign Management System
A backend application built with Spring Boot and MySQL for managing product discount campaigns in an e-commerce system. This project allows admin users to create, update, and manage time-bound promotional campaigns, track price history, apply discounts to product listings, and provide filtered views of active campaign products. It supports secure login with JWT authentication and pagination support for efficient product handling.

🚀 Features
✅ Authentication & Authorization
JWT-based Authentication

Role-based Access: Admin and User roles

Protected API endpoints using Spring Security

🗂️ Product Management
CRUD operations on Products

Pagination and sorting of product listings

Product view with and without active campaign pricing

📉 Campaign Management System
Admins can create, update, and delete promotional Campaigns.
Each Campaign includes:

Campaign name

Campaign start & end date

Discount percentage

Associated products

🔁 Price History Tracking
Tracks original price and discounted price during campaigns

Maintains historical records of price changes

Allows rollback and auditability of campaign pricing

📅 Scheduled Task Support
Scheduled cleanup for expired campaigns

Auto-update product prices after campaign expiry

📦 Campaign-Aware Product Listing
Real-time discount application for products in active campaigns

Products without campaigns return original prices

Campaign details shown alongside products if applicable

🧰 Tech Stack
Layer	Technology
Language	Java
Framework	Spring Boot
Security	Spring Security + JWT
Database	MySQL
ORM	JPA (Hibernate)
Build Tool	Maven
IDE	IntelliJ / STS
Scheduling	Spring @Scheduled
Pagination	Spring Data JPA Pageable

🧱 Project Structure
🔐 Auth Module
JWT-based authentication and token generation

Role validation and route protection

Login and Signup APIs

🛍️ Product Module
CRUD APIs for Products and Categories

Pagination and filtering support

Price and quantity tracking

🎯 Campaign Module
Create/Update/Delete campaigns

Associate multiple products with campaigns

Start and end date validation

Prevent duplicate campaign assignment

📜 Price History Module
Maintains a log of all product price updates

Captures price before, during, and after campaigns

⏰ Scheduled Campaign Handler
Auto-checks for expired campaigns using @Scheduled

Resets product price to original after campaign ends

Removes expired campaign-product mappings

📈 Example Workflow
Admin logs in using JWT authentication.

Admin creates a product and assigns a category.

Admin creates a campaign with a start and end date, and sets the discount percentage.

System applies discount prices to all associated products.

Products now reflect discounted prices in listing APIs.

After campaign ends, a scheduled job removes discounts and restores original prices.

All price changes are recorded in price history for each product.

📋 Key Business Logic
A product can only be part of one active campaign at a time.

Campaign dates must be future-valid and logically correct (end after start).

Rollback logic ensures consistency in product pricing even after campaign expiration.

Discounted price = originalPrice - (originalPrice * discountPercentage / 100)

🧑‍💻 Developer Info
Korat Aryan
Backend Developer | Java | Spring Boot | MySQL
📧 Email: aryankorat08@gmail.com
🔗 GitHub: github.com/korat08