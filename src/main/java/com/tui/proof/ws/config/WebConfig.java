package com.tui.proof.ws.config;

import com.tui.proof.ws.controller.resolver.FlightFilterArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(flightFilterArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver flightFilterArgumentResolver() {
        return new FlightFilterArgumentResolver();
    }
}
