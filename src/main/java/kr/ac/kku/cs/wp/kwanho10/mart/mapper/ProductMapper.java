package kr.ac.kku.cs.wp.kwanho10.mart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;


@Mapper(componentModel = "spring")
public interface ProductMapper {
	

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);   
	

	 // ProductDTO -> Product
    Product toEntity(ProductDTO productDTO);

    // Product -> ProductDTO
    ProductDTO toDto(Product product);
	
}
