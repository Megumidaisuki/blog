package com.megumi.mapper;

import com.megumi.entity.DTO.MenuMessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AdminMapper {


     List<String> getRoleName(Long userId);

     List<String> getAuthByRoleName(String roleName);

    List<MenuMessageDTO> getMenuMessageById();

    List<MenuMessageDTO> getSonMenu(Long menuId);
}
