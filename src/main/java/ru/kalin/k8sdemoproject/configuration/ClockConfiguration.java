package ru.kalin.k8sdemoproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kalin.k8sdemoproject.service.util.ClockService;

import java.time.Clock;

@Configuration
public class ClockConfiguration {

    @Bean
    public ClockService clock() {
        return new ClockService(Clock.systemUTC());
    }
}
