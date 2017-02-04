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
	`createdDate` datetime,
	`createdBy` varchar(45),
	`dueDate` datetime,
	`completedDate` datetime,
	`lastUpdateDate` datetime,
	primary key (`id`)
);


create table task(
	`id` int(11) not null auto_increment,
	`projectId` int(11) not null,
	`name` varchar(100) not null,
	`category` varchar(50),
	`description` varchar(200),
	`status` varchar(20),
	`createdDate` datetime,
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









