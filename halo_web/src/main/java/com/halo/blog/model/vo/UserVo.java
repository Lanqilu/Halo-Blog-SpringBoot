package com.halo.blog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @create 2021/09/28 下午 08:30
 * @description 前端用户基本信息
 */
@Data
public class UserVo implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

    private Integer status;

}
