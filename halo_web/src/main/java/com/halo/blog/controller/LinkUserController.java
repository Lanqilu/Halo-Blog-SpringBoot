package com.halo.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.blog.common.Result;
import com.halo.blog.entity.LinkNav;
import com.halo.blog.entity.LinkUser;
import com.halo.blog.model.dto.LinkUserDto;
import com.halo.blog.service.LinkNavService;
import com.halo.blog.service.LinkUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Halo
 * @since 2021-10-11
 */
@RestController
@Api(tags = "链接用户关系表操作接口")
@RequestMapping("/link-user")
public class LinkUserController {
    @Autowired
    LinkUserService linkUserService;
    @Autowired
    LinkNavService linkNavService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/edit")
    public Result edit(@RequestBody LinkUserDto linkUserDto) {
        LinkUser temp = new LinkUser();
        temp.setLinkUuid(linkUserDto.getLinkId());
        temp.setUid(linkUserDto.getUid());
        temp.setCreateTime(LocalDateTime.now());
        linkUserService.saveOrUpdate(temp);
        return Result.success();
    }

    /**
     * 根据用户ID查询该用户创建的链接
     * SELECT link_title FROM `m_link_nav` WHERE link_uuid IN (
     * SELECT link_uuid FROM `m_link_user` WHERE uid = 1
     * );
     */
    @GetMapping("/getLink/{uid}")
    public Result getLinkByUid(@PathVariable Long uid) {
        // 从数据库中查询
        List<LinkUser> list = linkUserService.getAllLink(uid);
        ArrayList<LinkNav> linkNavs = new ArrayList<>();
        for (LinkUser linkUser : list) {
            QueryWrapper<LinkNav> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("link_uuid", linkUser.getLinkUuid());
            LinkNav one = linkNavService.getOne(wrapper1);
            linkNavs.add(one);
        }
        System.out.println(linkNavs);
        return Result.success(linkNavs).message("链接查询成功");
    }

    @GetMapping("/{id}")
    public Result getLink(@PathVariable Long id) {
        LinkUser one = linkUserService.getById(id);
        System.out.println(one.getLinkUuid());
        return Result.success();
    }
}
