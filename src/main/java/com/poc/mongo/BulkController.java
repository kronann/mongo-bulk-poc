package com.poc.mongo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RestController
public class BulkController {

    private final ServiceBulk serviceBulk;

    public BulkController(ServiceBulk serviceBulk) {
        this.serviceBulk = serviceBulk;
    }


    @PostMapping("/batch")
    public void loadDate(@RequestBody List<User> users) {
        long start = System.currentTimeMillis();

        partition(users, 1000).forEach(this.serviceBulk::insertBulk);
        long end = System.currentTimeMillis();
        System.out.println("time ->  " + (end - start) / 1000);
    }



    @GetMapping("/")
    public void hello() {
        System.out.println("Hello coco ");
    }

    private static <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

}
