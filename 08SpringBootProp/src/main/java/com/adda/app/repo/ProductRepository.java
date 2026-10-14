package com.adda.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adda.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
