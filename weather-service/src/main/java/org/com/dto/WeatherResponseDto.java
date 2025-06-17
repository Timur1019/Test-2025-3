package org.com.dto;

import java.util.List;

public record WeatherResponseDto(
        String city,
        List<String> hours,
        List<Double> temperatures
) {}
