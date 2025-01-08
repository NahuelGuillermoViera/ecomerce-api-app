package org.authentication.ecomerceapiapp.Demo.Controllers;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    @GetMapping("/find/by_id/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
        data = new HashMap<>();
        ProductDTO productDTO = productService.getProductById(id);
        data.put("Producto entoncrado: ", productDTO);

        return  ResponseEntity.ok(data);
    }

    @GetMapping("/find/all")
    public ResponseEntity<Object> getAllProducts() {
        data = new HashMap<>();
        List<ProductDTO> productDTOList = productService.getAllProducts();
        data.put("Products", productDTOList);
        return  ResponseEntity.ok(data);
    }

    @GetMapping("/find/all_by_name")
    public ResponseEntity<Object> getAllProductsByName(@RequestParam("name") String name) {
        data = new HashMap<>();
        List<ProductDTO> productDTOList = productService.getProductsByName(name);
        data.put("Products", productDTOList);
        return  ResponseEntity.ok(data);
    }

    @GetMapping("/find/all_by_price")
    public ResponseEntity<Object> getAllProductsByPrice(@RequestParam(name = "min_price", defaultValue = "0") Double minPrice,
                                                        @RequestParam(name = "max_price", defaultValue = "0") Double maxPrice) {
        data = new HashMap<>();
        List<ProductDTO> productDTOList = productService.getProductsByPrice(minPrice, maxPrice);

        data.put("Products", productDTOList);
        return  ResponseEntity.ok(data);
    }
}
