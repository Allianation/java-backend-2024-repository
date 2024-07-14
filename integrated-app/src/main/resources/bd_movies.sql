CREATE TABLE `movie` ( 
    `id` int(11) NOT NULL, 
    `title` varchar(255) NOT NULL, 
    `duration` varchar(6) NOT NULL, 
    `genre` varchar(255) NOT NULL, 
    `image` varchar(255) NOT NULL
);

ALTER TABLE `movie` ADD PRIMARY KEY (`id`);
ALTER TABLE `movie` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

INSERT INTO `movie` (`title`, `duration`, `genre`, `image`) VALUES
('The shawshank Redemption', '3h 2m', 'Suspenso/Crimen', 'aclamada_1.jpg'),
('The Godfather', '3h 2m', 'Crimen', 'aclamada_2.jpg'),
('The Godfather 2', '3h 2m', 'Crimen', 'aclamada_3.jpg'),
('Schindler List', '3h 50m', 'Drama', 'aclamada_4.jpg'),
('12 angry man', '4h 50m', 'Drama', 'aclamada_5.jpg'),
('Batman', '2h 50m', 'Fantasia', 'aclamada_7.jpg'),
('Spirited away', '2h 5m', 'Fantasia/Aventura ', 'aclamada_6.jpg'),
('The Lord of the Rings', '3h 55m', 'Aventura', 'aclamada_12.jpg');