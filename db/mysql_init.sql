create database if not exists wallet;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(1000) NOT NULL,
  `email` varchar(1000) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `wallet`;
CREATE TABLE `wallet`(
`id` int NOT NULL AUTO_INCREMENT,
`cash` double NOT NULL,
`uid` int NOT NULL,
PRIMARY KEY(`id`),
KEY `wallet_uid` (`uid`),
CONSTRAINT `wallet_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`(
`id` int NOT NULL AUTO_INCREMENT,
`cardnumber` varchar(16) NOT NULL,
`expirydate` date,
`cvv` int,
`uid` int NOT NULL,
PRIMARY KEY(`id`),
UNIQUE KEY `cardnumber` (`cardnumber`),
KEY `card_uid` (`uid`),
CONSTRAINT `card_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions`(
`id` int NOT NULL AUTO_INCREMENT,
`account` varchar(18), 
`amount` double NOT NULL,
`comments` varchar(1000) NOT NULL,
`time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`user` int NOT NULL,
PRIMARY KEY(`id`),
KEY `trans_uid` (`user`),
CONSTRAINT `trans_uid` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

commit;

INSERT into user (`username`, `email`, `password`) values ('admin', 'etjs@vireonidae.com', 'QeJ8JFX9gWz');
INSERT into user (`username`, `email`, `password`) values ('Steve', 'steve@mailpremium.net', 'secret');
INSERT into user (`username`, `email`, `password`) values ('Robin', 'robin@xlsmail.com', 'world');

INSERT into card (`cardnumber`, `expirydate`, `cvv`, `uid`) values ('1234567812345678', str_to_date('20211201','%Y%m%d') , 123, 1);
INSERT into card (`cardnumber`, `expirydate`, `cvv`, `uid`) values ('1234567812345671', str_to_date('20211202','%Y%m%d') , 456, 1);
INSERT into card (`cardnumber`, `expirydate`, `cvv`, `uid`) values ('1234567812345672', str_to_date('20211203','%Y%m%d') , 128, 1);
INSERT into card (`cardnumber`, `expirydate`, `cvv`, `uid`) values ('1234567812345673', str_to_date('20211204','%Y%m%d') , 129, 2);
INSERT into card (`cardnumber`, `expirydate`, `cvv`, `uid`) values ('1234567812345679', str_to_date('20211208','%Y%m%d') , 130, 2);


INSERT into wallet (`cash`, `uid`) values (1000, 1);
INSERT into wallet (`cash`, `uid`) values (999, 2);
INSERT into wallet (`cash`, `uid`) values (9999, 3);

INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456781', '100', '', STR_TO_DATE('20180820', '%Y%m%d'), 1);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456702', '1000', '', STR_TO_DATE('20180920', '%Y%m%d'), 1);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456713', '999', '', STR_TO_DATE('20190513', '%Y%m%d'), 1);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456724', '200', '', STR_TO_DATE('20190517', '%Y%m%d'), 1);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456735', '100', '', STR_TO_DATE('20200119', '%Y%m%d'), 1);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456746', '1000', '', STR_TO_DATE('20200119', '%Y%m%d'), 1);

INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456781', '999', '', STR_TO_DATE('20191020', '%Y%m%d'), 2);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456778', '9999', '', STR_TO_DATE('20191120', '%Y%m%d'), 2);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456701', '999', '', STR_TO_DATE('20200613', '%Y%m%d'), 2);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456712', '250', '', STR_TO_DATE('20200617', '%Y%m%d'), 2);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456723', '150', '', STR_TO_DATE('20210219', '%Y%m%d'), 2);
INSERT into transactions (`account`, `amount`, `comments`, `time`, `user`) values ('123456707', '1790', '', STR_TO_DATE('20210219', '%Y%m%d'), 2);

commit;
