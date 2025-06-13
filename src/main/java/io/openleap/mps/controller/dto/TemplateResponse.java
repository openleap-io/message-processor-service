package io.openleap.mps.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "TemplateResponse model for API response")
public class TemplateResponse {
    @JsonProperty(required = true, value = "id")
    private String id;
    @JsonProperty(required = true, value = "name")
    private String name;
    @JsonProperty(required = true, value = "subject")
    private String subject;
    @JsonProperty(required = true, value = "body")
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
