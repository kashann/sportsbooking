# SportsBooking - Booking app for extreme sports tourism
![SportsBooking logo](https://github.com/kashann/sportsbooking/blob/master/src/main/resources/static/resources/images/sports-booking-logo-black.png?raw=true)
## REST API + Web MVC implementation

## Implementation Details
In such locations, where extreme sports are practiced, there are periods of the year when these activities can be performed (Ski December-February, Kiting May-August, ATV all year round, etc.). 
Each spot/location usually has an average cost per day.

The spots have a hierarchical structure and contain the country, region and city. 
This will permit anyone to look for a sky holiday in Romania, or maybe in Brasov region, or maybe just in Predeal city, between February and March. 

I implemented a REST API, that contains: endpoints through which the administrator (using a web application, which supposedly already exists) can ADD, REMOVE, UPDATE, GET a location and all its information.

I also implemented an endpoint which allows the users (from a mobile app, which supposedly already exists) to look for the best places where extreme sports can be performed. 
The users can specify:
-	sports – one or more 
-	period – selecting a period of the year (01 November -> 15 November)

In the mobile app, the response is shown from the endpoint and is an ordered list based on the cost estimated for the entire stay. 
Every location returned in the above-mentioned list contains the name, the sports available there and the cost. 

-----------------------------------
## Starting the app
* Right click on SportsBookingApplication -> run ...main()
* On top bar, click on SportsBookingApplication
* Edit Configurations
* Add Maven Goal to Before Launch section `generate-resources`
* Hit RUN

-----------------------------------
## View DataBase

Navigate to `http://localhost:9090/h2-console/`

-----------------------------------
## Web MVC testing

Navigate to `http://localhost:9090/`

-----------------------------------

## REST API testing in Postman:

1. Getting all locations:
   ```
   (GET) /api/locations
   ```
   Response:
   ```
    [
        {
            "id": 1,
            "name": "Cabana Susai",
            "town": {
                "id": 3,
                "name": "Predeal"
            },
            "sports": [
                {
                    "id": 1,
                    "name": "SKI",
                    "startDate": "2021-12-01",
                    "endDate": "2022-03-01",
                    "avgCostPerDay": 180.0
                },
                {
                    "id": 3,
                    "name": "ATV",
                    "startDate": "2021-01-01",
                    "endDate": "2022-01-01",
                    "avgCostPerDay": 200.0
                },
                {
                    "id": 5,
                    "name": "SNOWBOARDING",
                    "startDate": "2021-12-01",
                    "endDate": "2022-03-01",
                    "avgCostPerDay": 160.0
                },
                {
                    "id": 11,
                    "name": "SKI",
                    "startDate": "2021-06-01",
                    "endDate": "2021-07-11",
                    "avgCostPerDay": 123.4
                }
            ]
        },
        {
            "id": 2,
            "name": "Bunloc",
            "town": {
                "id": 4,
                "name": "Sacele"
            },
            "sports": [
                {
                    "id": 2,
                    "name": "PARAGLIDING",
                    "startDate": "2021-05-01",
                    "endDate": "2021-09-01",
                    "avgCostPerDay": 260.0
                },
                {
                    "id": 4,
                    "name": "DOWNHILL",
                    "startDate": "2021-04-01",
                    "endDate": "2021-11-01",
                    "avgCostPerDay": 20.0
                }
            ]
        },
        {
            "id": 3,
            "name": "Angertal",
            "town": {
                "id": 5,
                "name": "Bad Hofgastein"
            },
            "sports": [
                {
                    "id": 6,
                    "name": "SKI",
                    "startDate": "2021-12-19",
                    "endDate": "2022-04-02",
                    "avgCostPerDay": 320.0
                },
                {
                    "id": 7,
                    "name": "SNOWBOARDING",
                    "startDate": "2021-12-19",
                    "endDate": "2022-04-02",
                    "avgCostPerDay": 290.0
                }
            ]
        },
        {
            "id": 4,
            "name": "TNT Brothers Skydiving",
            "town": {
                "id": 2,
                "name": "Clinceni"
            },
            "sports": [
                {
                    "id": 8,
                    "name": "SKYDIVING",
                    "startDate": "2021-04-01",
                    "endDate": "2022-10-31",
                    "avgCostPerDay": 850.0
                },
                {
                    "id": 9,
                    "name": "PARAGLIDING",
                    "startDate": "2021-04-01",
                    "endDate": "2022-10-31",
                    "avgCostPerDay": 380.0
                }
            ]
        },
        {
            "id": 5,
            "name": "Blue Beach",
            "town": {
                "id": 1,
                "name": "Mamaia"
            },
            "sports": [
                {
                    "id": 10,
                    "name": "KITEBOARDING",
                    "startDate": "2021-06-01",
                    "endDate": "2022-09-15",
                    "avgCostPerDay": 250.0
                }
            ]
        }
    ]
   ```

2. Getting a specific location by its id:
   ```
   (GET) /api/locations/2
   ```
   Response:
   ```
    {
        "id": 4,
        "name": "TNT Brothers Skydiving",
        "town": {
            "id": 2,
            "name": "Clinceni"
        },
        "sports": [
            {
                "id": 8,
                "name": "SKYDIVING",
                "startDate": "2021-04-01",
                "endDate": "2022-10-31",
                "avgCostPerDay": 850.0
            },
            {
                "id": 9,
                "name": "PARAGLIDING",
                "startDate": "2021-04-01",
                "endDate": "2022-10-31",
                "avgCostPerDay": 380.0
            }
        ]
    }
   ```

3. Adding a new location:
   ```
   (POST) /api/locations/new
   (requestBody)
   {
       "name": "Navodari kiteboarding",
       "town": {
           "id": "1",
           "name": "Mamaia"
       }
   }
   ```
   Response:
   ```
   {
       "id": 6,
       "name": "Navodari kiteboarding",
       "town": {
           "id": 1,
           "name": "Mamaia"
       },
       "sports": []
   }
   ```

4. Updating a specific location:
   ```
   (PUT) /api/locations/1
   (requestBody)
    {
        "id": 1,
        "name": "Predeal Slope",
        "town": {
            "id": 3,
            "name": "Predeal"
        },
        "sports": [
            {
                "id": 1,
                "name": "SKI",
                "startDate": "2021-12-01",
                "endDate": "2022-03-01",
                "avgCostPerDay": 180.0
            },
            {
                "id": 3,
                "name": "ATV",
                "startDate": "2021-01-01",
                "endDate": "2022-01-01",
                "avgCostPerDay": 200.0
            },
            {
                "id": 5,
                "name": "SNOWBOARDING",
                "startDate": "2021-12-01",
                "endDate": "2022-03-01",
                "avgCostPerDay": 160.0
            }
        ]
    }
   ```
   Response:
   ```
   {
       "id": 1,
       "name": "Predeal Slope",
       "town": {
           "id": 1,
           "name": "Mamaia"
       },
       "sports": [
           {
               "id": 1,
               "name": "SKI",
               "startDate": "2021-12-01",
               "endDate": "2022-03-01",
               "avgCostPerDay": 180.0
           },
           {
               "id": 3,
               "name": "ATV",
               "startDate": "2021-01-01",
               "endDate": "2022-01-01",
               "avgCostPerDay": 200.0
           },
           {
               "id": 5,
               "name": "SNOWBOARDING",
               "startDate": "2021-12-01",
               "endDate": "2022-03-01",
               "avgCostPerDay": 160.0
           }
       ]
   }
   ```

5. Removing a specific location:
   ```
   (DELETE) /api/locations/4
   ```

6. Get a sport by its ID:
   ```
   (GET) /api/locations/5/sports/10
   ```
   Response:
   ```
    {
        "id": 10,
        "name": "KITEBOARDING",
        "startDate": "2021-06-01",
        "endDate": "2022-09-15",
        "avgCostPerDay": 250.0
    }
   ```
   
7. Add a new sport to a location:
   ```
   (POST) /api/locations/5/sports/new
   (requestBody)
   {
       "id": null,
       "name": "PARAGLIDING",
       "startDate": "2021-07-01",
       "endDate": "2022-10-15",
       "avgCostPerDay": 350.0
   }
   ```
   Response:
   ```
    {
        "id": 11,
        "name": "PARAGLIDING",
        "startDate": "2021-07-01",
        "endDate": "2022-10-15",
        "avgCostPerDay": 350.0
    }
   ```
   
8. Modifying a sport:
   ```
   (PUT) /api/locations/5/sports/10
   (requestBody)
    {
        "id": 10,
        "name": "KITEBOARDING",
        "startDate": "2021-06-15",
        "endDate": "2022-09-30",
        "avgCostPerDay": 280.0
    }
   ```
   Response:
   ```
    {
        "id": 10,
        "name": "KITEBOARDING",
        "startDate": "2021-06-15",
        "endDate": "2022-09-30",
        "avgCostPerDay": 280.0
    }
   ```
   
9. Removing a specific sport:
   ```
   (DELETE) /api/locations/5/sports/10
   ```
   
10. Searching for locations based on 1 sport and a period:
    ```
    (GET) /api/locations/search?sports=DOWNHILL&from=2021-10-01&to=2021-10-15
    ```
    Response:
    ```
    [
        {
            "id": 2,
            "locationName": "Bunloc",
            "townName": "Sacele",
            "sports": [
                {
                    "id": 4,
                    "name": "DOWNHILL",
                    "startDate": "2021-04-01",
                    "endDate": "2021-11-01",
                    "avgCostPerDay": 20.0
                }
            ]
        }
    ]
    ```

11. Searching for locations based on 2 or more sports and a period:
    ```
    (GET) /api/locations/search?sports=DOWNHILL,ATV&from=2021-10-01&to=2021-10-15
    ```
    Response:
    ```
    [
        {
            "id": 1,
            "locationName": "Cabana Susai",
            "townName": "Predeal",
            "sports": [
                {
                    "id": 3,
                    "name": "ATV",
                    "startDate": "2021-01-01",
                    "endDate": "2022-01-01",
                    "avgCostPerDay": 200.0
                }
            ]
        },
        {
            "id": 2,
            "locationName": "Bunloc",
            "townName": "Sacele",
            "sports": [
                {
                    "id": 4,
                    "name": "DOWNHILL",
                    "startDate": "2021-04-01",
                    "endDate": "2021-11-01",
                    "avgCostPerDay": 20.0
                }
            ]
        }
    ]
    ```

12. Searching for locations based on 2 or more sports, a period and sorting descending by avg price (ascending also possible):
    ```    
    (GET) /api/locations/search?sports=SKI,SNOWBOARDING&from=2022-01-10&to=2022-01-15&sort=DESC
    ```
    Response:
    ```
    [
        {
            "id": 3,
            "locationName": "Angertal",
            "townName": "Bad Hofgastein",
            "sports": [
                {
                    "id": 6,
                    "name": "SKI",
                    "startDate": "2021-12-19",
                    "endDate": "2022-04-02",
                    "avgCostPerDay": 320.0
                },
                {
                    "id": 7,
                    "name": "SNOWBOARDING",
                    "startDate": "2021-12-19",
                    "endDate": "2022-04-02",
                    "avgCostPerDay": 290.0
                }
            ]
        },
        {
            "id": 1,
            "locationName": "Cabana Susai",
            "townName": "Predeal",
            "sports": [
                {
                    "id": 1,
                    "name": "SKI",
                    "startDate": "2021-12-01",
                    "endDate": "2022-03-01",
                    "avgCostPerDay": 180.0
                },
                {
                    "id": 5,
                    "name": "SNOWBOARDING",
                    "startDate": "2021-12-01",
                    "endDate": "2022-03-01",
                    "avgCostPerDay": 160.0
                }
            ]
        }
    ]
    ```
