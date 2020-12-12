package me.devtools4.saga4j.orchestrator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TestConfig.class)
public class TestApp {
}