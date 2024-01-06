# Restaurant & Cuisine Management System

## Overview

This system is designed to manage restaurants and cuisines, leveraging Spring Boot to create a robust API. It supports operations such as importing data for restaurants and cuisines via CSV files and searching for restaurants based on certain criteria.

## Features

- RESTful endpoints for creating and searching restaurants and cuisines.
- CSV import functionality for batch processing of restaurant and cuisine data.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 11+
- Gradle
- H2

### Installation

1. Clone the repository to your local machine:

git clone https://github.com/gustavoraphael/kotlin-first-one.git

2. Load the initial data into your database using the CSV files provided in `src/main/resources/csv`.

### Running the Application

Use Gradle to run the application:

```./gradlew bootRun```


## Usage

### Importing Data

The application allows you to import restaurants and cuisines data through CSV files using the following API endpoints:

- **Import Cuisines**: `POST /api/cuisines/upload`
    - Use the `cuisines.csv` file located at `src/main/resources/csv/cuisines.csv` for importing cuisines.

- **Import Restaurants**: `POST /api/restaurants/upload`
    - Use the `restaurants.csv` file located at `src/main/resources/csv/restaurants.csv` for importing restaurants.

You can use tools such as cURL or Postman to send a multipart file upload request to these endpoints.

### Searching for Restaurants

The application provides an endpoint to search for restaurants based on various criteria:

- **Search Restaurants**: `GET /api/restaurants/search`

## Running Tests

Execute the unit tests with:

``./gradlew test``

## Built With

- Spring Boot - The web framework used
- Gradle - Dependency Management
- H2 - The database used
