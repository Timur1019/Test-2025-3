package org.com;

import io.javalin.Javalin;
import org.com.controller.WeatherController;
import org.com.service.WeatherService;

public class WeatherApplication {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();
        WeatherController weatherController = new WeatherController(weatherService);

        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
        }).start(7070);

        app.get("/weather", weatherController::getWeather);
    }
}
