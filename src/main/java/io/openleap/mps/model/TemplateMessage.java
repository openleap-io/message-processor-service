package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(
        description = "Template message implementation",
        allOf = Message.class,
        example =
                """
                        {
                          "messageType": "TEMPLATE",
                          "name": "test-template",
                          "templateParams": {
                            "key1": "value1",
                            "key2": "value2"
                          }
                        }
                        """
)
public class TemplateMessage extends Message {
    public TemplateMessage() {
        this.messageType = MessageType.TEMPLATE;
    }

    @Schema(description = "Template name", example = "test-template")
    @JsonProperty("name")
    String name;

    @Schema(description = "Template parameters", example = "{\"key1\":\"value1\", \"key2\":\"value2\"}")
    @JsonProperty("templateParams")
    Map<String, String> templateParams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, String> templateParams) {
        this.templateParams = templateParams;
    }
}
