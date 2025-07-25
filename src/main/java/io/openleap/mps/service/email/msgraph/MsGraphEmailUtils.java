package io.openleap.mps.service.email.msgraph;

import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.AttachmentCollectionResponse;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
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
        message.toRecipients = recipients;
    }

    public static void addCCRecipientsToMessage(String commaSeparatedRecipients, Message message) {
        List<Recipient> recipients = new ArrayList<>();
        String[] emailAddresses = commaSeparatedRecipients.split("[;,:]+");
        for (String email : emailAddresses) {
            Recipient recipient = createRecipient(email.trim());
            recipients.add(recipient);
        }
        message.ccRecipients = recipients;
    }

    public static void addBCCRecipientsToMessage(String commaSeparatedRecipients, Message message) {
        List<Recipient> recipients = new ArrayList<>();
        String[] emailAddresses = commaSeparatedRecipients.split("[;,:]+");
        for (String email : emailAddresses) {
            Recipient recipient = createRecipient(email.trim());
            recipients.add(recipient);
        }
        message.bccRecipients = recipients;
    }

    private static Recipient createRecipient(String email) {
        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = email;
        recipient.emailAddress = emailAddress;
        return recipient;
    }

    @NotNull
    public static Message createMessage(String subject, String content) {
        Message message = new Message();
        message.subject = subject;
        message.body = createMessageBody(content);
        return message;
    }

    private static ItemBody createMessageBody(String content) {
        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = content;
        return body;
    }

    public static void addAttachment(Message message, List<io.openleap.mps.model.Attachment> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        LinkedList<Attachment> attachmentsList = files.stream().map(file -> {
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.name = file.getName();
            fileAttachment.contentBytes = Base64.getDecoder().decode(file.getBase64Data());
            fileAttachment.oDataType = "#microsoft.graph.fileAttachment";
            fileAttachment.contentType = file.getContentType();
            return fileAttachment;
        }).collect(Collectors.toCollection(LinkedList::new));

        AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
        attachmentCollectionResponse.value = attachmentsList;
        AttachmentCollectionPage attachmentCollectionPage = new AttachmentCollectionPage(attachmentCollectionResponse, null);
        message.attachments = attachmentCollectionPage;
    }

}
