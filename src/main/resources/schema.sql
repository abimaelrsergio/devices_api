CREATE TABLE IF NOT EXISTS `device` (
    `device_id` int AUTO_INCREMENT  PRIMARY KEY,
    `device_name` varchar(50) NOT NULL,
    `device_brand` varchar(100) NOT NULL,
    `device_state` varchar(20) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );