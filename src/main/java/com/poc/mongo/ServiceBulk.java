package com.poc.mongo;

import java.util.List;

public interface ServiceBulk {
    void insertBulk(List<User> users);
    long count();

}
