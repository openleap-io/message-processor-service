package io.openleap.mps.repository;

import io.openleap.mps.model.template.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository {
    Template save(Template template);

    Optional<Template> findById(Long id);

    Optional<Template> findByName(String name);

    List<Template> findAll();

}
