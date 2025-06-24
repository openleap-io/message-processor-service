package io.openleap.mps.controller;

import io.openleap.mps.config.FreemarkerProcessor;
import io.openleap.mps.controller.dto.TemplateRequest;
import io.openleap.mps.controller.dto.TemplateResponse;
import io.openleap.mps.model.template.Template;
import io.openleap.mps.repository.TemplateRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mps/templates")
@Tag(name = "Message Template API", description = "Endpoints for managing message templates.")
public class TemplateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private FreemarkerProcessor freemarkerProcessor;

    @Operation(summary = "Get a message template by name", description = "Retrieves a message template by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TemplateRequest.class))),
            @ApiResponse(responseCode = "404", description = "Template not found",
                    content = @Content(examples = @ExampleObject(value = "{ \"error\": \"Template not found\" }")))
    })
    @GetMapping("/{name}")
    public ResponseEntity getTamplateByName(@PathVariable("name") String name) {
        try {
            Template template = templateRepository.findByName(name).orElse(null);
            if (template == null) {
                LOGGER.warn("Template with name {} not found", name);
                return ResponseEntity.badRequest().body(null);
            } else {
                LOGGER.info("Template with name {} requested", name);
                TemplateResponse templateResponse = createTemplateResponse(template);
                return ResponseEntity.ok(templateResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new message template", description = "Creates a new message template based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Template created successfully",
                    content = @Content(schema = @Schema(implementation = TemplateRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(examples = @ExampleObject(value = "{ \"error\": \"Invalid input\" }")))
    })
    public ResponseEntity createTamplateByName(@Valid @RequestBody TemplateRequest templateRequest) {
        if (templateRequest == null || templateRequest.getName() == null) {
            ResponseEntity.badRequest();
        }
        try {
            Template template = templateRepository.findByName(templateRequest.getName()).orElse(null);
            if (template != null) {
                LOGGER.warn("Template with name {} already found", templateRequest.getName());
                return ResponseEntity.badRequest().body(null);
            } else {
                LOGGER.info("Template with name {} creation requested", templateRequest.getName());
                TemplateResponse templateResponse = createTemplateResponse(
                        freemarkerProcessor.createTemplate(templateRequest.getName(), templateRequest.getSubject(), templateRequest.getBody()));
                return ResponseEntity.ok(templateResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping
    @Operation(summary = "Update an existing message template", description = "Updates an existing message template based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template updated successfully",
                    content = @Content(schema = @Schema(implementation = TemplateRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(examples = @ExampleObject(value = "{ \"error\": \"Invalid input\" }")))
    })
    public ResponseEntity updateTamplateByName(@Valid @RequestBody TemplateRequest templateRequest) {
        try {
            Template template = templateRepository.findByName(templateRequest.getName()).orElse(null);
            if (template == null) {
                LOGGER.warn("Template with name {} bot not found", templateRequest.getName());
                return ResponseEntity.badRequest().body(null);
            } else {
                LOGGER.info("Template with name {} update requested", templateRequest.getName());
                TemplateResponse templateResponse = createTemplateResponse(
                        freemarkerProcessor.updateTemplate(template, templateRequest.getSubject(), templateRequest.getBody()));
                return ResponseEntity.ok(templateResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public TemplateResponse createTemplateResponse(Template template) {
        TemplateResponse templateResponse = new TemplateResponse();
        templateResponse.setId(template.getId().toString());
        if (template.getBody() != null)
            templateResponse.setBody(template.getBody());
        templateResponse.setName(template.getName());
        if (template.getSubject() != null)
            templateResponse.setSubject(template.getSubject());
        return templateResponse;
    }
}
