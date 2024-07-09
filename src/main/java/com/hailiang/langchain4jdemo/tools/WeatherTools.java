package com.hailiang.langchain4jdemo.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDate;

public class WeatherTools {
    @Tool("Returns the weather forecast for tomorrow for a given city")
    String getWeather(@P("The city for which the weather forecast should be returned") String city) {
        System.out.println("The weather forecast for tomorrow in " + city + " is 25°C");
        return "The weather tomorrow in " + city + " is 25°C";
    }

    @Tool("Returns the date for tomorrow")
    LocalDate getTomorrow() {
        System.out.println("Tomorrow is " + LocalDate.now().plusDays(1));
        return LocalDate.now().plusDays(1);
    }

    @Tool("Transforms Celsius degrees into Fahrenheit")
    double celsiusToFahrenheit(@P("The celsius degree to be transformed into fahrenheit") double celsius) {
        System.out.println(celsius + "°C is " + (celsius * 1.8 + 32) + "°F");
        return (celsius * 1.8) + 32;
    }

    String iAmNotATool() {
        System.out.println("I am not a method annotated with @Tool");
        return "I am not a method annotated with @Tool";
    }
}
