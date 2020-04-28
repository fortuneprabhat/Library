package com.book.Library;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.book.Library.service.BookService;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest
public class BookControllerJPAhibernateMockTest {
	
	@Autowired
	MockMvc mockMvc;
    
    @MockBean
    private BookService bookService;
    
    @Test
    public void contextLoad() throws Exception {
    	
    	when(bookService.findAll()).thenReturn(Collections.emptyList());
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/book")
    	.accept(org.springframework.http.MediaType.APPLICATION_JSON))
    	.andReturn();
    	
    	System.out.println("prabhat--------->"+result.getResponse());
    	
    	verify(bookService).findAll();
    }
}
