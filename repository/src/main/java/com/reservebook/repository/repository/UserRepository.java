package com.reservebook.repository.repository;

import com.models.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoClient mongoClient, MongoTemplate mongoTemplate) {
        this.mongoClient = mongoClient;
        this.mongoDatabase = this.mongoClient.getDatabase("booking");
        this.mongoTemplate = mongoTemplate;
    }

    public User save(User user) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = new Document();
        document.put("fullName", user.getFullName());
        document.put("nationalCode",user.getNationalCode());
        document.put("email",user.getEmail());
        collection.insertOne(document);
        String objID = document.get("_id").toString();
        user.setId(objID);
        return user;
    }

    public void update(User user) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = new Document();
        document.append("_id", new ObjectId(user.getId()));
        Bson bson = Updates.combine(
                Updates.set("fullName", user.getFullName()),
                Updates.set("nationalCode",user.getNationalCode()),
                Updates.set("email",user.getEmail())
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(document,bson,options);
    }

    public void delete(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = new Document();
        document.put("_id",new ObjectId(id));
        collection.deleteOne(document);
    }

    public List<User> findAll() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        return collection.find().batchSize(10)
                .into(new ArrayList<>())
                .stream()
                .map(doc -> {
                    User user = new User();
                    user.setFullName(doc.getString("fullName"));
                    user.setNationalCode(doc.getString("nationalCode"));
                    user.setEmail(doc.getString("email"));
                    return user;
                })
                .collect(Collectors.toList());
    }

    public User findById(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = new Document();
        document.put("_id", new ObjectId(id));
        Document doc = collection.find(document).first();
        User user = new User();
        user.setFullName(doc.getString("fullName"));
        user.setNationalCode(doc.getString("nationalCode"));
        user.setEmail(doc.getString("email"));
        return user;
    }
}
