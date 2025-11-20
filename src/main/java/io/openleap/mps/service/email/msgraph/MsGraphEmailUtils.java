package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.models.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class MsGraphEmailUtils {
    public static void addRecipientsToMessage(String commaSeparatedRecipients, Message message) {
        List<Recipient> recipients = new ArrayList<>();
        String[] emailAddresses = commaSeparatedRecipients.split("[;,:]+");
        for (String email : emailAddresses) {
            Recipient recipient = createRecipient(email.trim());
            recipients.add(recipient);
        }
        message.setToRecipients(recipients);
    }

    public static void addCCRecipientsToMessage(String commaSeparatedRecipients, Message message) {
        List<Recipient> recipients = new ArrayList<>();
        String[] emailAddresses = commaSeparatedRecipients.split("[;,:]+");
        for (String email : emailAddresses) {
            Recipient recipient = createRecipient(email.trim());
            recipients.add(recipient);
        }
        message.setCcRecipients(recipients);
    }

    public static void addBCCRecipientsToMessage(String commaSeparatedRecipients, Message message) {
        List<Recipient> recipients = new ArrayList<>();
        String[] emailAddresses = commaSeparatedRecipients.split("[;,:]+");
        for (String email : emailAddresses) {
            Recipient recipient = createRecipient(email.trim());
            recipients.add(recipient);
        }
        message.setBccRecipients(recipients);
    }

    private static Recipient createRecipient(String email) {
        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(email);
        recipient.setEmailAddress(emailAddress);
        return recipient;
    }

    @NotNull
    public static Message createMessage(String subject, String content) {
        Message message = new Message();
        message.setSubject(subject);
        message.setBody(createMessageBody(content));
        return message;
    }

    private static ItemBody createMessageBody(String content) {
        ItemBody body = new ItemBody();
        body.setContentType(BodyType.Html);
        body.setContent(content);
        return body;
    }

    public static void addAttachment(Message message, List<io.openleap.mps.model.Attachment> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<Attachment> attachmentsList = files.stream().map(file -> {
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setName(file.getName());
            fileAttachment.setContentBytes(Base64.getDecoder().decode(file.getBase64Data()));
            fileAttachment.setOdataType("#microsoft.graph.fileAttachment");
            fileAttachment.setContentType(file.getContentType());
            return fileAttachment;
        }).collect(Collectors.toList());

        message.setAttachments(attachmentsList);
    }
}
