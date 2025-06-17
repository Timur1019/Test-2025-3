package org.com.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.dto.WeatherResponseDto;
import org.com.model.Location;
import org.com.util.Cache;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class WeatherService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private final Cache<WeatherResponseDto> cache = new Cache<>(15 * 60); // 15 минут


    public WeatherResponseDto getWeatherForecast(String city) throws Exception {
        WeatherResponseDto cached = cache.get(city.toLowerCase());
        if (cached != null) {
            return cached;
        }

        Location location = fetchCoordinates(city);
        WeatherResponseDto forecast = fetchForecast(city, location);
        cache.put(city.toLowerCase(), forecast);
        return forecast;
    }

    private Location fetchCoordinates(String city) throws Exception {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city;
        HttpResponse<String> response = httpClient.send(
                HttpRequest.newBuilder(URI.create(url)).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        JsonNode root = mapper.readTree(response.body());
        JsonNode results = root.get("results");

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("City not found: " + city);
        }

        JsonNode cityNode = results.get(0);
        System.out.println("CITY NODE: " + cityNode.toPrettyString());

        double lat = cityNode.get("latitude").asDouble();
        double lon = cityNode.get("longitude").asDouble();

        System.out.println("Latitude = " + lat);
        System.out.println("Longitude = " + lon);

        return new Location(lat, lon);
    }


    private WeatherResponseDto fetchForecast(String city, Location location) throws Exception {
        String url = String.format(
                Locale.US,
                "https://api.open-meteo.com/v1/forecast?latitude=%.5f&longitude=%.5f&hourly=temperature_2m&timezone=auto",
                location.lat(), location.lon()
        );

        HttpResponse<String> response = httpClient.send(
                HttpRequest.newBuilder(URI.create(url)).GET().build(),
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode body = mapper.readTree(response.body());
        JsonNode times = body.at("/hourly/time");
        JsonNode temps = body.at("/hourly/temperature_2m");

        List<String> hours = new ArrayList<>();
        List<Double> temperatures = new ArrayList<>();

        for (int i = 0; i < 24 && i < times.size(); i++) {
            hours.add(times.get(i).asText().substring(11, 16)); // HH:mm
            temperatures.add(temps.get(i).asDouble());
        }
        System.out.println("RAW API response: " + response.body());

        return new WeatherResponseDto(city, hours, temperatures);
    }

}
