package com.halo.blog.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @create 2021/10/09 下午 04:26
 * @description Link 导航数据模型
 */
@Data
public class LinkDto implements Serializable {

    private Long linkId;

    private String linkTitle;

    private String link;

    private String linkDescription;

    private String linkCover;

    private Integer isPublish;

    private Integer isOpenComment;
}
