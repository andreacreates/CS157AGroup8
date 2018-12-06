
/*
	Tables
*/

CREATE TABLE `product` (
  `brand` varchar(20) DEFAULT NULL,
  `model` int(11) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `bought` int(11) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  PRIMARY KEY (`model`),
  UNIQUE KEY `model_index` (`model`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `percussion` (
  `price` float DEFAULT NULL,
  `model` int(11) NOT NULL,
  `kind` varchar(20) DEFAULT NULL,
  `pieces` int(11) DEFAULT NULL,
  PRIMARY KEY (`model`),
  CONSTRAINT `percussion_item` FOREIGN KEY (`model`) REFERENCES `product` (`model`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `guitar` (
  `price` float DEFAULT NULL,
  `model` int(11) NOT NULL,
  `kind` varchar(20) DEFAULT NULL,
  `size` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`model`),
  CONSTRAINT `guitar_item` FOREIGN KEY (`model`) REFERENCES `product` (`model`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `bass` (
  `price` float DEFAULT NULL,
  `model` int(11) NOT NULL,
  `kind` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`model`),
  UNIQUE KEY `model` (`model`),
  CONSTRAINT `bass_item` FOREIGN KEY (`model`) REFERENCES `product` (`model`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `age` int(11) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `browseTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `review` varchar(500) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `reviewed_by` (`user`),
  KEY `item_reviewed` (`model`),
  CONSTRAINT `item_reviewed` FOREIGN KEY (`model`) REFERENCES `product` (`model`) ON DELETE CASCADE,
  CONSTRAINT `reviewed_by` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

CREATE TABLE `sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `item` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bought_by` (`user`),
  KEY `item_bought` (`item`),
  CONSTRAINT `bought_by` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `item_bought` FOREIGN KEY (`item`) REFERENCES `product` (`model`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


/*
	Table Data
*/

INSERT INTO `product` (`brand`, `model`, `type`, `bought`, `views`)
VALUES
	('Yamaha', 1000, 'Guitar', 3, 98),
	('Gipson', 1001, 'Guitar', 8, 84),
	('PRS', 1002, 'Guitar', 8, 26),
	('Yamaha', 1003, 'Guitar', 4, 34),
	('Yamaha', 1004, 'Guitar', 5, 78),
	('Gipson', 1005, 'Guitar', 0, 32),
	('PRS', 1006, 'Guitar', 3, 234),
	('Gipson', 2000, 'Bass', 8, 734),
	('PRS', 2002, 'Bass', 8, 64),
	('Yamaha', 2003, 'Bass', 5, 234),
	('Yamaha', 2004, 'Bass', 0, 97),
	('PRS', 2005, 'Bass', 2, 333),
	('Yamaha ', 2006, 'Bass', 6, 87),
	('Gipson', 2007, 'Bass', 29, 533),
	('Yamaha', 3000, 'Percussion', 0, 56),
	('Yamaha', 3001, 'Percussion', 4, 222),
	('Yamaha', 3002, 'Percussion', 6, 234),
	('Yamaha', 3003, 'Percussion', 32, 536),
	('Yamaha', 3004, 'Percussion', 11, 444),
	('Gipson', 3005, 'Percussion', 11, 777),
	('Gipson', 3006, 'Percussion', 34, 922),
	('Gipson', 3007, 'Percussion', 1, 31);

INSERT INTO `percussion` (`price`, `model`, `kind`, `pieces`)
VALUES
	(350, 3000, 'Drum Set', 4),
	(120, 3001, 'Snare Drum', 1),
	(150, 3002, 'Floor Tom', 1),
	(1245, 3003, 'Drum Set', 6),
	(220, 3004, 'Snare Drum', 1),
	(1532, 3005, 'Drum Set', 6),
	(250, 3006, 'Floor Tom', 1),
	(680, 3007, 'Drum Set', 5);


INSERT INTO `guitar` (`price`, `model`, `kind`, `size`)
VALUES
	(150, 1000, 'Acoustic', 'Full'),
	(300, 1001, 'Electric', 'Full'),
	(200, 1002, 'Acoustic', 'Full'),
	(125, 1003, 'Electric', 'Full'),
	(550, 1004, 'Acoustic', 'Travel'),
	(230, 1005, 'Acoustic', 'Travel'),
	(99, 1006, 'Electric', 'Travel');

INSERT INTO `bass` (`price`, `model`, `kind`)
VALUES
	(150, 2000, 'Electric'),
	(145, 2002, 'Electric'),
	(250, 2003, 'Electric'),
	(333, 2004, 'Electric'),
	(123, 2005, 'Acoustic'),
	(440, 2006, 'Acoustic'),
	(321, 2007, 'Electric');

INSERT INTO `user` (`age`, `gender`, `id`, `browseTime`)
VALUES
	(45, 'F', 1, 45),
	(54, 'M', 2, 65),
	(34, 'M', 3, 44),
	(22, 'F', 4, 66),
	(56, 'M', 5, 52),
	(43, 'M', 6, 98),
	(18, 'M', 7, 7),
	(25, 'M', 8, 80);

INSERT INTO `sales` (`id`, `user`, `item`, `amount`)
VALUES
	(1, 3, 2002, 145),
	(2, 3, 1001, 300),
	(3, 2, 3007, 680),
	(4, 4, 3006, 250),
	(5, 4, 2002, 145),
	(6, 5, 1005, 230),
	(7, 6, 1000, 150);

INSERT INTO `review` (`id`, `model`, `user`, `review`, `rating`)
VALUES
	(22, 2002, 7, 'Very good Bass FOR the price', 9),
	(23, 1001, 2, 'nice guitar!', 8),
	(24, 3007, 5, 'nice set of drums for a beginner', 8),
	(25, 3002, 3, 'not good', 4),
	(26, 1002, 6, 'good price', 8),
	(27, 3006, 4, 'I recommend this Floor tom', 8),
	(29, 3005, 7, 'high quality drum set', 10);



/*
	Views
*/

CREATE VIEW combine_product AS
SELECT
   product.model AS model,
   product.brand AS brand,
   product.type AS TYPE,
   product.bought AS bought,
   product.views AS views,
   combine.price AS price,
   combine.kind AS kind,
   combine.size AS size,
   combine.pieces AS pieces
FROM (((select bass.price AS price,bass.model AS model,bass.kind AS kind,NULL AS size,NULL AS pieces from bass) 
	union 
	select guitar.price AS price,guitar.model AS model,guitar.kind AS kind,guitar.size AS size,NULL AS pieces from guitar 
	union 
	select percussion.price AS price,percussion.model AS model,percussion.kind AS kind,NULL AS size,percussion.pieces AS pieces 
	from percussion) combine 
	join 
	product on((product.model = combine.model)));

CREATE VIEW model_average_rating AS
SELECT
   review.model AS model, avg(review.rating) AS average
FROM review group by review.model;

/*
	Procedures
*/

DELIMITER ;;
CREATE PROCEDURE `add_bass`(IN brand VARCHAR(20), IN model INT, IN price FLOAT, IN kind VARCHAR(20))
BEGIN
INSERT INTO product(brand, model, TYPE, bought, views) VALUES(brand, model, 'Bass', 0, 0);
INSERT INTO bass(price, model, kind) VALUES(price, model, kind);
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `add_guitar`(IN brand VARCHAR(20), IN model INT, IN price FLOAT, IN kind VARCHAR(20), IN size VARCHAR(11))
BEGIN
INSERT INTO product(brand, model, TYPE, bought, views) VALUES(brand, model, 'Guitar', 0, 0);
INSERT INTO guitar(price, model, kind, size) VALUES(price, model, kind, size);
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `add_percussion`(IN brand VARCHAR(20), IN model INT, IN price FLOAT, IN kind VARCHAR(20), IN pieces INT)
BEGIN
INSERT INTO product(brand, model, TYPE, bought, views) VALUES(brand, model, 'Percussion', 0, 0);
INSERT INTO percussion(price, model, kind, pieces) VALUES(price, model, kind, pieces);
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `add_review`(in id integer,
in model integer,
in user integer,
in rev varchar(500),
in rating int)
INSERT INTO review(id, model, user, review, rating)
VALUES(id, model, user, rev, rating);;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `get_gender_stats`()
SELECT gender, AVG(browseTime) AS browseTime, count(id) AS count FROM user GROUP BY gender;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `product_by_brand`()
SELECT brand, AVG(bought), AVG(views), count(brand) FROM product GROUP BY brand;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `product_by_type`()
SELECT TYPE, AVG(bought), AVG(views), count(TYPE) FROM product GROUP BY TYPE;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `update_review`(in revId integer,
in newRev varchar(500),
in newRating int)
UPDATE review
set review = newRev,
rating = newRating
where id = revId;;
DELIMITER ;
