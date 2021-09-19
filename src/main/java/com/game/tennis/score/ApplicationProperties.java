package com.game.tennis.score;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value("${tennis.game.deuce.enabled:false}")
    private boolean deuceEnabled;

    public boolean getDeuceEnabled() {
        return deuceEnabled;
    }

    public void setDeuceEnabled(boolean deuceEnabled) {
        this.deuceEnabled = deuceEnabled;
    }
}
