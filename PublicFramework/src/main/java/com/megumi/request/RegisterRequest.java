package com.megumi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email(message = "邮箱不符合格式")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "昵称不能为空")
    @Length(min=4,max=10,message="昵称长度必须在4~10")
    private String nickName;

    @NotNull(message = "密码不能为空")
    @Length(min=8,max=16,message="昵称长度必须在8~16")
    private String password;

    @NotNull(message = "用户名不能为空")
    private String userName;
}
