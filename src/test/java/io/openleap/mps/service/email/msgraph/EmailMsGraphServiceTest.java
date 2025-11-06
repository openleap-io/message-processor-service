package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.UsersRequestBuilder;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import com.microsoft.graph.users.item.sendmail.SendMailRequestBuilder;
import io.openleap.mps.config.FreemarkerProcessor;
import io.openleap.mps.repository.TemplateRepository;
import io.openleap.mps.service.email.msgraph.config.MicrosoftGraphClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailMsGraphServiceTest {

    private EmailMsGraphService emailMsGraphService;

    private MicrosoftGraphClientProperties microsoftGraphClientProperties;
    @Mock
    GraphServiceClient graphServiceClient;

    @Mock
    TemplateRepository templateRepository;

    @Mock
    FreemarkerProcessor freemarkerProcessor;

    @Mock
    UsersRequestBuilder usersRequestBuilder;

    @Mock
    UserItemRequestBuilder userItemRequestBuilder;

    @Mock
    SendMailRequestBuilder sendMailRequestBuilder;

    @BeforeEach
    void init() {
        this.microsoftGraphClientProperties = new MicrosoftGraphClientProperties();
        microsoftGraphClientProperties.setSenderId("senderId");
        Mockito.when(templateRepository.findByName("Test Template")).thenReturn(
                Optional.of(EmailDataFactory.createTemplate()));
        Mockito.when(freemarkerProcessor.process(
                "This is a test email body.", null)).thenReturn("Processed Template Content");

        // Setup Graph SDK 6.x mock chain
        when(graphServiceClient.users()).thenReturn(usersRequestBuilder);
        when(usersRequestBuilder.byUserId(anyString())).thenReturn(userItemRequestBuilder);
        when(userItemRequestBuilder.sendMail()).thenReturn(sendMailRequestBuilder);
        // Remove the thenReturn(null) since post() returns void
        doNothing().when(sendMailRequestBuilder).post(any());

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

        // Verify the new SDK 6.x method chain
        verify(graphServiceClient, times(1)).users();
        verify(usersRequestBuilder, times(1)).byUserId("senderId");
        verify(userItemRequestBuilder, times(1)).sendMail();
        verify(sendMailRequestBuilder, times(1)).post(any());
    }
}
