package com.krishpatel.api.product;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer — contains the business logic for products.
 *
 * KEY CONCEPTS:
 *   @Service → tells Spring "this is a service bean, manage its lifecycle".
 *              Spring creates one instance and injects it wherever needed.
 *
 * Why have a Service layer?
 *   - Keeps the Controller thin (it only handles HTTP concerns)
 *   - Business rules live here (validation, calculations, etc.)
 *   - Makes testing easier — you can test business logic without HTTP
 *   - In this simple app the service just delegates to the repo,
 *     but in real projects this layer grows with business rules
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /** Constructor injection — Spring passes the repository automatically. */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /** Saves a new product to the database and returns it (with generated id). */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /** Returns all products from the database. */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds a product by id, or throws an exception if not found.
     * orElseThrow() is a Java Optional pattern — if the product exists,
     * return it; otherwise throw an error that our ApiExceptionHandler catches.
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }
}
