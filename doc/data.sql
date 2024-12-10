아이디 : admin123!@#

-- User 데이터 추가
INSERT INTO user (id, name, email, password, status, address, point, created_at, updated_at, salt) VALUES 
('kku_1', '관리자', 'admin@gmail.com', '2LAeQ7P9fsspkapLqkIVvV0bDDIL2dW4RDc9N+4yZok=', '남성', '서울특별시', 0, NOW(), NOW(), 'WSUXy3KjUYtlQdm6RN40pQ==');


-- UserRole 데이터 추가
INSERT INTO user_role (user_id, role_id, role) VALUES ('kku_1', '1000', '관리자');


-- Product 데이터 추가

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `itemDetail` text NOT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1lw5pjqm2vwgl2eah12iq9j4d` (`user_id`),
  CONSTRAINT `FK1lw5pjqm2vwgl2eah12iq9j4d` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
