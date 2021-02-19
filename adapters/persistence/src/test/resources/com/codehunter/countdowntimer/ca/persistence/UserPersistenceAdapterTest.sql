insert into ROLE (name) values ('USER');
insert into ROLE (name) values ('MODERATOR');
insert into ROLE (name) values ('ADMINISTRATOR');

insert into USER (username, email, password) values ('admin', 'admin@admin.com', 'admin');

insert into USER_ROLE (user_id, role_id) values (1,3);