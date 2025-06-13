package io.openleap.mps.repository;

import io.openleap.mps.model.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTemplateRepository extends JpaRepository<Template, Long>, TemplateRepository {
}
