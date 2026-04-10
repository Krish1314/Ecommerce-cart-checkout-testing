package com.krishpatel.api.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller — handles incoming HTTP requests for products.
 *
 * KEY CONCEPTS:
 *   @RestController  → combines @Controller + @ResponseBody, meaning every
 *                       method return value is automatically converted to JSON
 *   @RequestMapping  → all endpoints in this class start with "/api/products"
 *
 * Architecture pattern (Controller → Service → Repository):
 *   Controller  = receives HTTP requests, delegates to Service
 *   Service     = contains business logic
 *   Repository  = talks to the database
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor Injection — Spring automatically passes the ProductService bean here.
     * This is the recommended way to inject dependencies (instead of @Autowired on fields)
     * because it makes the class easier to test.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST /api/products
     * Creates a new product from the JSON request body.
     *
     * @RequestBody  → tells Spring to parse the incoming JSON into a Product object
     * @ResponseStatus(CREATED) → returns HTTP 201 instead of the default 200
     *
     * Example request body: { "name": "Sauce Labs Backpack", "price": 29.99 }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    /**
     * GET /api/products
     * Returns a list of all products as a JSON array.
     */
    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    /**
     * GET /api/products/{id}
     * Returns a single product by its id.
     *
     * @PathVariable → extracts the {id} value from the URL
     * Example: GET /api/products/1 → id = 1
     */
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
