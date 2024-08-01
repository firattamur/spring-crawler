package com.firattamur.spring_crawler.mapper.impl;

import com.firattamur.spring_crawler.domain.dto.ProductResponseDto;
import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseDtoMapperImpl implements Mapper<ProductEntity, ProductResponseDto> {

    private final ModelMapper modelMapper;

    public ProductResponseDtoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto mapTo(ProductEntity productEntity) {
        return this.modelMapper.map(productEntity, ProductResponseDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductResponseDto productOutDto) {
        return this.modelMapper.map(productOutDto, ProductEntity.class);
    }

}
