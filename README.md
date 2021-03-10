"Availability-Tracker" 

Database can be created using below DDL
```
CREATE DATABASE availability_track
    WITH
    OWNER = availabilitytrackuser
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

Entities and functions used can be created in availability_track db, using following DDL
```
create table rickshaw (
	id bigserial primary key,
	driver_name character varying,
	registration character varying,
	lat real,
	lng real
);	

CREATE OR REPLACE FUNCTION calculate_distance(lat1 float, lon1 float, lat2 float, lon2 float, units varchar)
RETURNS float AS $dist$
    DECLARE
        dist float = 0;
        radlat1 float;
        radlat2 float;
        theta float;
        radtheta float;
    BEGIN
        IF lat1 = lat2 OR lon1 = lon2
            THEN RETURN dist;
        ELSE
            radlat1 = pi() * lat1 / 180;
            radlat2 = pi() * lat2 / 180;
            theta = lon1 - lon2;
            radtheta = pi() * theta / 180;
            dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);

            IF dist > 1 THEN dist = 1; END IF;

            dist = acos(dist);
            dist = dist * 180 / pi();
            dist = dist * 60 * 1.1515;

            IF units = 'K' THEN dist = dist * 1.609344; END IF;
	 IF units = 'm' THEN dist = dist * 1609.344; END IF;
            IF units = 'N' THEN dist = dist * 0.8684; END IF;

            RETURN dist;
        END IF;
    END;
$dist$ LANGUAGE plpgsql;

```

Application can be started by using fat jar or any IDE required jdk 11 is installed on the machine.
Application will run on default port 8080.

In order to on-board new rickshaw, following curl can be used.
```
curl --location --request POST 'http://localhost:8080/rickshaw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "driverName": "driverName",
    "registration": "registration",
    "lat": 28.704060,
    "lng": 77.102493
}'
```
For this request we get following response.
```
{
    "id": 1,
    "driverName": "driverName",
    "registration": "registration",
    "lat": 28.70406,
    "lng": 77.102493
}
```

In order to get details of all rickshaw along with their live locations, following curl is used
```
curl --location --request GET 'http://localhost:8080'
```
Here we get list of response.
```
[
    {
        "id": 1,
        "driverName": "driverName",
        "registration": "registration",
        "lat": 28.70406,
        "lng": 77.10249
    }
]
```

For accessing list of rickshaws withinn some given range from given point, following curl is used
```
curl --location --request GET 'http://localhost:8080/range/28.70506/77.10349/200'
```
Here in above request we have lat, lng, distance (in metres) as path params.
We get list of response which matched the gievn criteria.
