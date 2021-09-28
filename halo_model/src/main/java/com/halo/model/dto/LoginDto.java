package com.halo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Halo
 * @create 2021/09/28 上午 11:49
 * @description
 */
@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
