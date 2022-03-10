create table User
(
	userId varchar(40),
    userName varchar(40),
    userPassword varchar(40),
    userType int,
    primary key (userId)
);
drop table user;
insert into User values( "andrew", "Andrew Akhash", "abc123", 1);

create table Test
(
	testId int auto_increment,
    testTitle varchar (20),
    testDesc varchar (200),
    testDiff varchar (40),    
    primary key (testId)
);
alter table Test auto_increment = 100;

create table TestCase
(
	testCaseId int auto_increment,
    input varchar(50),
    output varchar(50),
    testId int,
    primary key (testCaseId),
	foreign key (testId) references Test (testId)
);

delete from User where userId = "haeram";
alter table TestCase auto_increment = 200;
SET FOREIGN_KEY_CHECKS=0;
use editor;

