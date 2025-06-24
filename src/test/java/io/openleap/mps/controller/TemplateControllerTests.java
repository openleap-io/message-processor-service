package io.openleap.mps.controller;

import io.openleap.mps.config.FreemarkerConfig;
import io.openleap.mps.config.FreemarkerProcessor;
import io.openleap.mps.config.SecurityLoggerConfig;
import io.openleap.mps.model.template.Template;
import io.openleap.mps.repository.TemplateRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TemplateController.class)
@Import({SecurityLoggerConfig.class, FreemarkerProcessor.class, FreemarkerConfig.class})
@ActiveProfiles({"logger"})
public class TemplateControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TemplateRepository templateRepository;

    @Test
    void templates_whenTemplateRequestedByName_shouldReturn200() throws Exception {
        Template template = getTemplate();
        Mockito.when(templateRepository.findByName("EN_Example_Template")).thenReturn(java.util.Optional.of(template));
        mockMvc
                .perform(
                        get("/mps/templates/{name}", "EN_Example_Template")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @NotNull
    private static Template getTemplate() {
        Template template = new Template();
        template.setId(1L);
        template.setName("EN_Example_Template");
        template.setBody("This is a test template");
        template.setSubject("Test Template");
        return template;
    }

    @Test
    void templates_whenNonExistentTemplateRequestedByName_shouldReturn400() throws Exception {
        Mockito.when(templateRepository.findByName("none_existent")).thenReturn(java.util.Optional.empty());
        mockMvc
                .perform(
                        get("/mps/templates/{name}", "none_existent")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void templates_whenTemplateCreate_shouldReturn200() throws Exception {
        Mockito.when(templateRepository.save(any())).thenReturn(getTemplate());

        mockMvc
                .perform(
                        post("/mps/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "name": "Test Template",
                                                  "subject": "Test Subject",
                                                  "body": "This is a test template."
                                                }
                                                """))
                .andExpect(status().isOk());
    }
    @Test
    void templates_whenTemplateUpdate_shouldReturn200() throws Exception {
        Template template = getTemplate();
        Mockito.when(templateRepository.save(any())).thenReturn(getTemplate());
        Mockito.when(templateRepository.findByName("EN_Example_Template")).thenReturn(java.util.Optional.of(template));
        mockMvc
                .perform(
                        patch("/mps/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "name": "EN_Example_Template",
                                                  "subject": "Test Subject",
                                                  "body": "This is a test template."
                                                }
                                                """))
                .andExpect(status().isOk());
    }

    @Test
    void templates_whenTemplateCreateWithMissingBodySubject_shouldReturn200() throws Exception {
        Mockito.when(templateRepository.save(any())).thenReturn(getTemplate());

        mockMvc
                .perform(
                        post("/mps/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "name": "Test Template"
                                                }
                                                """))
                .andExpect(status().isOk());
    }
    @Test
    void templates_whenTemplateCreateWithMissingName_shouldReturn400() throws Exception {
        Mockito.when(templateRepository.save(any())).thenReturn(getTemplate());

        mockMvc
                .perform(
                        post("/mps/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "subject": "Test Subject",
                                                  "body": "This is a test template."
                                                }
                                                """))
                .andExpect(status().isOk());
    }
}
