package org.authentication.ecomerceapiapp.Demo.Repositories;

import org.authentication.ecomerceapiapp.Demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
