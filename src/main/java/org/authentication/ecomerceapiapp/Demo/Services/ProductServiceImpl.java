package org.authentication.ecomerceapiapp.Demo.Services;

import org.authentication.ecomerceapiapp.Demo.DTO.ProductDTO;
import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.authentication.ecomerceapiapp.Demo.Exceptions.ResourceNotFoundException;
import org.authentication.ecomerceapiapp.Demo.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = mapToProduct(productDTO);
        if(!productRepository.existsById(product.getId())) {
            Product productSaved = productRepository.save(product);
            return mapToProductDTO(productSaved);
        }
        Product updatedProduct = productRepository.save(mapToProduct(productDTO));
        return mapToProductDTO(updatedProduct);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(this::mapToProductDTO).orElseThrow(() ->
                new ResourceNotFoundException("Product", "id", id.toString())
        );
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepository.findAll().forEach(product ->
                    productDTOList.add(mapToProductDTO(product))
                );
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = productRepository.findAllByName(name);
        if (productList.isEmpty()) {
            throw new ResourceNotFoundException("Product", "name", name);
        }
        productList.forEach(product ->
                productDTOList.add(mapToProductDTO(product))
        );
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getProductsByPrice(Double minPrice, Double maxPrice) {
        List<ProductDTO> productDTOList = new ArrayList<>();

        productRepository.findAll().forEach(product ->
                {
                    if (maxPrice == 0) {
                        if (product.getPrice() >= minPrice) {
                            productDTOList.add(mapToProductDTO(product));
                        }
                    } else {
                        if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                            productDTOList.add(mapToProductDTO(product));
                        }
                    }

                });

        return productDTOList;
    }


    private Product mapToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        return product;
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());

        return productDTO;
    }
}
