create table m_blog
(
    id              bigint auto_increment
        primary key,
    user_id         bigint               not null,
    blog_title      varchar(255)         not null,
    description     varchar(255)         not null,
    content         longtext             null,
    created         datetime             not null on update CURRENT_TIMESTAMP,
    status          tinyint    default 1 null comment '状态（1:正常,0:删除）',
    blog_cover      varchar(255)         null,
    blog_like       int        default 0 null,
    update_time     datetime             null,
    tag_uid         varchar(255)         null comment '标签id',
    collect_count   int                  null comment '收藏数',
    blog_sort_uid   varchar(255)         null comment '博客分类id',
    is_publish      tinyint    default 1 null comment '是否发布（0:否,1:是）',
    is_open_comment tinyint(1) default 1 null comment '是否开启评论（0:否,1:是）',
    is_original     tinyint(1) default 0 null comment '是否原创(0:否,1:是)',
    create_time     datetime             null comment '创建时间',
    deleted         tinyint(1) default 0 null
)
    charset = utf8mb4;

create table m_blog_sort
(
    id          bigint auto_increment comment '唯一uid'
        primary key,
    sort_name   varchar(255)                 null comment '分类内容',
    content     varchar(255)                 null comment '分类简介',
    create_time datetime                     not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime                     null on update CURRENT_TIMESTAMP comment '更新时间',
    status      tinyint unsigned default '1' not null comment '状态',
    sort        int              default 0   null comment '排序字段，越大越靠前',
    click_count int              default 0   null comment '点击数',
    user_id     bigint                       null,
    deleted     tinyint(1)       default 0   null
)
    comment '博客分类表' charset = utf8;

create table m_blog_tag
(
    uid         varchar(32)                  not null comment '唯一uid'
        primary key,
    content     varchar(1000)                null comment '标签内容',
    status      tinyint unsigned default '1' not null comment '状态：1[启用]，2[删除]',
    click_count int              default 0   null comment '标签简介',
    create_time datetime                     not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime                     not null on update CURRENT_TIMESTAMP comment '更新时间',
    sort        int              default 0   null comment '排序字段，越大越靠前'
)
    comment '标签表' charset = utf8;

create table m_user
(
    id         bigint auto_increment
        primary key,
    username   varchar(64)                                                                                null,
    avatar     varchar(255) default 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' null,
    email      varchar(64)                                                                                null,
    password   varchar(64)                                                                                null,
    status     int          default 0                                                                     not null,
    created    datetime                                                                                   null,
    last_login datetime                                                                                   null
)
    charset = utf8;

create index UK_USERNAME
    on m_user (username);

