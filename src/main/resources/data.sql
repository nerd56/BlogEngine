insert into posts (id, is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) values
    (1, 1, 'ACCEPTED', 1, 1, '2020-04-12', 'abc123', 'shdgfaksdhgfa', 300),
    (2, 1, 'ACCEPTED', 1, 1, '2020-04-11', 'abc123', 'shdgfaksdhgfa', 300);

insert into post_votes (id, post_id, time, user_id, value) values
    (1, 2, '2020-04-11', 1, 1),
    (2, 1, '2020-04-11', 1, -1);

insert into post_comments (id, parent_id, post_id, time, user_id, text) values
    (1, 1, 1,'2020-04-11', 1, "what a shitty post. git gut n00b");

insert into users (id, email, is_moderator, name, password, reg_time) values
    (1, 'gmail@gmail.com', 0, "Jihad", "123", '2020-04-11');

insert into tags (id, name) values
    (1, "Jaba"),
    (2, "Trup Strausa");

insert into tag2post (id, tag_id, post_id) values
    (1, 1, 2),
    (2, 2, 1);
