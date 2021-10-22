package com.halo.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.admin.mapper.SysUserMapper;
import com.halo.admin.service.SysMenuService;
import com.halo.admin.service.SysRoleService;
import com.halo.admin.service.SysUserService;
import halo.base.entity.SysMenu;
import halo.base.entity.SysRole;
import halo.base.entity.SysUser;
import halo.base.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author HALO
 * @since 2021-04-05
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SysUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 构建注册邮件内容
     *
     * @param email 注册者邮箱
     * @param code  验证码
     * @return 邮件 HTML
     */
    private String getMailContent(String email, String code) {
        return "<html>\r\n" +
                " <head>\r\n" +
                "  <title> Halo </title>\r\n" +
                " </head>\r\n" +
                " <body>\r\n" +
                "  <div id=\"contentDiv\" onmouseover=\"getTop().stopPropagation(event);\" onclick=\"getTop().preSwapLink(event, 'spam', 'ZC1222-PrLAp4T0Z7Z7UUMYzqLkb8a');\" style=\"position:relative;font-size:14px;height:auto;padding:15px 15px 10px 15px;z-index:1;zoom:1;line-height:1.7;\" class=\"body\">    \r\n" +
                "  <div id=\"qm_con_body\"><div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"\">\r\n" +
                "<style>\r\n" +
                "  .qmbox .email-body{color:#40485B;font-size:14px;font-family:-apple-system, \"Helvetica Neue\", Helvetica, \"Nimbus Sans L\", \"Segoe UI\", Arial, \"Liberation Sans\", \"PingFang SC\", \"Microsoft YaHei\", \"Hiragino Sans GB\", \"Wenquanyi Micro Hei\", \"WenQuanYi Zen Hei\", \"ST Heiti\", SimHei, \"WenQuanYi Zen Hei Sharp\", sans-serif;background:#f8f8f8;}.qmbox .pull-right{float:right;}.qmbox a{color:#FE7300;text-decoration:underline;}.qmbox a:hover{color:#fe9d4c;}.qmbox a:active{color:#b15000;}.qmbox .logo{text-align:center;margin-bottom:20px;}.qmbox .panel{background:#fff;border:1px solid #E3E9ED;margin-bottom:10px;}.qmbox .panel-header{font-size:18px;line-height:30px;padding:10px 20px;background:#fcfcfc;border-bottom:1px solid #E3E9ED;}.qmbox .panel-body{padding:20px;}.qmbox .container{width:100%;max-width:600px;padding:20px;margin:0 auto;}.qmbox .text-center{text-align:center;}.qmbox .thumbnail{padding:4px;max-width:100%;border:1px solid #E3E9ED;}.qmbox .btn-primary{color:#fff;font-size:16px;padding:8px 14px;line-height:20px;border-radius:2px;display:inline-block;background:#FE7300;text-decoration:none;}.qmbox .btn-primary:hover,.qmbox .btn-primary:active{color:#fff;}.qmbox .footer{color:#9B9B9B;font-size:12px;margin-top:40px;}.qmbox .footer a{color:#9B9B9B;}.qmbox .footer a:hover{color:#fe9d4c;}.qmbox .footer a:active{color:#b15000;}.qmbox .email-body#mail_to_teacher{line-height:26px;color:#40485B;font-size:16px;padding:0px;}.qmbox .email-body#mail_to_teacher .container,.qmbox .email-body#mail_to_teacher .panel-body{padding:0px;}.qmbox .email-body#mail_to_teacher .container{padding-top:20px;}.qmbox .email-body#mail_to_teacher .textarea{padding:32px;}.qmbox .email-body#mail_to_teacher .say-hi{font-weight:500;}.qmbox .email-body#mail_to_teacher .paragraph{margin-top:24px;}.qmbox .email-body#mail_to_teacher .paragraph .pro-name{color:#000000;}.qmbox .email-body#mail_to_teacher .paragraph.link{margin-top:32px;text-align:center;}.qmbox .email-body#mail_to_teacher .paragraph.link .button{background:#4A90E2;border-radius:2px;color:#FFFFFF;text-decoration:none;padding:11px 17px;line-height:14px;display:inline-block;}.qmbox .email-body#mail_to_teacher ul.pro-desc{list-style-type:none;margin:0px;padding:0px;padding-left:16px;}.qmbox .email-body#mail_to_teacher ul.pro-desc li{position:relative;}.qmbox .email-body#mail_to_teacher ul.pro-desc li::before{content:'';width:3px;height:3px;border-radius:50%;background:red;position:absolute;left:-15px;top:11px;background:#40485B;}.qmbox .email-body#mail_to_teacher .blackboard-area{height:600px;padding:40px;background-image:url();color:#FFFFFF;}.qmbox .email-body#mail_to_teacher .blackboard-area .big-title{font-size:32px;line-height:45px;text-align:center;}.qmbox .email-body#mail_to_teacher .blackboard-area .desc{margin-top:8px;}.qmbox .email-body#mail_to_teacher .blackboard-area .desc p{margin:0px;text-align:center;line-height:28px;}.qmbox .email-body#mail_to_teacher .blackboard-area .card:nth-child(odd){float:left;margin-top:45px;}.qmbox .email-body#mail_to_teacher .blackboard-area .card:nth-child(even){float:right;margin-top:45px;}.qmbox .email-body#mail_to_teacher .blackboard-area .card .title{font-size:18px;text-align:center;margin-bottom:10px;}\r\n" +
                "</style>\r\n" +
                "<meta>\r\n" +
                "<div class=\"email-body\" style=\"background-color: rgb(246, 244, 236);\">\r\n" +
                "<div class=\"container\">\r\n" +
                "<div class=\"logo\">\r\n" +
                "<img src=\"https://img.imgdb.cn/item/608289f3d1a9ae528feb09a8.jpg\",height=\"100\" width=\"100\">\r\n" +
                "</div>\r\n" +
                "<div class=\"panel\" style=\"background-color: rgb(246, 244, 236);\">\r\n" +
                "<div class=\"panel-header\" style=\"background-color: rgb(246, 244, 236);\">\r\n" +
                "注册验证\r\n" +
                "\r\n" +
                "</div>\r\n" +
                "<div class=\"panel-body\">\r\n" +
                "<p>您好 <a href=\"mailto:" + "Halo" + "\" rel=\"noopener\" target=\"_blank\">" + email + "<wbr></a>！</p>\r\n" +
                "<p>欢迎注册，请将验证码填写到注册页面。</p>\r\n" +
                "<p>验证码：" + code + "</p>\r\n" +
                "<p>验证码 10 分钟内有效</p>\r\n" +
                "</div>\r\n" +
                "</div>\r\n" +
                "<div class=\"footer\">\r\n" +
                "@halo123.top\r\n" +
                "<div class=\"pull-right\"></div>\r\n" +
                "</div>\r\n" +
                "</div>\r\n" +
                "</div>\r\n" +
                "<style type=\"text/css\">.qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {display: none !important;}</style></div></div><!-- --><style>#mailContentContainer .txt {height:auto;}</style>  </div>\r\n" +
                " </body>\r\n" +
                "</html>";
    }

    @Override
    public boolean sentAuthMail(String email) {
        // 先从 Redis 中查询
        String authCode = getAuthCode(email);
        // 如果 Redis 中没有，才发送
        if (Objects.equals(authCode, "")) {
            Random random = new Random();
            authCode = String.valueOf(random.nextInt(99999));
            // redis 存验证码, 10 分钟过期
            redisTemplate.opsForValue().set(email, authCode, 600, TimeUnit.SECONDS);
        }
        try {
            // 构建邮件内容
            Map<String, String> map = new HashMap<>(3);
            map.put("subject", "Halo");
            map.put("receiver", "885240677@qq.com");
            map.put("text", getMailContent(email, authCode));
            // 发送到RabbitMq
            rabbitTemplate.convertAndSend("exchange.halo", "halo.email", map);
            log.info("邮箱信息发送到 RabbitMq 成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String getAuthCode(String email) {
        Object o = redisTemplate.opsForValue().get(email);
        if (o != null) {
            return o.toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean isRegisterByEmail(String email) {
        QueryWrapper<SysUser> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", email);
        SysUser user = userService.getOne(emailQueryWrapper);
        return user != null;
    }

    @Override
    public boolean isRegisterByUserName(String userName) {
        QueryWrapper<SysUser> userNameQueryWrapper = new QueryWrapper<>();
        userNameQueryWrapper.eq("username", userName);
        SysUser user = userService.getOne(userNameQueryWrapper);
        return user != null;
    }


    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());
        } else {
            // 获取角色编码
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            // 获取菜单操作编码
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }

            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);
        }

        return authority;
    }


}
