package com.firattamur.spring_crawler.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    @NotBlank(message = "Url cannot be blank")
    @Pattern(regexp = "^(http|https)://www.hepsiburada.com/.*$")
    private String url;

}
