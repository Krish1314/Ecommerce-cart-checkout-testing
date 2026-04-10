package com.krishpatel.api.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA Entity — this class maps to the "products" table in PostgreSQL.
 *
 * KEY CONCEPTS:
 *   @Entity  → tells JPA "this Java class represents a database table"
 *   @Table   → specifies the exact table name (defaults to class name if omitted)
 *
 * Each instance of Product = one row in the "products" table.
 * JPA (Hibernate) automatically converts between Java objects and SQL rows.
 */
@Entity
@Table(name = "products")
public class Product {

    /**
     * @Id              → marks this field as the primary key
     * @GeneratedValue  → the database auto-generates the id (auto-increment)
     *                     IDENTITY strategy lets PostgreSQL handle the numbering
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column → customizes the database column:
     *   nullable = false → this column cannot be NULL (name is required)
     *   length = 100     → max 100 characters
     */
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    /**
     * No-arg constructor — required by JPA/Hibernate.
     * Hibernate creates Product objects using reflection, so it needs
     * an empty constructor even if you never call it yourself.
     */
    public Product() {
    }

    /** Convenience constructor for creating products in code. */
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    // --- Getters and Setters ---
    // JPA and Jackson (JSON serialization) both need these to read/write fields.
    // Jackson converts Product → JSON when sending API responses,
    // and JSON → Product when receiving API requests.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
