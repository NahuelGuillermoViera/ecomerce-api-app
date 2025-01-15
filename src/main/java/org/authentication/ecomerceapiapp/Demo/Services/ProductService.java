package org.authentication.ecomerceapiapp.Demo.Services;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByName(String name);
    List<ProductDTO> getProductsByPrice(Double minPrice, Double maxPrice);
    void deleteProduct(Long id);
}
