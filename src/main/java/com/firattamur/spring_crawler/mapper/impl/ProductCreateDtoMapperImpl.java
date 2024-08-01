package com.firattamur.spring_crawler.mapper.impl;

import com.firattamur.spring_crawler.domain.dto.ProductCreateDto;
import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.domain.entity.ProductStatus;
import com.firattamur.spring_crawler.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCreateDtoMapperImpl implements Mapper<ProductEntity, ProductCreateDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductCreateDtoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductCreateDto mapTo(ProductEntity productEntity) {
        return this.modelMapper.map(productEntity, ProductCreateDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductCreateDto productCreateDto) {
        return this.modelMapper.map(productCreateDto, ProductEntity.class);
    }

}
