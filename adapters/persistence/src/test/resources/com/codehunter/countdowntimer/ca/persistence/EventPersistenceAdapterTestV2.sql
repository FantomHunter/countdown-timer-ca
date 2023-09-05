insert into app_user (id, name) values('first-user-id', 'hunter');
insert into event (name, time, status, user_id) values ('event 1 from sql unit test', '2020-10-18 16:00:00.0', 'CREATED', 'first-user-id');
insert into event (name, time, status, user_id) values ('event 2 without user from sql unit test', '2020-10-18 16:00:00.0', 'CREATED', null);
