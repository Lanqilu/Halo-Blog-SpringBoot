package com.halo.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.halo.blog.common.Result;
import com.halo.blog.entity.LinkNav;
import com.halo.blog.model.dto.LinkDto;
import com.halo.blog.model.vo.LinkVo;
import com.halo.blog.service.LinkNavService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 链接导航
 *
 * @author Halo
 * @since 2021-10-09
 */
@RestController
@Api(tags = "链接导航接口")
@RequestMapping("/link")
public class LinkNavController {
    @Autowired
    LinkNavService linkNavService;

    @PostMapping("/edit")
    @ApiOperation(value = "编辑链接信息")
    public Result editLink(@Validated @RequestBody LinkDto linkDto) {
        LinkNav tempLinkNav;
        String msg = "";
        LocalDateTime now = LocalDateTime.now();
        // 如果表单中存在LinkID,判断为修改
        if (linkDto.getLinkId() != null) {
            // 通过ID查询Link
            tempLinkNav = linkNavService.getById(linkDto.getLinkId());
            // Link 不存在返回错误信息
            if (StringUtils.isEmpty(tempLinkNav)) {
                msg = "链接ID不存在";
                return Result.fail().message(msg);
            }
            msg = "修改链接信息成功";
        } else {
            // 表单中不存在LinkID,判断为新增
            tempLinkNav = new LinkNav();
            // 设置创建时间
            tempLinkNav.setCreateTime(now);
            msg = "新增链接成功";
        }
        // 设置更新时间
        tempLinkNav.setUpdateTime(now);
        // 将表单对象中的值复制到实体类中
        BeanUtil.copyProperties(linkDto, tempLinkNav);
        // 提交事务
        linkNavService.saveOrUpdate(tempLinkNav);
        return Result.success().message(msg);
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "删除链接")
    public Result deleteLink(Long linkId) {
        boolean result = linkNavService.removeById(linkId);
        if (result) {
            return Result.success().message("删除链接成功");
        }
        return Result.fail().message("删除链接失败");
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取所有公共链接")
    public Result getAllPublicLink() {
        ArrayList<LinkVo> linkVos = new ArrayList<>();
        for (LinkNav linkNav : linkNavService.getAllPublicLink()) {
            LinkVo linkVo = new LinkVo();
            BeanUtil.copyProperties(linkNav, linkVo);
            linkVos.add(linkVo);
        }
        return Result.success(200, "成功", linkVos);
    }
}
