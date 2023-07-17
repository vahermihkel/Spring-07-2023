package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return productRepository.findAll();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    @PutMapping("products/{id}")
    public List<Product> getProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productRepository.existsById(id)) {
            productRepository.save(product);
        }
        return productRepository.findAll();
    }

//    @PatchMapping("increase-stock/{id}")
//    public List<Product> increaseStock(@PathVariable Long id, @RequestBody Product product) {
//        if (productRepository.existsById(id)) {
//            productRepository.save(product);
//        }
//        return productRepository.findAll();
//    }
}
