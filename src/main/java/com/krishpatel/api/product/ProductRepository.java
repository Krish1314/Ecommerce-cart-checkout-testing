package com.krishpatel.api.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface — handles all database operations for Product.
 *
 * KEY CONCEPTS:
 *   By extending JpaRepository<Product, Long>, you get these methods FOR FREE
 *   (no code needed!):
 *     - save(product)       → INSERT or UPDATE a product
 *     - findAll()           → SELECT * FROM products
 *     - findById(id)        → SELECT * FROM products WHERE id = ?
 *     - deleteById(id)      → DELETE FROM products WHERE id = ?
 *     - count()             → SELECT COUNT(*) FROM products
 *     ... and many more!
 *
 *   The generics <Product, Long> mean:
 *     Product → the entity type this repository manages
 *     Long    → the type of the entity's primary key (id)
 *
 *   Spring Data JPA automatically creates the implementation at runtime.
 *   You just define the interface — no SQL writing needed!
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
