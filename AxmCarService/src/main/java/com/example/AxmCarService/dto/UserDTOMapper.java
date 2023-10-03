package com.example.AxmCarService.dto;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
@Data
public class UserDTOMapper {

    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
    public static UserDTO fromUser(User user, Role role){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setRoleName(role.getRoleName());
        userDTO.setPermission(role.getPermission());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }





}
