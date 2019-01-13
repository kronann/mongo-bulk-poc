package com.poc.mongo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceBulkImplTest {

    @Autowired
    MongoTemplate mongoTemplate;
    private ServiceBulk serviceBulk;

    @Before
    public void setUp() throws Exception {
        serviceBulk = new ServiceBulkImpl(mongoTemplate);
    }

    @Test
    public void insertBulk() throws Exception {
        long start = System.currentTimeMillis();
        this.serviceBulk.insertBulk(Arrays.asList(new User("0")));
        long end = System.currentTimeMillis();
        System.out.println(serviceBulk.count());
        System.out.println("time ->  " + (end - start) / 1000);
    }

    private void loadFile() {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("users.json")) {

            ObjectMapper mapper = new ObjectMapper();

            List<User> users = mapper.readValue(resourceAsStream, new TypeReference<List<User>>() {
            });
            System.out.println(users.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}