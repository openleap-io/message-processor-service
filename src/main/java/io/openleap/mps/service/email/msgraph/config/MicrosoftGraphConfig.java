package io.openleap.mps.service.email.msgraph.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("email.msgraph")
@EnableConfigurationProperties(MicrosoftGraphClientProperties.class)
public class MicrosoftGraphConfig {

    private final MicrosoftGraphClientProperties clientProperties;

    public MicrosoftGraphConfig(MicrosoftGraphClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @Bean
    public GraphServiceClient graphServiceClient() {
        try {
            final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                    .clientId(clientProperties.getClientId())
                    .clientSecret(clientProperties.getClientSecret())
                    .tenantId(clientProperties.getTenantId())
                    .build();
            return new GraphServiceClient(clientSecretCredential);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
