package com.ikea.integration;

import com.ikea.model.Article;
import com.ikea.model.Product;
import com.ikea.model.ProductArticle;
import com.ikea.repository.ArticleRepository;
import com.ikea.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void givenProduct_whenGetProduct_thenStatus200()
            throws Exception {

        createTestProdut("Dining Chair");

        mvc.perform(get("/getProducts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Dining Chair"));
    }

    private void createTestProdut(String productName) {
        List<Product> products = new ArrayList<Product>();
        Article article1 = new Article("seat", 2L);
        Article article2 = new Article("screws", 18L);
        articleRepository.saveAll(Arrays.asList(article1, article2));
        Set<ProductArticle> productArticles = new HashSet<ProductArticle>(Arrays.asList(new ProductArticle(article1, 1), new ProductArticle(article2, 8)));
        Product product = new Product(productName, productArticles);
        repository.save(product);
    }
}
