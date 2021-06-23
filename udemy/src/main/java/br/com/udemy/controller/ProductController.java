package br.com.udemy.controller;

import br.com.udemy.entities.Product;
import br.com.udemy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> index () {
        return new ResponseEntity<>(productService.listAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> show (@PathVariable Integer id) {

        Product product = productService.findById(id);

        if ( product != null ) return new ResponseEntity<>(product, HttpStatus.OK);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Product>> findByItemName (@RequestParam String name) {
        return new ResponseEntity<>(productService.findByNameContains(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> store (@RequestBody Product item) {

        Product product = productService.insertNewProduct(item);

        if ( product != null ) return new ResponseEntity(product, HttpStatus.CREATED);

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update (@PathVariable Integer id, @RequestBody Product product ) {

        Product productUpdated = productService.update(id, product);

        if ( productUpdated != null ) return new ResponseEntity<>(productUpdated, HttpStatus.NO_CONTENT);

        return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete ( @PathVariable Integer id ) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
