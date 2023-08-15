package com.example.AxmCarService.dto;

import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domainDTO.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
@Data
public class UserDTOMapper {

    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }





}
