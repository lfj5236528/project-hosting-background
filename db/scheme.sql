CREATE TABLE `basic_users`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` VARCHAR (40) NOT NULL COMMENT'用户名',
`email` VARCHAR (40) DEFAULT NULL ,
`mobile` VARCHAR (40) NOT NULL COMMENT'手机号,可作为账号登录',
`password` VARCHAR (40) NOT NULL COMMENT'用户密码',
`status` INTEGER DEFAULT 1 COMMENT'用户状态: 1:正常,0:冻结,-1:删除',
`group_ids` VARCHAR (1024) DEFAULT NULL COMMENT'用户所属组ID,可以是多个组,数组类型',
`role_json` VARCHAR (1024) DEFAULT NULL COMMENT'用户角色信息',
`extra_json` varchar(1024) DEFAULT NULL COMMENT '用户额外信息,json字符串',
`created_at` datetime NOT NULL,
`updated_at` datetime NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY `idx_users_name` (`name`),
UNIQUE KEY `idx_users_email` (`email`),
UNIQUE KEY `idx_users_mobile` (`mobile`)
)COMMENT'基础用户表';


CREATE TABLE `operator_roles`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` VARCHAR (40) NOT NULL COMMENT'角色名称',
`desc` VARCHAR (2048) DEFAULT NULL COMMENT'角色职责描述',
`status` INTEGER DEFAULT 1 COMMENT'角色状态: 1:生效 ,-1:失效',
`allow_json` VARCHAR (2048) DEFAULT NULL COMMENT'角色权限',
`extra_json` varchar(4096) DEFAULT NULL COMMENT '角色额外信息,json字符串',
`created_at` datetime NOT NULL,
`updated_at` datetime NOT NULL,
PRIMARY KEY (`id`)
)COMMENT'用户权限表';


CREATE TABLE `project_groups`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`parent_id` bigint(20) DEFAULT NULL COMMENT'父级团队',
`name` VARCHAR (40) NOT NULL COMMENT'团队名称',
`desc_message` VARCHAR (2048) DEFAULT NULL COMMENT'团队描述',
`leader_id` bigint(20) NOT NULL  COMMENT'团队负责人ID',
`members_ids` VARCHAR (1024) DEFAULT NULL COMMENT'团队成员,数组形式',
`extra_json` varchar(4096) DEFAULT NULL COMMENT '团队额外信息,json字符串',
`created_at` datetime NOT NULL,
`updated_at` datetime NOT NULL,
PRIMARY KEY (`id`)
)COMMENT'项目团队表';


CREATE TABLE `projects`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` VARCHAR (40) NOT NULL COMMENT'项目名称',
`desc_message` VARCHAR (2048) DEFAULT NULL COMMENT'项目描述',
`creater_id` bigint(20) NOT NULL  COMMENT'项目创建ID',
`groups_ids` VARCHAR (1024) DEFAULT NULL COMMENT'团队ID,数组形式',
`extra_json` varchar(4096) DEFAULT NULL COMMENT '项目额外信息,json字符串',
`created_at` datetime NOT NULL,
`updated_at` datetime NOT NULL,
PRIMARY KEY (`id`)
)COMMENT'项目表';



CREATE TABLE `project_tasks`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` VARCHAR (40) NOT NULL COMMENT'任务名称',
`desc_message` VARCHAR (2048) DEFAULT NULL COMMENT'任务描述',
`status` int(11) DEFAULT 0 COMMENT '状态',
`project_id` bigint(20) DEFAULT NULL COMMENT'所属项目Id',
`user_id` VARCHAR (1024) DEFAULT NULL COMMENT'所属负责人',
`parent_id` bigint (20) DEFAULT NULL COMMENT'父级Id',
`extra_json` varchar(4096) DEFAULT NULL COMMENT '任务额外信息,json字符串',
`created_at` datetime NOT NULL,
`updated_at` datetime NOT NULL,
`ended_at` datetime ,
PRIMARY KEY (`id`)
)COMMENT'项目任务表';



CREATE TABLE `notify_articles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '发布者ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT'发布者名称',
  `theme` varchar(2048) COLLATE utf8_unicode_ci NOT NULL COMMENT '主题',
  `content` text COLLATE utf8_unicode_ci COMMENT '内容',
  `status` int(11) DEFAULT 0 COMMENT '状态,1:发布,-1：删除',
  `notify_members` varchar(2048)  DEFAULT NULL COMMENT  '通知对象,可多个,json格式',
  `extra_json` varchar(2048)DEFAULT NULL COMMENT '额外信息',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='通知表';


CREATE TABLE `user_posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '发布者ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT'发布者名称',
  `title` varchar(2048) NOT NULL COMMENT '主题',
  `content` text NOT NULL COMMENT '内容',
  `status` int(11) DEFAULT 0 COMMENT '状态,1:发布,-1：删除',
  `reply_info` text  DEFAULT NULL COMMENT  '评论信息,json格式',
  `extra_json` varchar(2048)DEFAULT NULL COMMENT '额外信息',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='用户帖子';






