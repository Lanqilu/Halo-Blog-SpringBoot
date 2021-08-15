package com.halo.blog.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 09:28
 * @description
 */
@Data
public class LoginDto implements Serializable {
//    @NotBlank(message = "昵称不能为空")
//    private String username;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
