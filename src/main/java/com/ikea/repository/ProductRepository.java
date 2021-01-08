package com.ikea.repository;

import com.ikea.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    public Product findByName(String name);

}
