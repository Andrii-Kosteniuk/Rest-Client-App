# CEPIK Vehicle Lookup App

A web-based application built using 
**Spring Framework** and 
**Vaadin** that allows users 
to fetch and view information about 
vehicles registered in Poland, 
using data from the CEPIK API.


## 📜 Table of Contents

* [Features](#-features)
* [Prerequisites](#prerequisites)
* [Steps](#steps)
* [Technologies used](#-technologies-used)
* [Examples](#-examples)

## ✨ Features

- Search for vehicles registered in Poland via CEPIK API
- Display vehicle information in a clean and interactive UI
- Responsive design using Vaadin
- Structured error handling and logging

## Prerequisites
- Java 17+
- Maven
- Internet access for CEPIK API calls

## Steps

Clone the Repository

```
git clone https://github.com/Andrii-Kosteniuk/Rest-Client-App
```

Run the Application

```bash
mvn clean install
```
```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

## 🛠 Technologies Used

- Spring Framework (Spring Boot, RestClient)
- Vaadin framework 
- Jackson
- Logging (SLF4J)

## 📸 Examples

1.Home page
![Home-page](src/main/resources/static/images/Home-page.jpg)


2.Search vehicle page
![Search-vehicle-page](src/main/resources/static/images/Search-page.jpg)

3.Chose a province
![Province](src/main/resources/static/images/Chose-province.jpg)

4.Chose a date from and type of returned date
![Chose-date](src/main/resources/static/images/Chose-data-and-type.jpg)

5.Retrieved data from central API Cepik
![Data](src/main/resources/static/images/Retrieved-data.jpg)

6.Filtering data
![Filter-data](src/main/resources/static/images/Filtering.jpg)

7.Information about data
![Info](src/main/resources/static/images/Information.jpg)

9.My contact
![Contact](src/main/resources/static/images/Contact.jpg)

