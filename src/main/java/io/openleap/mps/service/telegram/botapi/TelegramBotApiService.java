package io.openleap.mps.service.telegram.botapi;

import io.openleap.mps.model.Attachment;
import io.openleap.mps.model.message.TelegramMessage;
import io.openleap.mps.service.telegram.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;


@Profile("telegram.botapi")
@Service
public class TelegramBotApiService implements TelegramService {
    Logger logger = LoggerFactory.getLogger(TelegramBotApiService.class);
    private final WebClient telegramWebClient;

    public TelegramBotApiService(WebClient telegramWebClient) {
        this.telegramWebClient = telegramWebClient;
    }

    @Override
    public Mono<String> send(TelegramMessage telegramMessage) {
        if (telegramMessage.getMessage().getAttachments().isEmpty()) {
            return sendMessage(telegramMessage);
        } else {
            List<Attachment> attachments = telegramMessage.getMessage().getAttachments();
            Attachment attachment = attachments.get(0);
            byte[] data = attachment.getBase64Data().getBytes();

            File file = new File(attachment.getName());
            try {
                Files.write(file.toPath(), data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return sendDocumentWithCaption(telegramMessage, file);
        }
    }

    public Mono<String> sendMessage(TelegramMessage telegramMessage) {
        return telegramWebClient.post()
                .uri("/sendMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "chat_id", telegramMessage.getChannel().getChatId(),
                        "text", telegramMessage.getMessage().getBody()
                ))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> sendDocument(Long chatId, File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("chat_id", chatId.toString());
        builder.part("document", new FileSystemResource(file));

        return telegramWebClient.post()
                .uri("/sendDocument")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> sendDocumentWithCaption(TelegramMessage telegramMessage, File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("chat_id", telegramMessage.getChannel().getChatId());
        builder.part("document", new FileSystemResource(file));

        if (telegramMessage.getMessage().getBody() != null) {
            builder.part("caption", telegramMessage.getMessage().getBody());
        }

        return telegramWebClient.post()
                .uri("/sendDocument")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class);
    }

}
