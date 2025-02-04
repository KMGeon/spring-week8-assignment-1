package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.errors.ProductNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author mugeon
 * Service for products.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    public ProductService(
            Mapper dozerMapper,
            ProductRepository productRepository
    ) {
        this.mapper = dozerMapper;
        this.productRepository = productRepository;
    }

    /**
     * Returns all products in this application.
     * @return all products.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     *
     *  Returns the product with given ID.
     * @author 김무건
     * @param id is the identifier of the product.
     * @return the product with given ID not null.
     * @throws ProductNotFoundException 상품을 찾을 수 없을 경우 발생한다.
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * Make new Product
     * @param productData
     * @return
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * Returns the products With given ID about update
     * @param id
     * @param productData
     * @return
     * @throws ProductNotFoundException
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * Delete Product With ID
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
