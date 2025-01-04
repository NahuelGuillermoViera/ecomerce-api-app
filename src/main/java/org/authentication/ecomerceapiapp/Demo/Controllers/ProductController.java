package org.authentication.ecomerceapiapp.Demo.Controllers;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.authentication.ecomerceapiapp.Demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProductController {
    private final ProductService productService;
    private HashMap<Object, String> data;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        data = new HashMap<>();
        data.put(productDTO, "Se creó correctamente");
        return ResponseEntity.ok(data);
    }
}
