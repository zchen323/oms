create table doctype (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255),
  `sampleUrl` varchar(255),
  PRIMARY KEY (`id`)
);

create table project(
	`id` int(11) not null auto_increment,
	`name` varchar(100) not null,
	`category` varchar(50),
	`description` varchar(500),
	`status` varchar(20),
	`prime` tinyint(1),
	`primeName` varchar(100),
	`primeContactInfo` varchar(200),
	`location` varchar(100),
	`organization` varchar(100),
	`agency` varchar(100),
	`createdDate` datetime,
	`createdBy` varchar(45),
	`startDate` datetime,
	`dueDate` datetime,
	`completedDate` datetime,
	`lastUpdateDate` datetime,
	primary key (`id`)
);


	private Timestamp startDate;
	private Timestamp createdDate;
	private Timestamp dueDate;
	private Timestamp completedDate;

create table task(
	`id` int(11) not null auto_increment,
	`projectId` int(11) not null,
	`name` varchar(100) not null,
	`category` varchar(50),
	`description` varchar(200),
	`status` varchar(20),
	`createdDate` datetime,
	`startDate` datetime,
	`completedDate` datetime,
	`dueDate` datetime,
	primary key (`id`)
);

create table project_users (
	`id` int(11) not null auto_increment,
	`projectId` int(11) not null,
	`username` varchar(45),
	`role` varchar(45),
	primary key (`id`)
);

create table task_users (
	`id` int(11) not null auto_increment,
	`taskId` int(11) not null,
	`username` varchar(45),
	`role` varchar(45),
	primary key (`id`)	
);

create table documents(
	`id` int not null auto_increment,
	`name` varchar(200) not null,
	`description` varchar(1000),
	`doctype` varchar(100),
	`url` varchar(500),
	primary key(`id`)
);

create table userdetail(
	`username` varchar(45) not null,
	`name` varchar(50),
	`company` varchar(100),
	`phone` varchar(60),
	`email` varchar(100),
	`address` varchar(200),
	`isContractor` tinyint(1),
	`fullAccess` tinyint(1),
	`createdTS` datetime,
	primary key(`username`) 
);

create table tasktemplatedoctype(
	`id` int not null auto_increment,
	`taskTemplateId` int not null,
	`doctype` varchar(100),
	primary key(`id`)
)

create table tasktemplatedoctype(
	id int not null auto_increment,
	taskTemplateId int not null,
	docType varchar(50),
	primary key (id)
);

create table taskdoc(
	id int not null auto_increment,
	taskId int not null,
	docType varchar(50),
	primary key(id)
);

create table tasknote(
	id int not null auto_increment,
	taskId int not null,
	title varchar(50),
	content varchar(256),
	createdBy varchar(40),
	createdTime timestamp,
	primary key(id)
);

CREATE TABLE document (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(256) NOT NULL,
	type VARCHAR(30) NOT NULL,
	size INT NOT NULL,
	content MEDIUMBLOB NOT NULL,
	hasMore tinyint(1),
	PRIMARY KEY(id)
);

create table document_additional(
	id int not null auto_increment,
	documentId int not null,
	content MEDIUMBLOB,
	primary key(id)
);

create table projecttaskdocument(
	id int not null auto_increment,
	documentId int,
	projectId int,
	taskId int,
	primary key(id)
);

create table userprojecthistory (
	id int not null auto_increment,
	userId varchar(50),
	projectId int not null,
	primary key(id)
);

create table userdocumenthistory (
	id int not null auto_increment,
	userId varchar(50),
	documentId int,
	primary key(id)
);

create table usersearchhistory (
	id int not null auto_increment,
	userId varchar(50),
	keyword varchar(250),
	primary key(id)
);


create table documenttext (
	id int not null auto_increment,
	documentId int,
	text mediumtext,
	primary key(id)
);

create table documentcategory (
	id int not null auto_increment,
	documentId int,
	categoryTitle varchar(250),
	startPage int,
	endPage int,
	startPosition int,
	endPosition int,
	primary key(id)
);








