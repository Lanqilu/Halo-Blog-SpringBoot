package halo.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Halo
 * @create 2021/10/22 下午 08:36
 * @description
 */
@Data
public class SysRoleMenu {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色 id
     */
    private Long roleId;
    /**
     * 菜单 id
     */
    private Long menuId;
}
