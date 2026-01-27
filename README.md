* City Map Java SDK Demonstration

![City Map Demo View](images/map.jpg)


To run..

```
export FME_SERVER_SIDE_API_KEY="<your server-side api key>"
mvn clean install
mvn exec:java
```

I ran with command-line Maven.  You can run from an IDE.

The built-in flag reference is... 

```
map_of_cities
```
![Sample Flag Rules](images/rules.jpg)

The flag has no dynamic config, but the fun comes in using a "radius" attribute.  Radius is a number. All the cities in the demo are evaluated with a radius attribute reflecting their distance from San Francisco.

The user interface is in the console window, a map of the US with colored dots reflecting the feature rollout.

The demo uses SDK_UPDATE, so it will update instantly especially to a kill.

David Martin

