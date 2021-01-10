package com.ikea.service;

import com.ikea.exceptions.InvalidInputException;
import com.ikea.mapper.ProductResponse;
import com.ikea.model.Article;
import com.ikea.model.Product;
import com.ikea.model.ProductArticle;
import com.ikea.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>();
        Article article1 = new Article("seat", 4L);
        Article article2 = new Article("screws", 18L);
        Set<ProductArticle> productArticles = new HashSet<>(Arrays.asList(new ProductArticle(article1, 1), new ProductArticle(article2, 6)));
        products.add(new Product("Dining Chair", productArticles));
        products.add(new Product("Dining Table", productArticles));
        when(productRepository.findAll())
                .thenReturn(products);
        when(productRepository.findByName("Dining Chair"))
                .thenReturn(new Product("Dining Chair", productArticles));
    }

    @Test
    public void getProducts_thenProductsWithAvailableQuantityAreReturned() {

        //when
        List<ProductResponse> productResponse = productService.getProducts();

        //then
        assertEquals("Dining Chair", productResponse.get(0).getName());
        assertEquals(Optional.of(3L), Optional.of(productResponse.get(0).getQuantity()));
        assertEquals("Dining Table", productResponse.get(1).getName());
        assertEquals(Optional.of(3L), Optional.of(productResponse.get(1).getQuantity()));
    }

    @Test(expected = InvalidInputException.class)
    public void sellProduct_thenProductsNameInvalid() {
        //when
        productService.sellProduct("Dining", 1);
    }

    @Test
    public void sellProduct_thenInventoryForProductIsReducedAndLeftQuantityReturned() {
        //when
        ProductResponse productResponse = productService.sellProduct("Dining Chair", 2);

        //then
        assertEquals("Dining Chair", productResponse.getName());
        assertEquals(Optional.of(1L), Optional.of(productResponse.getQuantity()));
    }
}
