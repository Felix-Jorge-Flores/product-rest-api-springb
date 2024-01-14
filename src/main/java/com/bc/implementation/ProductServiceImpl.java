package com.bc.implementation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bc.exception.ProductException;
import com.bc.model.Category;
import com.bc.model.Product;
import com.bc.repository.CategoryRepo;
import com.bc.repository.ProductRepo;
import com.bc.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo pRepo;

	@Autowired
	private CategoryRepo cRepo;

	@Override
	public List<Product> viewAllProduct() throws ProductException {
		List<Product> products = pRepo.findAll();
		if (products.size() > 0) {
			return products;
		} else {
			throw new ProductException("Products not found");
		}
	}
	@Override
	public Page<Product> getProducts(
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> sortBy
	) throws ProductException {

		return pRepo.findAll(
				PageRequest.of(

				page.orElse(0),
				20,
				Sort.Direction.DESC, sortBy.orElse("id")
				)
		);
	}
	@Override
	public Product addProduct(Product product) throws ProductException {
		Product pro = pRepo.save(product);
		if (pro != null) {
			return pro;
		} else {
			throw new ProductException("Product not added");
		}

	}

	@Override
	public Product updateProduct(Product product) throws ProductException {
		Optional<Product> opt = pRepo.findById(product.getProductId());
		if (opt.isPresent()) {
			return pRepo.save(product);

		} else {
			throw new ProductException("Product not updated");
		}

	}

	@Override
	public Product viewProduct(Integer productId) throws ProductException {
		Optional<Product> opt = pRepo.findById(productId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new ProductException("Product not found with product id - " + productId);
		}
	}

	@Override
	public List<Product> viewProductByCategory(Integer categoryId) throws ProductException {
		Optional<Category> category = cRepo.findById(categoryId);
		if (category.isPresent()) {
			return category.get().getProductList();
		} else {
			throw new ProductException("Product not found with category id - " + categoryId);
		}

	}

	@Override
	public Product removeProduct(Integer productId) throws ProductException {
		Product p = pRepo.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
		pRepo.delete(p);
		return p;

	}

	public List<Product> searchProductByQuery(String query) throws ProductException {
		List<Product> result = new ArrayList<>();
		List<Product> products = pRepo.findAll();
		for (Product product : products) {
			Field[] fields = Product.class.getDeclaredFields();

			for (Field field : fields) {
				if (field.getType() == String.class) {
					field.setAccessible(true);

					try {
						String fieldValue = (String) field.get(product);

						if (fieldValue != null && fieldValue.toLowerCase().contains(query.toLowerCase())) {
							result.add(product);
							break;  // No es necesario seguir revisando los otros campos
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}



}
