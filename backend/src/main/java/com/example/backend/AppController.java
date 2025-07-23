package com.example.backend;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class AppController{

    List<Book> books = Arrays.asList(
        new Book("The Great Gatsby", "F. Scott Fitzgerald"),
        new Book("1984", "George Orwell"),
        new Book("To Kill a Mockingbird", "Harper Lee"),
        new Book("One Hundred Years of Solitude", "Gabriel García Márquez"),
        new Book("The Catcher in the Rye", "J.D. Salinger")
    );

    @GetMapping
    String welcome(){
        return "<h1>Hello World!</h1>";
    }

    @GetMapping("/books")
    ResponseEntity<?> getAll(){
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/welcome")
    String welcome2(){
        return "<h1>Hello APP!</h1>";
    }

     
}

@Data
@AllArgsConstructor
class Book {
    private String name;
    private String author;
}