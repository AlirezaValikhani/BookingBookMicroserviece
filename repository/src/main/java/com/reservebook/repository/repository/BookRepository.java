package com.reservebook.repository.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.model.Book;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.reservebook.repository.dto.SearchPaginationDto;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class BookRepository {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper mapper;

    public BookRepository(MongoClient mongoClient, MongoTemplate mongoTemplate, ObjectMapper mapper) {
        this.mongoClient = mongoClient;
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
        this.mongoDatabase = this.mongoClient.getDatabase("booking");
    }

    public Book save(Book book) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("booking");
        Document query = new Document();
        query.put("name", book.getName());
        query.put("authorName", book.getAuthorName());
        query.put("isReserve", book.getIsReserve());
        collection.insertOne(query);
        String ObjID = query.get("_id").toString();
        book.setId(ObjID);
        return book;
    }

    public void update(Book book) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("booking");
        Document query = new Document();
        query.append("_id", new ObjectId(book.getId()));

        Map bookMap = new ObjectMapper().convertValue(book, Map.class);
        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject(bookMap));
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(query, update, options);
    }

    public void delete(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("booking");
        Document document = new Document();
        document.put("_id", new ObjectId(id));
        collection.deleteOne(document);
    }

    public List<Book> findAll() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("booking");
        return collection.find()
                .into(new ArrayList<>())
                .stream()
                .map(doc -> {
                    Book book = new Book();
                    book.setAuthorName(doc.getString("authorName"));
                    book.setId(doc.getObjectId("_id").toString());
                    book.setName(doc.getString("name"));
                    return book;
                })
                .collect(Collectors.toList());
    }

    public Book findById(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("booking");
        Document document = new Document();
        document.put("_id", new ObjectId(id));
        Document doc = collection.find(document)
                .first();
        Book book = new Book();
        book.setName(doc.getString("name"));
        book.setAuthorName(doc.getString("authorName"));
        book.setIsReserve(doc.getBoolean("isReserve"));
        return book;
    }

    @SneakyThrows
    public List<Book> search(SearchPaginationDto searchDto) {
        List<BasicDBObject> aggregationInput = new ArrayList<>();
        aggregationInput.add(
                new BasicDBObject("$match",
                        new BasicDBObject("$or",
                                Arrays.asList(
                                        new BasicDBObject("name",
                                                new BasicDBObject("$regex", searchDto.getSearch()).append("$options", "i")
                                        ),
                                        new BasicDBObject("authorName",
                                                new BasicDBObject("$regex", searchDto.getSearch()).append("$options", "i")
                                        )
                                )
                        )
                )
        );
        aggregationInput.add(
                new BasicDBObject("$sort",
                        new BasicDBObject("_id", 1)
                )
        );
        aggregationInput.add(
                new BasicDBObject("$skip",
                        searchDto.getSize() * searchDto.getPage())
        );
        aggregationInput.add(
                new BasicDBObject("$limit",
                        searchDto.getSize())
        );
        List<Document> documents = mongoDatabase.getCollection("booking")
                .aggregate(aggregationInput).into(new ArrayList<>());
        List<Book> books = new ArrayList<>();
        for (Document d : documents) {
            Book book = mapper.readValue(d.toJson(), Book.class);
            books.add(book);
        }
        return books;
    }
}
