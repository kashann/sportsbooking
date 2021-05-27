# REST API for sport locations (to be continued with MVC + Thymeleaf...)

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

## Testing in Postman:

1. Getting all locations:
   ```
   (GET) /api/locations
   ```
   Response:
   ```
   {
       "locations": [
           {
               "id": 1,
               "name": "Predeal slope",
               "town": {
                   "id": 2,
                   "name": "Predeal"
               },
               "sports": [
                   {
                       "id": 3,
                       "name": "ATV",
                       "startDate": [
                           2020,
                           1,
                           1
                       ],
                       "endDate": [
                           2021,
                           1,
                           1
                       ],
                       "avgCostPerDay": 200.0
                   },
                   {
                       "id": 5,
                       "name": "SNOWBOARDING",
                       "startDate": [
                           2020,
                           12,
                           1
                       ],
                       "endDate": [
                           2021,
                           3,
                           1
                       ],
                       "avgCostPerDay": 160.0
                   },
                   {
                       "id": 1,
                       "name": "SKI",
                       "startDate": [
                           2020,
                           12,
                           1
                       ],
                       "endDate": [
                           2021,
                           3,
                           1
                       ],
                       "avgCostPerDay": 180.0
                   }
               ]
           },
           {
               "id": 2,
               "name": "Bunloc",
               "town": {
                   "id": 1,
                   "name": "Sacele"
               },
               "sports": [
                   {
                       "id": 2,
                       "name": "PARAGLIDING",
                       "startDate": [
                           2020,
                           5,
                           1
                       ],
                       "endDate": [
                           2020,
                           9,
                           1
                       ],
                       "avgCostPerDay": 260.0
                   },
                   {
                       "id": 4,
                       "name": "DOWNHILL",
                       "startDate": [
                           2020,
                           4,
                           1
                       ],
                       "endDate": [
                           2020,
                           11,
                           1
                       ],
                       "avgCostPerDay": 20.0
                   }
               ]
           },
           {
               "id": 3,
               "name": "Angertal",
               "town": {
                   "id": 3,
                   "name": "Bad Hofgastein"
               },
               "sports": [
                   {
                       "id": 6,
                       "name": "SKI",
                       "startDate": [
                           2020,
                           12,
                           19
                       ],
                       "endDate": [
                           2021,
                           4,
                           2
                       ],
                       "avgCostPerDay": 320.0
                   },
                   {
                       "id": 7,
                       "name": "SNOWBOARDING",
                       "startDate": [
                           2020,
                           12,
                           19
                       ],
                       "endDate": [
                           2021,
                           4,
                           2
                       ],
                       "avgCostPerDay": 290.0
                   }
               ]
           }
       ]
   }
   ```

2. Getting a specific location by its id:
   ```
   (GET) /api/locations/2
   ```
   Response:
   ```
   {
       "id": 2,
       "name": "Bunloc",
       "town": {
           "id": 1,
           "name": "Sacele"
       },
       "sports": [
           {
               "id": 2,
               "name": "PARAGLIDING",
               "startDate": [
                   2020,
                   5,
                   1
               ],
               "endDate": [
                   2020,
                   9,
                   1
               ],
               "avgCostPerDay": 260.0
           },
           {
               "id": 4,
               "name": "DOWNHILL",
               "startDate": [
                   2020,
                   4,
                   1
               ],
               "endDate": [
                   2020,
                   11,
                   1
               ],
               "avgCostPerDay": 20.0
           }
       ]
   }
   ```

3. Adding a new location:
   ```
   (POST) /api/locations/new
   (requestBody)
   {
       "name": "TNT Brothers",
       "town": {
           "name": "Clinceni"
       },
       "sports": [
           {
               "name": "PARAGLIDING",
               "startDate": [
                   2020,
                   4,
                   1
               ],
               "endDate": [
                   2020,
                   10,
                   1
               ],
               "avgCostPerDay": 850.0
           }
       ]
   }
   ```
   Response:
   ```
   {
       "id": 4,
       "name": "TNT Brothers",
       "town": {
           "id": 4,
           "name": "Clinceni"
       },
       "sports": [
           {
               "id": 8,
               "name": "PARAGLIDING",
               "startDate": [
                   2020,
                   4,
                   1
               ],
               "endDate": [
                   2020,
                   10,
                   1
               ],
               "avgCostPerDay": 850.0
           }
       ]
   }
   ```

4. Updating a specific location (PATCH can also be implemented):
   ```
   (PUT) /api/locations/4
   (requestBody)
   {
       "name": "TNT Brothers Skydiving",
       "town": {
           "name": "Tulcea"
       },
       "sports": [
           {
               "name": "SKYDIVING",
               "startDate": [
                   2020,
                   4,
                   1
               ],
               "endDate": [
                   2020,
                   10,
                   1
               ],
               "avgCostPerDay": 850.0
           }
       ]
   }
   ```
   Response:
   ```
   {
       "id": 4,
       "name": "TNT Brothers Skydiving",
       "town": {
           "id": 5,
           "name": "Tulcea"
       },
       "sports": [
           {
               "id": 9,
               "name": "SKYDIVING",
               "startDate": [
                   2020,
                   4,
                   1
               ],
               "endDate": [
                   2020,
                   10,
                   1
               ],
               "avgCostPerDay": 850.0
           }
       ]
   }
   ```

5. Removing a specific location:
   ```
   (DELETE) /api/locations/4
   ```

6. Searching for locations based on 1 sport and a period:
   ```
   (GET) /api/locations/search?sports=DOWNHILL&from=2021-10-01&to=2021-10-15
   ```
   Response:
   ```
   [
       {
           "locationName": "Bunloc",
           "sports": [
               {
                   "id": 4,
                   "name": "DOWNHILL",
                   "startDate": [
                       2021,
                       4,
                       1
                   ],
                   "endDate": [
                       2021,
                       11,
                       1
                   ],
                   "avgCostPerDay": 20.0
               }
           ]
       }
   ]
   ```

7. Searching for locations based on 2 or more sports and a period:
   ```
   (GET) /api/locations/search?sports=DOWNHILL,ATV&from=2021-10-01&to=2021-10-15
   ```
   Response:
   ```
   [
       {
           "locationName": "Predeal slope",
           "sports": [
               {
                   "id": 3,
                   "name": "ATV",
                   "startDate": [
                       2021,
                       1,
                       1
                   ],
                   "endDate": [
                       2021,
                       1,
                       1
                   ],
                   "avgCostPerDay": 200.0
               }
           ]
       },
       {
           "locationName": "Bunloc",
           "sports": [
               {
                   "id": 4,
                   "name": "DOWNHILL",
                   "startDate": [
                       2020,
                       4,
                       1
                   ],
                   "endDate": [
                       2020,
                       11,
                       1
                   ],
                   "avgCostPerDay": 20.0
               }
           ]
       }
   ]
   ```

8. Searching for locations based on 2 or more sports, a period and sorting descending by avg price (ascending also possible):
   ```    
   (GET) /api/locations/search?sports=SKI,SNOWBOARDING&from=2021-01-10&to=2021-01-15&sort=DESC
   ```
   Response:
   ```
   [
       {
           "locationName": "Angertal",
           "sports": [
               {
                   "id": 6,
                   "name": "SKI",
                   "startDate": [
                       2020,
                       12,
                       19
                   ],
                   "endDate": [
                       2021,
                       4,
                       2
                   ],
                   "avgCostPerDay": 320.0
               },
               {
                   "id": 7,
                   "name": "SNOWBOARDING",
                   "startDate": [
                       2020,
                       12,
                       19
                   ],
                   "endDate": [
                       2021,
                       4,
                       2
                   ],
                   "avgCostPerDay": 290.0
               }
           ]
       },
       {
           "locationName": "Predeal slope",
           "sports": [
               {
                   "id": 1,
                   "name": "SKI",
                   "startDate": [
                       2020,
                       12,
                       1
                   ],
                   "endDate": [
                       2021,
                       3,
                       1
                   ],
                   "avgCostPerDay": 180.0
               },
               {
                   "id": 5,
                   "name": "SNOWBOARDING",
                   "startDate": [
                       2020,
                       12,
                       1
                   ],
                   "endDate": [
                       2021,
                       3,
                       1
                   ],
                   "avgCostPerDay": 160.0
               }
           ]
       }
   ]
   ```
   