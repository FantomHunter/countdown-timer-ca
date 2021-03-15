insert into user (id, name) values('first-user-id', 'hunter');
insert into event (id, name, time, status, user_id) values (1, 'event 1 from sql unit test', '2020-10-18 16:00:00.0', 'CREATED', 'first-user-id');
insert into event (id, name, time, status, user_id) values (2, 'event 2 without user from sql unit test', '2020-10-18 16:00:00.0', 'CREATED', null);
