package com.halo.blog.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Halo
 * @since 2021-08-13
 */
@RestController
@Api(tags = "博客标签接口")
@RequestMapping("/blog/tag")
public class BlogTagController {

}
