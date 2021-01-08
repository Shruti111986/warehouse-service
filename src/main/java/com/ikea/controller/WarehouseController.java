package com.ikea.controller;

import com.ikea.mapper.ProductResponse;
import com.ikea.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    @ApiOperation("Get all products and quantity of each that is an available with the current inventory")
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("/sellProduct/{productName}/{quantity}")
    @ApiOperation("Reduce the inventory for the product and returns the left quantity")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse sellProduct(@PathVariable String productName, @PathVariable int quantity) {
        return productService.sellProduct(productName, quantity);
    }

}
