package org.authentication.ecomerceapiapp.Demo.Controllers;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.authentication.ecomerceapiapp.Demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/demo/product")
public class ProductController {
    private final ProductService productService;
    private HashMap<String, Object> data;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO productDTOSaved = productService.addProduct(productDTO);
        data = new HashMap<>();

        data.put("Se creó correctamente", productDTOSaved);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
        data = new HashMap<>();
        ProductDTO productDTO = productService.getProductById(id);

        if (productDTO == null) {
            return ResponseEntity.notFound().build();
        }

        data.put("Producto entoncrado: ", productDTO);

        return  ResponseEntity.ok(data);
    }
}
