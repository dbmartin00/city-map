package io.harness.fme.citymap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

public class CityLoader {

    public static List<City> loadCities() {
        List<City> result = new LinkedList<>();

        try (InputStream is =
                 CityLoader.class.getClassLoader()
                     .getResourceAsStream("city_coords.json")) {

            if (is == null) {
                throw new RuntimeException("city_coords.json not found");
            }

            ObjectMapper mapper = new ObjectMapper();

            Map<String, List<Double>> cityMap =
                mapper.readValue(is, Map.class);

            for (Map.Entry<String, List<Double>> entry : cityMap.entrySet()) {
                String name = entry.getKey();
                double lat = entry.getValue().get(0);
                double lon = entry.getValue().get(1);

                result.add(new City(name, lat, lon));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load city data", e);
        }

        System.out.println("loaded " + result.size() + " cities");
        return result;
    }
}

