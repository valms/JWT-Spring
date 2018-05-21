-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 18, 2018 at 05:27 PM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `chaton`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

CREATE TABLE `channel` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `description` text,
  `is_open` bit(1) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL,
  `name` varchar(90) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `channel`
--

INSERT INTO `channel` (`id`, `created_at`, `updated_at`, `description`, `is_open`, `created_by`, `updated_by`, `name`) VALUES
(1, '2018-05-14 18:14:44', '2018-05-14 18:14:44', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 2, 2, 'Fortaleza'),
(2, '2018-05-14 18:17:24', '2018-05-14 18:17:24', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 2, 2, 'Fortaleza'),
(3, '2018-05-14 18:19:09', '2018-05-14 18:19:09', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 2, 2, 'Fortaleza'),
(4, '2018-05-14 18:35:42', '2018-05-14 18:35:42', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 2, 2, 'Fortaleza'),
(5, '2018-05-14 18:36:52', '2018-05-14 18:36:52', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 2, 2, 'Fortaleza'),
(6, '2018-05-14 18:49:04', '2018-05-14 18:49:04', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 6, 6, 'Fortaleza'),
(7, '2018-05-14 18:54:45', '2018-05-14 18:54:45', 'BLA BLA BLA BLA BLA BLA BLA', b'0', 6, 6, 'Fortaleza'),
(8, '2018-05-14 18:56:40', '2018-05-14 18:56:40', 'BLA BLA BLA BLA BLA BLA BLA', b'1', 6, 6, 'Fortaleza'),
(9, '2018-05-14 19:11:27', '2018-05-14 19:11:27', 'haha', b'1', 6, 6, 'Goiania'),
(10, '2018-05-14 19:33:21', '2018-05-14 19:33:21', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur rhoncus odio tortor, ac ultrices felis bibendum sit amet. Phasellus quis dignissim justo. Aliquam erat volutpat. Mauris scelerisque, odio id ullamcorper rhoncus, mi purus condimentum ex, et porta lacus eros at mauris. Aliquam orci tortor, placerat sit amet consequat placerat, hendrerit ut diam. Nunc eu ante neque. Integer ac placerat orci. Integer ornare nisi at tortor feugiat, a malesuada felis ullamcorper. Proin lacinia in diam sit amet egestas. Vestibulum in dolor sed leo varius tincidunt. Vestibulum fringilla quis risus sed volutpat. Aenean laoreet, felis quis tempor facilisis, leo nisl volutpat ipsum, in dignissim magna odio et justo.', b'1', 6, 6, 'Goiania'),
(11, '2018-05-15 19:22:07', '2018-05-15 19:22:07', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur rhoncus odio tortor, ac ultrices felis bibendum sit amet. Phasellus quis dignissim justo. Aliquam erat volutpat. Mauris scelerisque, odio id ullamcorper rhoncus, mi purus condimentum ex, et porta lacus eros at mauris. Aliquam orci tortor, placerat sit amet consequat placerat, hendrerit ut diam. Nunc eu ante neque. Integer ac placerat orci. Integer ornare nisi at tortor feugiat, a malesuada felis ullamcorper. Proin lacinia in diam sit amet egestas. Vestibulum in dolor sed leo varius tincidunt. Vestibulum fringilla quis risus sed volutpat. Aenean laoreet, felis quis tempor facilisis, leo nisl volutpat ipsum, in dignissim magna odio et justo.', b'1', 6, 6, 'Fortaleza/Ceara');

-- --------------------------------------------------------

--
-- Table structure for table `favorite_channel`
--

CREATE TABLE `favorite_channel` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `channel_id` bigint(20) NOT NULL,
  `profile_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `favorite_channel`
--

INSERT INTO `favorite_channel` (`id`, `created_at`, `updated_at`, `channel_id`, `profile_id`) VALUES
(1, '2018-05-15 19:29:40', '2018-05-15 19:29:40', 11, 1),
(2, '2018-05-16 17:12:27', '2018-05-16 17:12:27', 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_main` bit(1) NOT NULL,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `city` varchar(100) DEFAULT NULL,
  `enable_notification` bit(1) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `nickname` varchar(90) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `created_at`, `updated_at`, `city`, `enable_notification`, `gender`, `nickname`, `user_id`) VALUES
(1, '2018-05-08 19:40:35', '2018-05-08 19:40:35', 'Fortaleza', b'1', 'MASCULINO', 'Valms', 2),
(2, '2018-05-09 20:39:57', '2018-05-09 20:39:57', 'Fortaleza', b'1', 'MASCULINO', 'Valmas', 3),
(3, '2018-05-10 19:44:56', '2018-05-10 19:44:56', 'Goiania', b'1', 'FEMININO', 'chris', 4),
(5, '2018-05-14 18:48:06', '2018-05-14 18:48:06', 'Goiania', b'1', 'FEMININO', 'chris1', 6),
(6, '2018-05-15 17:32:06', '2018-05-15 17:32:06', 'Goiania', b'1', 'FEMININO', 'valmarjunior', 7),
(7, '2018-05-17 21:28:26', '2018-05-17 21:28:26', 'Goiania', b'1', 'FEMININO', 'chris8', 8),
(8, '2018-05-17 21:29:42', '2018-05-17 21:29:42', 'Goiania', b'1', 'FEMININO', 'chris10', 9);

-- --------------------------------------------------------

--
-- Table structure for table `profile_channel`
--

CREATE TABLE `profile_channel` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_moderator` bit(1) NOT NULL,
  `channel_id` bigint(20) NOT NULL,
  `profile_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `profile_channel`
--

INSERT INTO `profile_channel` (`id`, `created_at`, `updated_at`, `is_moderator`, `channel_id`, `profile_id`) VALUES
(1, '2018-05-14 18:14:44', '2018-05-14 18:14:44', b'1', 1, 1),
(2, '2018-05-14 18:17:24', '2018-05-14 18:17:24', b'1', 2, 1),
(3, '2018-05-14 18:19:09', '2018-05-14 18:19:09', b'1', 3, 1),
(4, '2018-05-14 18:35:42', '2018-05-14 18:35:42', b'1', 4, 1),
(5, '2018-05-14 18:36:52', '2018-05-14 18:36:52', b'1', 5, 1),
(6, '2018-05-14 18:49:05', '2018-05-14 18:49:05', b'1', 6, 5),
(7, '2018-05-14 18:56:40', '2018-05-14 18:56:40', b'1', 8, 5),
(8, '2018-05-14 19:11:27', '2018-05-14 19:11:27', b'1', 9, 5),
(9, '2018-05-14 19:33:21', '2018-05-14 19:33:21', b'1', 10, 5),
(10, '2018-05-15 19:22:08', '2018-05-15 19:22:08', b'1', 11, 5),
(11, '2018-05-17 00:00:00', '2018-05-17 00:00:00', b'0', 11, 1),
(12, '2018-05-17 20:00:44', '2018-05-17 20:00:44', b'0', 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'ROLE_USER', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, 'ROLE_ADMIN', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `agenda` bit(1) NOT NULL,
  `camera` bit(1) NOT NULL,
  `notifications` bit(1) NOT NULL,
  `settings_id` bigint(20) DEFAULT NULL,
  `profile_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `created_at`, `updated_at`) VALUES
(2, 'valmar.junior1@gmail.com', '$2a$10$Yw6YG3zdoQmZpF33RRCvUOaLqieXr4MUcdZzgkaB0JGJn1EO46MXW', '2018-05-08 19:40:35', '2018-05-08 19:40:35'),
(3, 'valmar.junior2@gmail.com', '$2a$10$5AhSXqfKLpmgwloxkS1pL.4xQogNwV7cOTaQVYScbEU1Vp2rYBiHS', '2018-05-09 20:39:57', '2018-05-09 20:39:57'),
(4, 'chris.flores@gmail.com', '$2a$10$lhTjugStJ/jT4NXNDKcU1ObaUsZ7wrAQbnMh1RpHPwxK0s4SjbRtO', '2018-05-10 19:44:55', '2018-05-10 19:44:55'),
(6, 'chris.flores1@gmail.com', '$2a$10$jdJ7mMNJoQuSGEV9la8jz.LXz67uShWktODV1nbWCWwyFsDsXmIp2', '2018-05-14 18:48:05', '2018-05-14 18:48:05'),
(7, 'valmar.junior4@gmail.com', '$2a$10$Xqnm9tJ4bLLbRFmFfeB9ouul9sEr6Eprh2oZzMR7ZAPPqiaPOkQ3a', '2018-05-15 17:32:06', '2018-05-15 17:32:06'),
(8, 'chris.flores8@gmail.com', '$2a$10$iAKyEFg8hElWRutkFtOki.H5CKpK5H.PyL8nOkNIQwfUpQBxkvQOG', '2018-05-17 21:28:25', '2018-05-17 21:28:25'),
(9, 'chris.flores18@gmail.com', '$2a$10$SETiDuDI8wFUQCuEIhvJ2.azUOtz960j.VPbMAoyAwzXTvtzSmTB.', '2018-05-17 21:29:42', '2018-05-17 21:29:42');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(2, 1),
(3, 1),
(4, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `waitlist_channel`
--

CREATE TABLE `waitlist_channel` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `channel_id` bigint(20) NOT NULL,
  `profile_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `channel`
--
ALTER TABLE `channel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `favorite_channel`
--
ALTER TABLE `favorite_channel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9nbwd46opd32ohdhp4ytibc2g` (`channel_id`),
  ADD KEY `FK3wdiu7tey7n3gb7v5g8qwodcx` (`profile_id`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2yh8yncwo807rhkftv4ytt243` (`nickname`),
  ADD KEY `FKawh070wpue34wqvytjqr4hj5e` (`user_id`);

--
-- Indexes for table `profile_channel`
--
ALTER TABLE `profile_channel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrqkg916kcyh0cwhdaifjqfs70` (`channel_id`),
  ADD KEY `FKnq4v2tb7ti2c2fj5w5f76t9qv` (`profile_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_epk9im9l9q67xmwi4hbed25do` (`name`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6wjhqpw9e32ep4b75q9pi9hqj` (`settings_id`),
  ADD KEY `FK2w2uiy771sngymsaem66jh18j` (`profile_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_t8tbwelrnviudxdaggwr1kd9b` (`email`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);

--
-- Indexes for table `waitlist_channel`
--
ALTER TABLE `waitlist_channel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4c8t0b7dfs413s9ivsas0i7o7` (`channel_id`),
  ADD KEY `FKk6cdyhir7uqvrfjfwieso4cyv` (`profile_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `channel`
--
ALTER TABLE `channel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `favorite_channel`
--
ALTER TABLE `favorite_channel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `profile_channel`
--
ALTER TABLE `profile_channel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `waitlist_channel`
--
ALTER TABLE `waitlist_channel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favorite_channel`
--
ALTER TABLE `favorite_channel`
  ADD CONSTRAINT `FK3wdiu7tey7n3gb7v5g8qwodcx` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `FK9nbwd46opd32ohdhp4ytibc2g` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FKawh070wpue34wqvytjqr4hj5e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `profile_channel`
--
ALTER TABLE `profile_channel`
  ADD CONSTRAINT `FKnq4v2tb7ti2c2fj5w5f76t9qv` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `FKrqkg916kcyh0cwhdaifjqfs70` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

--
-- Constraints for table `settings`
--
ALTER TABLE `settings`
  ADD CONSTRAINT `FK2w2uiy771sngymsaem66jh18j` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `FK4verw8ent8513ghdga0s51x0f` FOREIGN KEY (`settings_id`) REFERENCES `profile` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `waitlist_channel`
--
ALTER TABLE `waitlist_channel`
  ADD CONSTRAINT `FK4c8t0b7dfs413s9ivsas0i7o7` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`),
  ADD CONSTRAINT `FKk6cdyhir7uqvrfjfwieso4cyv` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`);
