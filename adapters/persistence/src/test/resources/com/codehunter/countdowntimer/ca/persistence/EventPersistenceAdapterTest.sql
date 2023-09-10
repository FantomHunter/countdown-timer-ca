--insert into user (id, name) values('first-user-id', 'hunter');
--insert into event (id, name, time, status, user_id) values (1, 'event from sql unit test', '2020-10-18 16:00:00.0', 'CREATED', 'first-user-id');
insert into event (name, time, status, user_id) values ('event from sql unit test', '2020-10-18 16:00:00.0Z', 'CREATED', null);
