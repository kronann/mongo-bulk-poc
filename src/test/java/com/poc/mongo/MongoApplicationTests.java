package com.poc.mongo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(initializers = MongoApplicationTests.Initializer.class)
public class MongoApplicationTests {

    private ServiceBulk serviceBulk;

    @Before
    public void setUp() throws Exception {
        serviceBulk = new ServiceBulkImpl(mongoTemplate);
    }

    @ClassRule
    public static  GenericContainer mongo = new GenericContainer("mongo:3.4.18-jessie")
            .withExposedPorts(27017);


    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongo.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongo.getMappedPort(27017)
            );
            values.applyTo(configurableApplicationContext);
        }
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void should_insertData() {
        serviceBulk.insertBulk(Arrays.asList(new User("0")));
        assertThat(serviceBulk.count()).isEqualTo(1);
    }

}

