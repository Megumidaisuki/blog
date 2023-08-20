package com.megumi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String avatar;
    private String email;
    private Long id;
    private String nickName;
    private String sex;
}
