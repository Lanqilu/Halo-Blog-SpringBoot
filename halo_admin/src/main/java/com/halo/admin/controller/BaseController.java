package com.halo.admin.controller;


import halo.base.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author HALO
 */
public class BaseController {

    @Autowired
    RedisUtil redisUtil;

}
