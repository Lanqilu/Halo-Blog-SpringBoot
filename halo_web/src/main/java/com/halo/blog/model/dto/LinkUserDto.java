package com.halo.blog.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @create 2021/10/11 下午 04:09
 * @description 用户和链接关系表
 */
@Data
public class LinkUserDto implements Serializable {
    /**
     * 链接ID
     */
    private Long linkId;

    /**
     * 用户ID
     */
    private Long uid;

}
