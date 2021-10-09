package com.halo.blog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Halo
 * @create 2021/10/09 下午 06:14
 * @description
 */
@Data
public class LinkVo implements Serializable {

    private Long linkId;

    private String linkTitle;

    private String link;

    private String linkDescription;

    private String linkCover;

    private Integer linkLike;

    private Integer isPublish;

    private Integer isOpenComment;

    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}