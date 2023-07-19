package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    // KODUS: Kõikide võtmine, Lisamine, Kustutamine, Ühe võtmine, Muutmine
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("categories")
    public List<Category> addCategories(@RequestBody Category category) {
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategories(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

    @GetMapping("categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    @PutMapping("categories/{id}")
    public List<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(categoryRepository.findById(id).get().getId());
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
