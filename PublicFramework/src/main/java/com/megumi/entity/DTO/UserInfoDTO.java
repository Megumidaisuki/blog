package com.megumi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoDTO {
    private String avatar;
    private String email;
    private Long id;
    private String nickName;
    private String sex;
}
