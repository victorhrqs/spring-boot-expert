package br.com.udemy.service;

import br.com.udemy.entities.Product;
import br.com.udemy.exception.NotFoundException;
import br.com.udemy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product insertNewProduct (Product item) {
        return productRepository.save(item);
    }

    public Product findById (Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
    }

    public List<Product> findByNameContains (String name) {
        return productRepository.findByNameContaining(name);
    }

    public Product update (Integer id, Product product ) {
        Product productFound = this.findById(id);

        if ( productFound != null ) {
            product.setId(id);
            return productRepository.save(product);
        }
        throw new NotFoundException("Failed to update the product: " + id);
    }

    public void delete(Integer id) {
        if ( this.findById(id) != null ) productRepository.deleteById(id);

        throw new NotFoundException("Failed to delete the product: " + id);
    }
}
