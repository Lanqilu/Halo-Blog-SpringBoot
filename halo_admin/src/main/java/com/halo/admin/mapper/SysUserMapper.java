package com.halo.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import halo.base.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HALO
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<Long> getNavMenuIds(Long userId);

}
