package com.book.Library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.book.Library.model.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    @Test
    public void contextLoads() {

    }
    
    @Test
    public void testGetAllBooks() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/books",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }
    
    
    @Test
    public void testGetEmployeeById() {
        Book book = restTemplate.getForObject(getRootUrl() + "/1", Book.class);
        System.out.println(book.getAuthor());
        assertNotNull(book);
    }

    @Test
    public void testCreateEmployee() {
        Book book = new Book();
        book.setAuthor("abc");
        book.setId(11);
        book.setTitle("mY tITLE");
        ResponseEntity<Book> postResponse = restTemplate.postForEntity(getRootUrl() + "/books", book, Book.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateEmployee() {
        int id = 1;
        Book book = restTemplate.getForObject(getRootUrl() + "/books/" + id, Book.class);
        book.setAuthor("abc");
        book.setTitle("My Title");
        restTemplate.put(getRootUrl() + "/employees/" + id, book);
        Book updatedEmployee = restTemplate.getForObject(getRootUrl() + "/booka/" + id, Book.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
         int id = 2;
         Book book = restTemplate.getForObject(getRootUrl() + "/books/" + id, Book.class);
         assertNotNull(book);
         restTemplate.delete(getRootUrl() + "/books/" + id);
         try {
              book = restTemplate.getForObject(getRootUrl() + "/books/" + id, Book.class);
         } catch (final HttpClientErrorException e) {
              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
}
