package com.bc.service;

import java.util.List;
import java.util.Optional;

import com.bc.exception.ProductException;
import com.bc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductService {

	public List<Product> viewAllProduct() throws ProductException;

	Page<Product> getProducts(
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> sortBy
	) throws ProductException;

	public Product addProduct(Product product) throws ProductException;

	public Product updateProduct(Product product) throws ProductException;

	public Product viewProduct(Integer productId) throws ProductException;

	public List<Product> viewProductByCategory(Integer categoryId) throws ProductException;

	public Product removeProduct(Integer productId) throws ProductException;
	public List<Product> searchProductByQuery(String query) throws ProductException;

}
