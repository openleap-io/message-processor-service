package io.openleap.mps;

import io.openleap.mps.config.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("logger")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageProcessorServiceApplicationTest extends BaseTest {
    @Test
    void contextLoads() {
    }
}
