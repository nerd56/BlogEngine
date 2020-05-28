insert into posts (id, is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) values
    (1, 1, 'ACCEPTED', 1, 1, '2020-04-16', 'abc123', 'post1_text', 300),
    (2, 1, 'ACCEPTED', 1, 2, '2021-04-12', 'abc123', 'post2_text', 301),
    (3, 0, 'DECLINED', 1, 3, '2020-04-12', 'abc123', 'post3_text', 0),
    (4, 1, 'NEW', 1, 1, '2020-03-11', 'abc123', 'post4_text', 30),
    (5, 1, 'ACCEPTED', 1, 1, '2020-04-01', 'abc123', 'post5_text', 55),
    (6, 1, 'ACCEPTED', 1, 2, '2020-04-17', 'abc123', 'post6_text', 64),
    (7, 1, 'ACCEPTED', 1, 2, '2020-04-16', 'abc123', 'post7_text', 9000),
    (8, 1, 'ACCEPTED', 1, 3, '2020-04-04', 'abc123', 'post8_text', 123);

insert into post_votes (id, post_id, time, user_id, value) values
    (1, 2, '2020-04-11', 1, 1),
    (2, 2, '2020-04-11', 1, 1),
    (3, 3, '2020-04-11', 1, 1),
    (4, 1, '2020-04-11', 1, 1),
    (5, 1, '2020-04-11', 1, -1),
    (6, 1, '2020-04-11', 1, 1),
    (7, 4, '2020-04-11', 1, -1),
    (8, 5, '2020-04-11', 1, 1),
    (9, 5, '2020-04-11', 1, 1),
    (10, 5, '2020-04-11', 1, 1),
    (11, 6, '2020-04-11', 1, 1),
    (12, 1, '2020-04-11', 1, -1);

insert into post_comments (id, post_id, time, user_id, text) values
    (1, 1,'2020-04-11', 1, "comment1"),
    (2, 1,'2020-04-11', 1, "comment2"),
    (3, 2,'2020-04-11', 1, "comment3"),
    (4, 5,'2020-04-11', 1, "comment4"),
    (5, 5,'2020-04-11', 1, "comment5"),
    (6, 3,'2020-04-11', 1, "comment6"),
    (7, 3,'2020-04-11', 1, "comment7");

insert into users (id, email, is_moderator, name, password, reg_time) values
    (1, 'gmail1@gmail.com', 0, "user1", "123", '2020-04-09'),
    (2, 'gmail2@gmail.com', 0, "user2", "1234", '2020-04-10'),
    (3, 'gmail3@gmail.com', 0, "user3", "12345", '2020-04-11');

insert into tags (id, name) values
    (1, "tag1"),
    (2, "tag2"),
    (3, "tag3");

insert into tag2post (id, tag_id, post_id) values
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1),
    (4, 1, 6),
    (5, 1, 7),
    (6, 2, 8),
    (7, 2, 4),
    (8, 2, 2),
    (9, 2, 8),
    (10, 3, 6),
    (11, 2, 7);
