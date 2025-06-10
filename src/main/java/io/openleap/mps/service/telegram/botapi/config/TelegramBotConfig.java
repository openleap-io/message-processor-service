package io.openleap.mps.service.telegram.botapi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(TelegramBotApiClientProperties.class)
@Profile("telegram.botapi")
public class TelegramBotConfig {
    private final TelegramBotApiClientProperties properties;

    public TelegramBotConfig(TelegramBotApiClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient telegramWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.telegram.org/bot" + properties.getToken())
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)) // 16MB for file uploads
                .build();
    }
}
