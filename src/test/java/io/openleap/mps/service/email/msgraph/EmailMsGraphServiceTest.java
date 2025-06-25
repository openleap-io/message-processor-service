package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.requests.GraphServiceClient;
import io.openleap.mps.config.FreemarkerProcessor;
import io.openleap.mps.repository.TemplateRepository;
import io.openleap.mps.service.email.msgraph.config.MicrosoftGraphClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmailMsGraphServiceTest {

    private EmailMsGraphService emailMsGraphService;

    private MicrosoftGraphClientProperties microsoftGraphClientProperties;
    @MockitoBean
    GraphServiceClient graphServiceClient;

    @MockitoBean
    TemplateRepository templateRepository;

    @MockitoBean
    FreemarkerProcessor freemarkerProcessor;

    @BeforeEach
    void init() {
        this.templateRepository = Mockito.mock(TemplateRepository.class);
        this.freemarkerProcessor = Mockito.mock(FreemarkerProcessor.class);
        this.graphServiceClient = Mockito.mock(GraphServiceClient.class);
        this.microsoftGraphClientProperties = new MicrosoftGraphClientProperties();

        microsoftGraphClientProperties.setSenderId("senderId");
        Mockito.when(templateRepository.findByName("Test Template")).thenReturn(
                Optional.of(EmailDataFactory.createTemplate()));
        Mockito.when(freemarkerProcessor.process(
                "This is a test email body.", null)).thenReturn("Processed Template Content");
        Mockito.when(graphServiceClient.users("senderId"))
                .thenReturn(Mockito.mock(com.microsoft.graph.requests.UserRequestBuilder.class));
        Mockito.when(graphServiceClient.users("senderId").sendMail(Mockito.any())).
                thenReturn(
                        Mockito.mock(com.microsoft.graph.requests.UserSendMailRequestBuilder.class));
        Mockito.when(graphServiceClient.users("senderId").sendMail(Mockito.any()).buildRequest())
                .thenReturn(Mockito.mock(com.microsoft.graph.requests.UserSendMailRequest.class));

        emailMsGraphService = new EmailMsGraphService(
                microsoftGraphClientProperties,
                graphServiceClient,
                templateRepository,
                freemarkerProcessor
        );
    }


    @Test
    void testEmailMsGraphServiceInitialization() {
        emailMsGraphService.send(EmailDataFactory.createEmailTemplateMessage());
        Mockito.verify(graphServiceClient, Mockito.times(3))
                .users("senderId");
    }
}
