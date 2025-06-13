package io.openleap.mps.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "TemplateRequest model for API request")
public class TemplateRequest {
    @JsonProperty(required = true, value = "name")
    private String name;
    @JsonProperty(required = true, value = "subject")
    private String subject;
    @JsonProperty(required = true, value = "body")
    private String body;

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
