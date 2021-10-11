package com.halo.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 链接导航表实体类
 *
 * @author Halo
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_link_nav")
public class LinkNav implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "link_id", type = IdType.AUTO)
    private Long linkId;

    /**
     * 唯一ID
     */
    private Long linkUuid;

    /**
     * 链接创建者ID
     */
    private Long linkUid;


    /**
     * 链接标题
     */
    private String linkTitle;

    /**
     * 链接描述
     */
    private String linkDescription;

    /**
     * 链接地址
     */
    private String link;

    /**
     * 链接封面图片地址
     */
    private String linkCover;

    /**
     * 链接点赞数
     */
    private Integer linkLike;

    /**
     * 链接状态 (0:正常,1:异常)
     */
    private Integer linkStatus;

    /**
     * 是否发布 (0:否,1:是)
     */
    private Integer isPublish;

    /**
     * 是否开启评论 (0:否,1:是)
     */
    private Integer isOpenComment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除 (0:否,1:是)
     */
    @TableLogic
    private Integer deleted;


}
