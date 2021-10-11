package com.halo.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Halo
 * @since 2021-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_link_user")
public class LinkUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    private Long id;

    /**
     * 链接ID
     */
    private Long linkUuid;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否删除(0:否,1:是)
     */
    private Integer deleted;


}
