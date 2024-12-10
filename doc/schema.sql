create database kwanho10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
create user `kwanho10`@`localhost` identified by 'kwanho10';  
grant all privileges on kwanho10.* to `kwanho10`@`localhost` ;
 




CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `point` bigint(20) DEFAULT NULL,
  `salt` varchar(200) DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhl4ga9r00rh51mdaf20hmnslt` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `user_role` (
  `role` varchar(45) DEFAULT NULL,
  `role_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FKfgsgxvihks805qcq8sq26ab7c` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKfgsgxvihks805qcq8sq26ab7c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `role` (
  `id` varchar(50) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `role` varchar(200) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;




CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1lw5pjqm2vwgl2eah12iq9j4d` (`user_id`),
  CONSTRAINT `FK1lw5pjqm2vwgl2eah12iq9j4d` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_date` datetime(6) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgj6qg9p2bi6csya9xy00rvgik` (`user_id`),
  CONSTRAINT `FKgj6qg9p2bi6csya9xy00rvgik` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


























