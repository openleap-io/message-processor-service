package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import io.openleap.mps.config.FreemarkerProcessor;
import io.openleap.mps.exception.ProcessingException;
import io.openleap.mps.exception.TemplateNotFoundException;
import io.openleap.mps.model.MessageType;
import io.openleap.mps.model.TemplateMessage;
import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.model.template.Template;
import io.openleap.mps.repository.TemplateRepository;
import io.openleap.mps.service.email.EmailService;
import io.openleap.mps.service.email.msgraph.config.MicrosoftGraphClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Profile("email.msgraph")
@Service
@EnableConfigurationProperties(MicrosoftGraphClientProperties.class)
public class EmailMsGraphService implements EmailService {
    private final MicrosoftGraphClientProperties clientProperties;
    Logger logger = LoggerFactory.getLogger(EmailMsGraphService.class);
    private final GraphServiceClient graphServiceClient;
    private TemplateRepository templateRepository;
    private FreemarkerProcessor freemarkerProcessor;

    public EmailMsGraphService(MicrosoftGraphClientProperties clientProperties,
                               GraphServiceClient graphServiceClient,
                               TemplateRepository templateRepository,
                               FreemarkerProcessor freemarkerProcessor) {
        this.clientProperties = clientProperties;
        this.graphServiceClient = graphServiceClient;
        this.templateRepository = templateRepository;
        this.freemarkerProcessor = freemarkerProcessor;
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

    private Message createEmailMessage(EmailMessage emailRequest) {
        String subject = emailRequest.getMessage().getSubject();
        String body = emailRequest.getMessage().getBody();
        if (emailRequest.getMessage().getMessageType().equals(MessageType.TEMPLATE)) {
            TemplateMessage templateMessage = (TemplateMessage) emailRequest.getMessage();
            Optional<Template> template = templateRepository.findByName(templateMessage.getName());
            if (template.isPresent()) {
                subject = template.get().getSubject();
                body = freemarkerProcessor.process(template.get().getBody(), (Map) templateMessage.getTemplateParams());
            } else {
                logger.error("Template with name {} not found", templateMessage.getName());
                throw new TemplateNotFoundException("Template not found: ".concat(templateMessage.getName()));
            }
        }
        Message message = MsGraphEmailUtils.createMessage(subject, body);
        MsGraphEmailUtils.addRecipientsToMessage(emailRequest.getRecipientId(), message);
        MsGraphEmailUtils.addAttachment(message, emailRequest.getMessage().getAttachments());
        return message;
    }

    @Override
    public void send(EmailMessage emailRequest) {
        try {
            Message emailMessage = createEmailMessage(emailRequest);
            sendEmailViaGraph(clientProperties.getSenderId(), emailMessage);
            logger.debug("Email message sent successfully");
        } catch (Exception error) {
            logger.error("Error sending email: {}", error.getMessage());
            throw new ProcessingException("Error sending email: " + error.getMessage(), error);
        }
    }
}
