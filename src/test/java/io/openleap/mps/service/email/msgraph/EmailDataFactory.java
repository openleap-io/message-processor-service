package io.openleap.mps.service.email.msgraph;

import io.openleap.mps.model.ChannelType;
import io.openleap.mps.model.EmailChannel;
import io.openleap.mps.model.MessageType;
import io.openleap.mps.model.TemplateMessage;
import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.model.template.Template;
import org.jetbrains.annotations.NotNull;

public class EmailDataFactory {
    public static EmailMessage createEmailTemplateMessage() {
        TemplateMessage templateMessage = getTemplateMessage();

        EmailChannel emailChannel = new EmailChannel();
        emailChannel.setBcc("bcc");
        emailChannel.setChannelType(ChannelType.EMAIL);

        return new EmailMessage(templateMessage, "recipientId", emailChannel);
    }

    @NotNull
    public static TemplateMessage getTemplateMessage() {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setName("Test Template");
        templateMessage.setSubject("Test Subject");
        templateMessage.setBody("This is a test email body.");
        templateMessage.setMessageType(MessageType.TEMPLATE);
        return templateMessage;
    }

    public static Template createTemplate() {
        TemplateMessage templateMessage = getTemplateMessage();
        Template template = new Template();
        template.setId(1L);
        template.setName(templateMessage.getName());
        template.setSubject(templateMessage.getSubject());
        template.setBody(templateMessage.getBody());
        return template;
    }
}
