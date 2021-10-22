package halo.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Halo
 * @create 2021/10/22 下午 08:30
 * @description 系统用户类对应数据表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密文密码
     */
    private String password;

    /**
     * 用户头像链接
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 城市
     */
    private String city;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 用户角色, 不在数据表, 通过 SysUserRole 维护
     */
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();

}
