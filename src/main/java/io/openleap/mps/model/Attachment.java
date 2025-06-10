package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Attachment model for API response")
public class Attachment {
    @JsonProperty("name")
    @Schema(description = "Attachment name", example = "test.txt")
    String name;

    @JsonProperty("contentType")
    @Schema(description = "Content type of the attachment", example = "text/plain")
    String contentType;

    @JsonProperty("base64Data")
    @Schema(description = "Base64 encoded data of the attachment", example = "dGVzdA==")
    String base64Data;

    @JsonProperty("url")
    @Schema(description = "URL of the attachment", example = "https://example.com/test.txt")
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
