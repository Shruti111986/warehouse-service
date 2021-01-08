package com.ikea.service;

import com.ikea.exceptions.InvalidInputException;
import com.ikea.mapper.ProductResponse;
import com.ikea.model.Article;
import com.ikea.model.Product;
import com.ikea.model.ProductArticle;
import com.ikea.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<Product>();
        Article article1 = new Article("seat", 2L);
        Article article2 = new Article("screws", 18L);
        Set<ProductArticle> productArticles = new HashSet<ProductArticle>(Arrays.asList(new ProductArticle(article1, 1), new ProductArticle(article2, 8)));
        products.add(new Product("Dining Chair", productArticles));
        products.add(new Product("Dining Table", productArticles));
        Mockito.when(productRepository.findAll())
                .thenReturn(products);
    }

    @Test
    public void getProducts_thenProductsWithAvailableQuantityAreReturned() {

        //when
        List<ProductResponse> prooductResponse = productService.getProducts();

        //then
        assertEquals("Dining Chair", prooductResponse.get(0).getName());
        assertEquals(Optional.of(2L), Optional.of(prooductResponse.get(0).getQuantity()));
        assertEquals("Dining Table", prooductResponse.get(1).getName());
        assertEquals(Optional.of(2L), Optional.of(prooductResponse.get(1).getQuantity()));
    }

    @Test(expected = InvalidInputException.class)
    public void sellProduct_thenProductsNameInvalid() {
        //when
        productService.sellProduct("Dining", 1);
    }
}
