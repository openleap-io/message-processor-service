package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import io.openleap.mps.service.email.msgraph.config.MicrosoftGraphClientProperties;
import io.openleap.mps.model.Attachment;
import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("email.msgraph")
@Service
@EnableConfigurationProperties(MicrosoftGraphClientProperties.class)
public class EmailMsGraphService implements EmailService {
    private final MicrosoftGraphClientProperties clientProperties;
    Logger logger = LoggerFactory.getLogger(EmailMsGraphService.class);
    private final GraphServiceClient graphServiceClient;

    public EmailMsGraphService(MicrosoftGraphClientProperties clientProperties, GraphServiceClient graphServiceClient) {
        this.clientProperties = clientProperties;
        this.graphServiceClient = graphServiceClient;
    }

    private void sendEmailViaGraph(String sender, Message message) {
        graphServiceClient.users(sender)
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(true)
                        .build())
                .buildRequest()
                .post();
    }

    private Message createEmailMessage(String recipientList, String subject, String body, List<Attachment> attachments) {
        Message message = MsGraphEmailUtils.createMessage(subject, body);
        MsGraphEmailUtils.addRecipientsToMessage(recipientList, message);
        MsGraphEmailUtils.addAttachment(message, attachments);
        return message;
    }

    @Override
    public void send(EmailMessage emailRequest) {
        try {
            Message emailMessage = createEmailMessage(
                    emailRequest.getRecipientId(),
                    emailRequest.getMessage().getSubject(),
                    emailRequest.getMessage().getBody(),
                    emailRequest.getMessage().getAttachments());
            sendEmailViaGraph(clientProperties.getSenderId(), emailMessage);
            logger.debug("Email message sent successfully");
        } catch (Exception error) {
            logger.error("Error sending email: {}", error.getMessage());
            error.printStackTrace();
        }
    }
}
