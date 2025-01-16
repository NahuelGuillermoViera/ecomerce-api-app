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
        if( productDTO.getId() != null && productRepository.existsById(productDTO.getId())) {
            Product updatedProduct = productRepository.save(mapToProduct(productDTO, true));
            return mapToProductDTO(updatedProduct);
        }
        Product productSaved = productRepository.save(mapToProduct(productDTO, false));
        return mapToProductDTO(productSaved);
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
        if (productDTOList.isEmpty()) {
            throw new ResourceNotFoundException("Product", "all", "selection");
        }
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

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id.toString());
        }
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProduct(ProductDTO productDTO) {
        if(!productRepository.existsById(productDTO.getId())) {
            throw new ResourceNotFoundException("Product", "id", productDTO.getId().toString());
        }
        productRepository.delete(mapToProduct(productDTO, true));
    }


    private Product mapToProduct(ProductDTO productDTO, boolean exist) {
        Product product = new Product();
        if (exist) {
            product.setId(productDTO.getId());
        }
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
