package com.korit.board.service;

import com.korit.board.dto.ProductRespDto;
import com.korit.board.entity.Product;
import com.korit.board.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public List<ProductRespDto> getProduct() {

        List<ProductRespDto> productRespDtos = new ArrayList<>();

        productMapper.getProducts().forEach(products -> {
            productRespDtos.add(products.toProductDto());
        });

        return productRespDtos;
    }
}
