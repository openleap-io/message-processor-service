package io.openleap.mps.config;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {
    private final static String INTERPRETER_TEMPLATE_NAME = "interpreterTemplate";
    private final static String INTERPRETER_TEMPLATE = "<#assign inlineTemplate=content?interpret><@inlineTemplate />";

    @Bean
    public Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(INTERPRETER_TEMPLATE_NAME, INTERPRETER_TEMPLATE);
        configuration.setTemplateLoader(templateLoader);
        return configuration;
    }
}
