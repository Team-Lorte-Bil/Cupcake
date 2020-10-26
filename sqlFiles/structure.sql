CREATE TABLE `CakeOptions`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` enum('topping','bottom') NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double(10, 2) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `CakesOnOrder`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `orderId` int(0) NULL,
  `bottomId` int(0) NULL,
  `toppingId` int(0) NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userId` int(0) NULL,
  `comment` varchar(255) NULL,
  `createdAt` timestamp NULL,
  `paid` boolean NULL,
  `completed` boolean NULL DEFAULT false,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phoneno` int(12) NOT NULL,
  `salt` blob NOT NULL,
  `secret` blob NOT NULL,
  `role` enum('User','Admin') NOT NULL,
  `createdAt` timestamp NOT NULL,
  `accountBalance` double(255, 0) NOT NULL,
  PRIMARY KEY (`id`, `email`, `phoneno`)
);

ALTER TABLE `CakesOnOrder` ADD CONSTRAINT `CakeIdOnOrderId` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`);
ALTER TABLE `CakesOnOrder` ADD CONSTRAINT `BottomIdOnCakeId` FOREIGN KEY (`bottomId`) REFERENCES `CakeOptions` (`id`);
ALTER TABLE `CakesOnOrder` ADD CONSTRAINT `ToppingIdOnCakeId` FOREIGN KEY (`toppingId`) REFERENCES `CakeOptions` (`id`);
ALTER TABLE `Orders` ADD CONSTRAINT `UserOnOrder` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`);

