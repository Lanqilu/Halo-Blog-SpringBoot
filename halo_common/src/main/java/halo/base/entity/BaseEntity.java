package halo.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Halo
 * @create 2021/10/22 下午 08:26
 * @description
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 数据库 id, 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 更新时间
     */
    private LocalDateTime updated;
    /**
     * 状态 1: 正常; 0: 禁用
     */
    private Integer status;
    /**
     * 逻辑删除 1: 删除; 0: 未删除
     */
    @TableLogic
    private Integer deleted;
}