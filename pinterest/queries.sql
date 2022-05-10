--insertion into person
insert into person values ('red99','lameasspass','Redwan Haque','redhaque234@gmail.com');
insert into person values ('gsaha567', 'badluck', 'Gourab Saha', 'gsaha7031@gmail.com');
insert into person values ('sahil234', 'amiodada', 'Ajmain Sahil', 'sahil001@yahoo.com');
insert into person values ('Saif098', 'hogorvai', 'Saifullah Talukdar', 'talukdar.saif@gmail.com');
insert into person values ('mahir2', 'asdfghjkl', 'Mahir Sezan', 'mahir.sezan@yahoo.com');
insert into person values ('mahatir69', 'qwerty12', 'Mahatir Mohamman', 'mmahatir69@gmail.com');
insert into person values ('anjum243', 'zxcv1234', 'Tahmid Anjum', 'anjum.lola@gmail.com');

--trial of the above insertion
select * from person;

--insertion into pin
insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Photography\NicePhoto.jpg','Nice Photo', current_date ,'This is a nice photo','red99');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Science\DNA.jpg','DNA', current_date ,'Double Helix DNA','gsaha567');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Arts\WeirdArt.jpg','Random Art', current_date ,'This is a weird photo','mahir2');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Planets\Solarsystem.jpg','Photo of SS', current_date ,'Solar System','mahatir69');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Technology\Foldable Phone.jpg','Foldable Phone', current_date ,'This is a foldable phone','sahil234');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\Science\Molecule.bmp','Molecule', current_date ,'This is a foldable phone','sahil234');

insert into pin(image_src,title,date_created,description,creator_alias)
values('E:\2-2\Database Sessional\Project\Final_Project\Images\TScience\ChemLab.jpg','Chem Lab', current_date ,'This is a foldable phone','sahil234');


--trial of the above insertion
select * from pin;


--insertion into topic
insert into topic values ('Science');
insert into topic values ('Technology');
insert into topic values ('Arts');
insert into topic values ('Planets');
insert into topic values ('Photography');
insert into topic values ('Nature');
insert into topic values ('Buildings');

--trial of the above insertion
select * from topic;

--insertion into board
insert into board(creator_alias, board_topic, board_title) values('mahir2','Science', 'Biology');
insert into board(creator_alias, board_topic, board_title) values('mahatir69','Technology', 'Phones');
insert into board(creator_alias, board_topic, board_title) values('red99','Photography', 'Weird Photos');
insert into board(creator_alias, board_topic, board_title) values('anjum243','Planets', 'Solar System');
insert into board(creator_alias, board_topic, board_title) values('Saif098','Buildings', 'Skyscraper');
insert into board(creator_alias, board_topic, board_title) values('Saif098','Arts', 'Madness');
insert into board(creator_alias, board_topic, board_title) values('sahil234','Science', 'Chemistry');
insert into board(creator_alias, board_topic, board_title) values('mahir2','Science', 'Random Stuff');
insert into board(creator_alias, board_topic, board_title) values('mahir2','Arts', 'Wow');
insert into board(creator_alias, board_topic, board_title) values('mahir2','Technology', 'IoT');
insert into board(creator_alias, board_topic, board_title) values('mahir2','Buildings', 'Tall');

select *
from latest_board;


--trial of the above insertion
select * from board;

--insertion into board follow record
insert into board_follow_record values('mahir2',1010);
insert into board_follow_record values('mahatir69',1010);
insert into board_follow_record values('mahir2',1011);
insert into board_follow_record values('mahir2',1012);
insert into board_follow_record values('Saif098',1009);
insert into board_follow_record values('red99',1014);



--trial of the above insertion
select * from board_follow_record;



--board_follow_notification trigger
create or replace function board_follow_procedure() returns trigger as
	$$
	declare
	b_id int;
	c_alias varchar(30);
	not_text varchar(80);
	begin
	b_id := new.board_id;
	select creator_alias into c_alias from board where board.id=new.board_id;
	not_text := new.follower_alias || ' followed you board';
	insert into notification(notification_to, notification_from, notification_text,notification_data
	                         ) values(c_alias,new.follower_alias,not_text);
	return new;
	end;
	$$ language plpgsql;

drop trigger if exists bFollow_notice
on board_follow_record;
	create trigger bFollow_notice after insert on board_follow_record
		for each row
			execute procedure board_follow_procedure();


--notification query
select * from notification;


--insertion into user_follow_record
insert into user_follow_record values('anjum243','mahir2');
insert into user_follow_record values('mahir2','sahil234');
insert into user_follow_record values('anjum243','gsaha567');
insert into user_follow_record values('mahatir69','red99');
insert into user_follow_record values('red99','sahil234');
insert into user_follow_record values('anjum243','mahatir99');


--trial of the above insertion
select * from user_follow_record;

delete from user_follow_record where follower_alias='anjum243' and followed_alias='mahatir69';

--user follow notification record
create or replace function user_follow_procedure() returns trigger as
	$$
	declare
	not_text varchar(80);
	begin
	not_text := new.follower_alias || ' followed you board';
	insert into notification(notification_to, notification_from, notification_text) values(new.followed_alias,new.follower_alias,not_text);
	return new;
	end;
	$$ language plpgsql;

drop trigger if exists uFollow_notice
on user_follow_record;
	create trigger uFollow_notice after insert on user_follow_record
		for each row
			execute procedure user_follow_procedure();


--notification query
select * from notification;

select * from likes;

--like table
insert into likes values(10004,'mahatir69');
insert into likes values(10002,'mahatir69');
insert into likes values(10001,'red99');
insert into likes values(10004,'anjum243');
insert into likes values(10002,'sahil234');
insert into likes values(10003,'gsaha567');
insert into likes values(10007,'mahatir69');
insert into likes values(10006,'mahatir69');
insert into likes values(10007,'red99');
insert into likes values(10008,'anjum243');
insert into likes values(10008,'sahil234');
insert into likes values(10008,'gsaha567');

--trial of the above insertion
select * from likes;

--like trigger
create or replace function like_procedure() returns trigger as
	$$
	declare
	c_alias varchar(30);
	not_text varchar(80);
	begin
	select creator_alias into c_alias from pin where pin.id=new.pin_id;
	not_text := new.person_alias || ' likes you photo';
	insert into notification(notification_to, notification_from, notification_text) values(c_alias,new.person_alias,not_text);
	return new;
	end;
	$$ language plpgsql;

drop trigger if exists like_notice
on likes;
	create trigger like_notice after insert on likes
		for each row
			execute procedure like_procedure();


--notification query
select * from notification;


--comment table
insert into pin_comments(pin_id,person_alias,description) values(10002,'mahir2','I hate Biology');
insert into pin_comments(pin_id,person_alias,description) values(10002,'Saif098','I freaking love bio');
insert into pin_comments(pin_id,person_alias,description) values(10004,'red99','WTF is this?');
insert into pin_comments(pin_id,person_alias,description) values(10006,'sahil234','I want this phone for Hishu');
insert into pin_comments(pin_id,person_alias,description) values(10003,'gsaha567','This is my first comment');
insert into pin_comments(pin_id,person_alias,description) values(10007,'mahir2','I hate Biology');
insert into pin_comments(pin_id,person_alias,description) values(10007,'Saif098','I freaking love bio');
insert into pin_comments(pin_id,person_alias,description) values(10007,'red99','WTF is this?');
insert into pin_comments(pin_id,person_alias,description) values(10008,'sahil234','I want this phone for Hishu');
insert into pin_comments(pin_id,person_alias,description) values(10008,'gsaha567','This is my first comment');

--trial of the above insertion
select * from pin_comments;

--comment_trigger
	create or replace function comment_procedure() returns trigger as
	$$
	declare
	p_id int;
	c_alias varchar(30);
	not_text varchar(80);
	begin
	p_id := new.pin_id;
	select creator_alias into c_alias from pin where pin.id=p_id;
	not_text := new.person_alias || ' commented on you photo';
	insert into notification(notification_to, notification_from, notification_text) values(c_alias,new.person_alias,not_text);
	return new;
	end;
	$$ language plpgsql;

drop trigger if exists comment_notice on pin_comments;
	create trigger comment_notice after insert on pin_comments
		for each row
			execute procedure comment_procedure();

--notification query
select * from notification;

--reply insertion
insert into reply values(20010,'gsaha567','Me too');
insert into reply values(20010,'gsaha567','And me three');
insert into reply values(20011,'sahil234','What an idiot');
insert into reply values(20012,'mahir2','Nice comment');
insert into reply values(20013,'anjum243','Nice comment');
insert into reply values(20014,'mahir2','Nice comment');
insert into reply values(20010,'gsaha567','Me 4');
insert into reply values(20015,'Saif098','Go to hell');
insert into reply values(20015,'gsaha567','Ok dude');
insert into reply values(20015,'sahil234','Me too');
insert into reply values(20017,'Saif098','Seriously bro!!??');

--trial of the above insertion
select * from reply;

--reply trigger
create or replace function reply_procedure() returns trigger as
	$$
	declare
	c_id int;
	c_alias varchar(30);
	not_text varchar(80);
	begin
	c_id := new.comment_id;
	select person_alias into c_alias from pin_comments where pin_comments.id=c_id;
	not_text := new.person_alias || ' replied to your comment';
	insert into notification(notification_to, notification_from, notification_text) values(c_alias,new.person_alias,not_text);
	return new;
	end;
	$$ language plpgsql;

drop trigger if exists reply_notice on reply;
	create trigger reply_notice after insert on reply
		for each row
			execute procedure reply_procedure();


--board pin relationship record
insert into board_pin_relationship_record values (1009,10002);
insert into board_pin_relationship_record values (1010,10006);
insert into board_pin_relationship_record values (1011,10001);
insert into board_pin_relationship_record values (1012,10004);
insert into board_pin_relationship_record values (1015,10003);
insert into board_pin_relationship_record values (1016,10007);
insert into board_pin_relationship_record values (1016,10008);

--trial of the above insertion
select * from board_pin_relationship_record;

--complex queries
select image_src from pin
where id in (select pin_id from board_pin_relationship_record
						 where board_id in (select board.id from board
						 where board_topic='Science' ))
order by date_created desc;

--topic search
select pin_id from board_pin_relationship_record where board_id in (select board.id from board where board_topic='Science' );

--update pin set image_src = 'E:\2-2\Database Sessional\Project\Final_Project\Images\Science\ChemLab.jpg' where image_src='E:\2-2\Database Sessional\Project\Final_Project\Images\TScience\ChemLab.jpg';

--retrieving user's boards
select * from board where creator_alias = 'Saif098';

--insertion into topic follow record
insert into topic_follow_record values ('Saif098','Science');
insert into topic_follow_record values ('Saif098','Planets');
insert into topic_follow_record values ('Saif098','Arts');
insert into topic_follow_record values ('ags27','Science');
insert into topic_follow_record values ('mahir2','Science');
insert into topic_follow_record values ('mahir2','Planets');
insert into topic_follow_record values ('mahir2','Photography');

--trial of above insertion
select * from topic_follow_record;

--topic follow screen queries
select * from topic;

select topic_name from topic_follow_record where follower_alias= 'Saif098';

select board_title from board where id in (select board_id from board_follow_record where follower_alias= 'mahir2');

select followed_alias from user_follow_record where follower_alias='anjum243';

select person_alias from likes where pin_id = 10001;

select * from pin_comments where pin_id = 10007;

select person_alias,description from reply where comment_id=20015;

alter table notification add column notification_data varchar(20);

select * from notification;

create table latest_board
(
  id int,
  board_id int
);

create or replace function last_board() returns trigger as
  $$
  declare
    a int;
  begin
		a:=new.id;
		update latest_board
    set board_id = a
    where id = 1;
	return new;
	end;

  $$ language plpgsql;

drop trigger if exists last_board_trigger on board;
create trigger last_board_trigger after insert on board
		for each row
			execute procedure last_board();

insert into latest_board values (1,1);

select * from latest_board;

select * from board;

select * from pin;

drop function if exists is_user_followed(_Follower_alias varchar, _Followed_alias varchar);
create or replace function is_user_followed(_Follower_alias varchar, _Followed_alias varchar)
returns int as $$
  declare
    count int;
  begin
		select count(*) into count from user_follow_record t where t.follower_alias= _Follower_alias and t.followed_alias= _Followed_alias;
		--select count(*) into count from dummy_table;
		return count;


	end;
  $$ language plpgsql;

select is_user_followed('mahir2','anjum243');

create or replace function is_board_followed(boardID int, alias varchar)
returns int as $$
  declare
    count int;
  begin
		select count(*) into count from board_follow_record where board_follow_record.board_id= boardID and board_follow_record.follower_alias= alias;
		--select count(*) into count from dummy_table;
		return count;


	end;
  $$ language plpgsql;

select is_board_followed(1010, 'mahir2');

create or replace function is_topic_followed(t_name varchar, alias varchar)
returns int as $$
  declare
    count int;
  begin
		select count(*) into count from topic_follow_record where topic_follow_record.topic_name= t_name and topic_follow_record.follower_alias= alias;
		--select count(*) into count from dummy_table;
		return count;


	end;
  $$ language plpgsql;

select is_topic_followed('Science','mahir2');

select * from notification;