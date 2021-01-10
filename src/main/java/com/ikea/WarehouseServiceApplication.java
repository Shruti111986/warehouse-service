package com.ikea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.model.Article;
import com.ikea.model.Product;
import com.ikea.service.InventoryService;
import com.ikea.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class WarehouseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(InventoryService inventoryService, ProductService productService) {
		 return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Article>> typeReference = new TypeReference<List<Article>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/inventory.json");
			try {
				List<Article> articles = mapper.readValue(inputStream,typeReference);
				inventoryService.save(articles);
				System.out.println("Inventory Saved!");
			} catch (IOException e){
				System.out.println("Unable to save inventory: " + e.getMessage());
			}

			TypeReference<List<Product>> typeReferenceProduct = new TypeReference<List<Product>>(){};
			inputStream = TypeReference.class.getResourceAsStream("/json/products.json");
			 try {
				 List<Product> products = mapper.readValue(inputStream,typeReferenceProduct);
				 productService.save(products);
				 System.out.println("Products Saved!");
			 } catch (IOException e){
				 System.out.println("Unable to save products: " + e.getMessage());
			 }
		};
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}
