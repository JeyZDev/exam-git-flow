package com.sm.jeyz9.storemateapi;

import com.sm.jeyz9.storemateapi.dto.ProductRequestDTO;
import com.sm.jeyz9.storemateapi.models.Category;
import com.sm.jeyz9.storemateapi.models.Product;
import com.sm.jeyz9.storemateapi.models.ProductStatus;
import com.sm.jeyz9.storemateapi.models.ProductStock;
import com.sm.jeyz9.storemateapi.repository.*;
import com.sm.jeyz9.storemateapi.services.ProductService;
import com.sm.jeyz9.storemateapi.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private ProductStatusRepository productStatusRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductStockRepository productStockRepository;
    @Mock private ReviewRepository reviewRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldAddProductSuccessfully() {

        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductName("Soap");
        request.setCategoryId(1L);
        request.setStatusId(1L);
        request.setPrice(100.0);
        request.setStockQuantity(10);

        when(productStatusRepository.findById(1L))
                .thenReturn(Optional.of(new ProductStatus()));

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(new Category()));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    p.setId(1L);
                    return p;
                });

        when(productStockRepository.save(any(ProductStock.class)))
                .thenReturn(new ProductStock());

        String result = productService.addProduct(request, null);

        assertEquals("Add product success.", result);

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productStockRepository, times(1)).save(any(ProductStock.class));
    }
}