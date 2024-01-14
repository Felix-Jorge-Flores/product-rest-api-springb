package com.bc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bc.exception.ProductException;
import com.bc.model.Product;
import com.bc.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService pService;

	@GetMapping("/view")
	public ResponseEntity<List<Product>> viewAllProduct() throws ProductException {
		return new ResponseEntity<List<Product>>(pService.viewAllProduct(), HttpStatus.OK);
	}

	@GetMapping("/viewPaged")
	public ResponseEntity<Page<Product>> viewPagedProduct(	@RequestParam Optional<Integer> page,
															  @RequestParam Optional<String> sortBy) throws ProductException {
		return new ResponseEntity<Page<Product>>(pService.getProducts(page, sortBy), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product p) throws ProductException {
		Product product = pService.addProduct(p);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product p) throws ProductException {
		Product product = pService.updateProduct(p);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> viewProductById(@PathVariable("productId") Integer productId) throws ProductException {
		return new ResponseEntity<Product>(pService.viewProduct(productId), HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Product>> viewProductByCategoryId(@PathVariable("categoryId") Integer categoryId)
			throws ProductException {
		return new ResponseEntity<List<Product>>(pService.viewProductByCategory(categoryId), HttpStatus.OK);
	}

	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<Product> removeProductById(@PathVariable("productId") Integer productId)
			throws ProductException {
		return new ResponseEntity<Product>(pService.removeProduct(productId), HttpStatus.OK);
	}
	@GetMapping("/search/{query}")
	public ResponseEntity<List<Product>> searchProductByName(@PathVariable("query") String query) throws ProductException {
		return new ResponseEntity<List<Product>>(pService.searchProductByQuery(query), HttpStatus.OK);
	}
}
