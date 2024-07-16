
-- ----------------------------
-- Table structure for user_role_map
-- ----------------------------
DROP TABLE IF EXISTS `user_role_map`;
CREATE TABLE `user_role_map` (
  `user_id` bigint NULL DEFAULT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE dpeq.`user` (
	id BIGINT auto_increment NOT NULL,
	username varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	rememberme tinyint(1) DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
CREATE INDEX user_username_IDX USING BTREE ON dpeq.`user` (username);
-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE dpeq.`role` (
	id BIGINT auto_increment NOT NULL,
	name varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	description varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
	permissions varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
CREATE INDEX role_name_IDX USING BTREE ON dpeq.`role` (name);


insert into role values (1, 'admin', 'The administrator role only given to site admins', "admin=read,write")
insert into user_role_map(user_id, role_id) values (8, 1)