create table person(
	alias varchar(30) unique,
	pass varchar(30),
	full_name varchar(40),
	email varchar(30)
);

create table topic
(
	name varchar(15) unique
);

create sequence board_seq increment by 1
	minvalue 1001;

create table board(
	id integer unique,
	creator_alias varchar(30) references person(alias),
	board_topic varchar(15) references topic(name),
	board_title varchar(30)
);

alter table board
alter column id set default nextval('board_seq');

create sequence pin_seq increment by 1
	minvalue 10001;

create table pin
(
	id integer  unique,
	image_src varchar(100),
	title varchar(15),
	date_created date,
	description varchar(50),
	creator_alias varchar(30)
);

alter table pin
alter column id set default nextval('pin_seq');

create table likes
(
	pin_id integer references pin(id),
	person_alias varchar(30) references person(alias)
);

create sequence comm_seq
	minvalue 20001;

create table pin_comments
(
	id integer unique,
	pin_id integer references pin(id),
	person_alias varchar(30) references person(alias),
	description varchar(100)
);

alter table pin_comments
alter column id set default nextval('comm_seq');

create table reply
(
	comment_id integer references pin_comments(id),
	person_alias varchar(30) references person(alias),
	description varchar(100)

);

create table board_pin_relationship_record
(
	board_id integer references board(id),
	pin_id integer references pin(id)
);


create table user_follow_record
(
	follower_alias varchar(30) references person(alias),
	followed_alias varchar(30) references person(alias)
);

create table board_follow_record
(
	follower_alias varchar(30) references person(alias),
	board_id integer references board(id)
);

create table board_collaboration_record
(
	collaborator_alias varchar(30) references person(alias),
	board_id integer references board(id)
);

create table topic_follow_record
(
	follower_alias varchar(30) references person(alias),
	topic_name varchar(30) references topic(name)
);

create sequence notification_seq
minvalue 30001;

create table notification
(
  id int unique ,
  notification_to varchar(30) references person(alias),
  notification_from varchar(30) references person(alias),
  notification_text text
);

alter table notification
alter column id set default nextval('notification_seq');