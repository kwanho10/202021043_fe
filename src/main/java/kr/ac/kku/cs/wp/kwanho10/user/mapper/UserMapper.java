package kr.ac.kku.cs.wp.kwanho10.user.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;


@Mapper(componentModel = SPRING, uses = {UserRoleMapper.class})
public interface UserMapper {
	
    @Mapping(target = "userRoles", source = "userRoles")
    User toEntity(UserDTO userDTO);

    List<User> toEntityList(List<UserDTO> userDTOList);
    
    // user -> UserDTO
    UserDTO toDto(User user);
}
