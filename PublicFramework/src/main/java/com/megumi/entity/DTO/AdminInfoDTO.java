package com.megumi.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfoDTO {
    private Set<String> permissions;
    private List<String> roles;
    private UserInfoDTO user;
}
