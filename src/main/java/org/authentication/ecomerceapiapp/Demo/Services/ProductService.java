package org.authentication.ecomerceapiapp.Demo.Services;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    Optional<Product> getProductById(Long id);
}
