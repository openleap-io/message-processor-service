package io.openleap.mps.db;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import io.openleap.mps.config.BaseTest;
import io.openleap.mps.repository.TemplateRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("logger")
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public class TemplateRepositoryTest {//extends BaseTest {

    @Autowired
    private TemplateRepository repository;


//    @Test
    @DataSet(executeScriptsBefore = "cleardb.sql", value = "dataset/template_repository_IT.yml")
    public void testFindByName_notFound() {
        Assert.assertFalse(repository.findByName("test").isPresent());
    }

//    @Test
    @DataSet(executeScriptsBefore = "cleardb.sql", value = "dataset/template_repository_IT.yml")
    public void testFindByName_found() {
        Assert.assertTrue(repository.findByName("123").isPresent());
    }

//    @Test
    @DataSet(executeScriptsBefore = "cleardb.sql", value = "dataset/template_repository_IT.yml")
    public void testFindByName_foundAndAllPropertiesPresent() {
        Assert.assertTrue(repository.findByName("123").isPresent());
        var template = repository.findByName("123").get();
        Assert.assertEquals("123", template.getName());
        Assert.assertEquals("Test Template", template.getSubject());
        Assert.assertEquals("This is a test template.", template.getBody());

    }

//    @Test
    @DataSet(executeScriptsBefore = "cleardb.sql", value = "dataset/template_repository_IT.yml")
    public void testUpdateByName_foundAndAllPropertiesPresent() {
        Assert.assertTrue(repository.findByName("123").isPresent());
        var template = repository.findByName("123").get();
        template.setName("1234");
        template.setSubject("Updated Template");
        template.setBody("This is an updated test template.");
        repository.save(template);
        Assert.assertTrue(repository.findByName("1234").isPresent());
        template = repository.findByName("1234").get();
        Assert.assertEquals("1234", template.getName());
        Assert.assertEquals("Updated Template", template.getSubject());
        Assert.assertEquals("This is an updated test template.", template.getBody());

    }
}
