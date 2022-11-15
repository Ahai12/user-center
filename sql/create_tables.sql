-- auto-generated definition
create table user_info
(
    id            bigint auto_increment comment '用户id
'
        primary key,
    user_name     varchar(256)                       null comment '用户名称
',
    user_account  varchar(256)                       null comment '用户账号
',
    avatar_url    varchar(1024)                      null comment '用户头像',
    gender        tinyint                            null comment '性别
',
    user_password varchar(512)                       not null comment '密码
',
    phone         varchar(128)                       null comment '电话',
    email         varchar(512)                       null comment '邮箱
',
    user_status   int      default 0                 not null comment '用户状态 -0 正常',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '用户创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '用户更新时间',
    is_delete     tinyint  default 0                 not null comment '是否删除 ',
    user_role     int      default 0                 not null comment '用户级别 -0 普通用户，-1 管理员'
)
    comment '用户信息';
