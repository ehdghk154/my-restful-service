-- User 초기 데이터
insert into users(id, name, join_date, password, ssn) values(90001, 'Dante', now(), 'test1', '900101-1111111');
insert into users(id, name, join_date, password, ssn) values(90002, 'Sendi', now(), 'test2', '950111-2222222');
insert into users(id, name, join_date, password, ssn) values(90003, 'Volta', now(), 'test3', '930121-1234567');

-- Post 초기 데이터
insert into post(description, user_id) values('My First Post', 90001);
insert into post(description, user_id) values('My Second Post', 90001);