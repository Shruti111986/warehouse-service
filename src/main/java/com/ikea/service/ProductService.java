package com.ikea.service;

import com.ikea.exceptions.InvalidInputException;
import com.ikea.mapper.ProductResponse;
import com.ikea.model.Product;
import com.ikea.model.ProductArticle;
import com.ikea.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    public ProductService() {
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
        for (Product product : products) {
            productResponses.add(getAvailableProductQuantity(product));
        }
        return productResponses;
    }

    private ProductResponse getAvailableProductQuantity(Product product) {
        Long quanityAvailableProduct = Long.MAX_VALUE;
        for (ProductArticle productArticle : product.getProductArticles()) {
            if (productArticle.getArticle().getStock() >= productArticle.getAmountOf()) {
                Long quantityAvailableArticle = productArticle.getArticle().getStock() / productArticle.getAmountOf();
                if (quantityAvailableArticle < quanityAvailableProduct)
                    quanityAvailableProduct = quantityAvailableArticle;
            } else
                break;
        }
        return new ProductResponse(product.getName(), quanityAvailableProduct);
    }

    public void save(List<Product> products) {
        productRepository.saveAll(products);
    }

    public ProductResponse sellProduct(String productName, int quantity) {
        Product product = productRepository.findByName(productName);
        if (product == null)
            throw new InvalidInputException("Product not found " + productName);
        ProductResponse productResponse = getAvailableProductQuantity(product);
        if (productResponse.getQuantity() >= quantity) {
            for (ProductArticle productArticle : product.getProductArticles()) {
                productArticle.getArticle().setStock(productArticle.getArticle().getStock() - productArticle.getAmountOf());
                inventoryService.save(productArticle.getArticle());
            }
            productResponse.setQuantity(productResponse.getQuantity() - quantity);
            return productResponse;
        } else {
            throw new InvalidInputException("Product " + productName + " has only available quantity " + productResponse.getQuantity());
        }
    }
}
