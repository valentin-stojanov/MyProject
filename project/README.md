# My Project README

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
    - [Weather Forecast](#weather-forecast)
    - [Registration](#registration)
    - [Login](#login)
    - [Adding a New Route](#adding-a-new-route)
    - [Routes](#routes)
    - [Route Details](#route-details)
    - [Profile](#profile)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Introduction

Welcome to My Project! This README provides an overview of the project and its main features.

My Project is a web application (online at https://www.routetales.eu/) that offers several features to its users, including user registration (with options to use Google or GitHub accounts) and login, password reset, route management, route details viewing, and user profile.

## Features

### [Weather Forecast](https://www.routetales.eu/)

At the homepage, without the need for registration, you can access the weather forecast for the current day and the next three days. You can use an interactive map to select a location, use your current location, or provide latitude and longitude to get the weather forecast for that point. The weather forecast data is retrieved from [OpenWeather](https://openweathermap.org/api).

### [Registration](https://www.routetales.eu/users/register)

To register, simply fill in the required fields and click the register button. After successful registration, you will receive a confirmation email in your inbox.

### [Login](https://www.routetales.eu/users/login)

You can log in by providing your credentials, or for an easier way, click on one of the Google or GitHub icons. You will be asked for permission to use your publicly available account information to register if you don't have an account, or to log in if you do. You can also reset your password by clicking on "Forgot password?" and following the instructions.

Here's how password reset works:

1. Click on "Forgot password?" and provide your email address.

2. After submitting your email (if it's correct), you will receive an email with a link to reset your password.

3. Click on the link to access the password reset page and set a new password.

### [Adding a New Route](https://www.routetales.eu/routes/add) (registration needed)

Here, users can add their routes. They need to specify the name, description, choose the difficulty level, and route category, and attach a [GPX file](https://www.topografix.com/gpx.asp) containing the actual route.

### [Routes](https://www.routetales.eu/routes) (registration needed)

The "Routes" feature enables users to view a list of routes available in the system. Key points about this feature include:

- Users can see a paginated list of routes.
- Navigation buttons allow users to navigate between pages of routes.
- Click the "Learn more" button to view additional information about a specific route.

### Route Details (registration needed)

The "Route Details" feature provides detailed information about a specific route. Key features include:

- Display of route name, author, description, and difficulty level.
- Interactive map with different layers and a visualized route.
- Image gallery associated with the route.
- Option for image upload.
- User comments section.

## Running the Application Locally

### Prerequisites

Before you get started, ensure you have the following:

1. An installed IDE (e.g., IntelliJ, Visual Studio Code).
2. JDK 17 or above.
3. PostgreSQL or Docker Compose.

If you have PostgreSQL installed, create a new database named `myproject`. If you have Docker Compose installed, create a YAML file containing this:

```yaml
services:
  postgres:
    container_name: postgres
    image: postgres:latest
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=root
      - POSTGRES_DB=myproject
    ports:
      - "5432:5432"
    restart: always

  pgadmin:
    container_name: pgadmin
    image: dpage/pgadmin4:latest
    environment:
      - PGADMIN_DEFAULT_EMAIL=test@email.com
      - PGADMIN_DEFAULT_PASSWORD=root
    ports:
      - "5050:80"
    restart: always
```

When you run `$ docker compose up -d` in the folder containing the YAML file, you will have two services running: the PostgreSQL server and pgAdmin on [http://localhost:5050/](http://localhost:5050/). You can log in with the following credentials:

- Username: `test@email.com`
- Password: `root`

After that, click on "Add New Server" -> "General" -> in the "Name" field, type: `MyProject`.

On the "Connection" tab:

- In the "Host name/address" field, type: `postgres`.
- In the "Port" field, type: `5432`.
- In the "Username" field, type: `postgres`.
- In the "Password" field, type: `root`.

Click "Save".

To stop the containers, run `$ docker compose down -v`.

### Running My Project

Follow these steps to run My Project:

1. Clone the project repository: `git clone https://github.com/valentin-stojanov/MyProject`.
2. Open the containing folder with your IDE.
3. Start the application.
4. Access the application in your web browser at [http://localhost:8080](http://localhost:8080).

## Usage

To use the application, follow the instructions provided for each feature in the respective sections above.

