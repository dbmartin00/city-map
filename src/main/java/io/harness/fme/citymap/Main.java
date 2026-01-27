package io.harness.fme.citymap;

import io.split.client.SplitFactoryBuilder;
import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import java.util.concurrent.TimeoutException;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        List<City> cities = CityLoader.loadCities();
        int width = 80, height = 20;

        String apiKey = System.getenv("FME_SERVER_SIDE_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("FME_SERVER_SIDE_API_KEY is not set");
        }

        SplitClientConfig config = SplitClientConfig.builder()
          .setBlockUntilReadyTimeout(5000)
          .build();

        SplitClient client = SplitFactoryBuilder.build(apiKey, config).client(); 
        client.blockUntilReady();

        MapRenderer renderer = new MapRenderer(width, height);

        int wait = 1000;
        while (true) {
            Thread.sleep(wait);
            
            // refresh treatments
            for (City city : cities) {
                int radius = GeoUtils.distance(37.7562, -122.4430,
                        city.getLatitude(), city.getLongitude());
                Map<String, Object> attrs = Map.of("radius", radius);

                String treatment = client.getTreatment(city.getName(),
                        "map_of_cities", attrs);
                city.setTreatment(treatment);
                //System.out.println(city.getName() + " - " + radius + " - " + treatment);
            }

            // render map
            renderer.render(cities);

            // wait for space to refresh
            //InteractiveMap.waitForSpace();
        }
    }
}

