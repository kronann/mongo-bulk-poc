package com.poc.mongo;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.bulk.BulkWriteError;
import com.mongodb.bulk.BulkWriteResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceBulkImpl implements ServiceBulk {

    private static Logger LOGGER = LoggerFactory.getLogger(ServiceBulkImpl.class);

    private final MongoTemplate mongoTemplate;

    public ServiceBulkImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insertBulk(List<User> users) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, User.class, "users");
        MongoConverter converter = mongoTemplate.getConverter();

        users.forEach(user -> {
                    Document bson = new Document();
                    converter.write(user, bson);
                    bulkOperations.insert(bson);
                }
        );

        try {
            BulkWriteResult execute = bulkOperations.execute();
            LOGGER.debug("INSERT - {}" + execute.getInsertedCount());
        } catch (DuplicateKeyException ex) {
            manage(ex, users);
        }
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(),"users");
    }

    private void manage(Exception ex, List<User> users) {
        List<BulkWriteError> writeErrors = ((MongoBulkWriteException) ex.getCause()).getWriteErrors();
        List<User> userToUpdate = retrieveUserFromErrorsId(writeErrors, users);

        removeEntries(userToUpdate);
        insertNewEntries(userToUpdate);
    }

    private void insertNewEntries(List<User> userToUpdate) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, User.class, "users");
        MongoConverter converter = mongoTemplate.getConverter();

        try {
            userToUpdate.forEach(user -> {
                Document bson = new Document();
                converter.write(user, bson);
                bulkOperations.insert(bson);
            });
            BulkWriteResult execute = bulkOperations.execute();
            LOGGER.debug("INSERT NEW - {}", execute.getInsertedCount());
        } catch (DataAccessException ex) {
            LOGGER.debug("Ex " + ex);
        }
    }

    private void removeEntries(List<User> userToUpdate) {
        final BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, User.class, "users");
        final MongoConverter converter = mongoTemplate.getConverter();
        try {
            userToUpdate.forEach(user -> {
                LOGGER.debug("id " + user.getId());

                Document bson = new Document();
                converter.write(user, bson);
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(user.getId()));

                bulkOperations.remove(query);
            });
            BulkWriteResult execute = bulkOperations.execute();
            LOGGER.debug("DELETE - {}", execute.getDeletedCount());
        } catch (DataAccessException ex) {
            LOGGER.debug("Ex " + ex);
        }
    }

    private List<User> retrieveUserFromErrorsId(List<BulkWriteError> writeErrors, List<User> users) {
        List<String> duplicatesId = writeErrors.stream().map(err -> err.getMessage().split("\"")[1]).collect(Collectors.toList());

        return users.stream()
                .filter(user -> duplicatesId.stream()
                        .anyMatch(duplicateId -> user.getId().equals(duplicateId)))
                .collect(Collectors.toList());
    }
}
