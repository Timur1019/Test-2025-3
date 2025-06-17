package org.com.controller;

import io.javalin.http.Context;
import org.com.dto.WeatherResponseDto;
import org.com.service.WeatherService;

public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void getWeather(Context ctx) {
        String city = ctx.queryParam("city");
        if (city == null || city.isBlank()) {
            ctx.status(400).result("Missing 'city' parameter");
            return;
        }

        try {
            WeatherResponseDto response = weatherService.getWeatherForecast(city);
            ctx.json(response);
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    }
}
