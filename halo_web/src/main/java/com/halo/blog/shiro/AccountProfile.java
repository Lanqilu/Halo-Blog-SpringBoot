package com.halo.blog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 08:55
 * @description
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;
}
