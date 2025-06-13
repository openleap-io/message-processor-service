package io.openleap.mps.config;

import freemarker.template.TemplateException;
import io.openleap.mps.model.template.Template;
import io.openleap.mps.repository.TemplateRepository;
import jakarta.ws.rs.ProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreemarkerProcessor {
    @Autowired
    private FreemarkerConfig freemarkerConfig;
    @Autowired
    private TemplateRepository templateRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(FreemarkerProcessor.class);

    public String process(String body, Map<String, Object> params) {

        if (params == null)
            params = new HashMap<>();
        else
            escapeParams(params);

        Writer out;
        out = getWriter(body, params);
        return out.toString();
    }

    public Writer getWriter(String body, Map<String, Object> params) {
        Writer out;
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("content", body);
            root.put("params", params);

            freemarker.template.Template template = freemarkerConfig.configuration().getTemplate("interpreterTemplate");
            out = new StringWriter();
            template.process(root, out);
        } catch (IOException e) {
            LOGGER.error("Processing interrupted", e.getMessage());
            throw new ProcessingException(e.getMessage());
        } catch (TemplateException e) {
            LOGGER.error("Processing interrupted", e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return out;
    }

    public void escapeParams(Map<String, Object> params) {
        params.entrySet().stream().forEach(e -> {
            e.setValue(HtmlUtils.htmlEscape((String) e.getValue()));
        });
    }

    public Template createTemplate(String name, String subject, String body) {
        Template template = new Template.Builder()
                .withSubject(subject)
                .withBody(body)
                .withName(name)
                .build();
        return templateRepository.save(template);

    }

    public Template updateTemplate(Template template, String subject, String body) {
        template.setBody(body);
        template.setSubject(subject);
        return templateRepository.save(template);

    }

    public Template getTemplate(String countryCode, String name) {
        String templateName = name;
        if (countryCode != null) {
            templateName = countryCode + "_" + name;
        }
        return templateRepository.findByName(templateName).orElse(null);
    }
}
