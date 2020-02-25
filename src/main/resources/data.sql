insert into bm.author (id, name, surname, email) values (1, 'name1', 'surname1', 'surname1@test.com');
insert into bm.author (id, name, surname, email) values (2, 'name2', 'surname2', 'surname2@test.com');
insert into bm.author (id, name, surname, email) values (3, 'name3', 'surname3', 'surname3@test.com');
insert into bm.author (id, name, surname, email) values (4, 'name4', 'surname4', 'surname4@test.com');
insert into bm.author (id, name, surname, email) values (5, 'name5', 'surname5', 'surname5@test.com');

insert into bm.publication (id, isbn, title, description, release_date) values (1, 'isbn1', 'title1', 'desc1', '2016-07-01');
insert into bm.publication (id, isbn, title, description, release_date) values (2, 'isbn2', 'title2', 'desc2', '2014-05-01');
insert into bm.publication (id, isbn, title, description, release_date) values (3, 'isbn3', 'title3', 'desc3', '2013-02-01');
insert into bm.publication (id, isbn, title, description, release_date) values (4, 'isbn4', 'title4', 'desc4', '2011-08-01');
insert into bm.publication (id, isbn, title, description, release_date) values (5, 'isbn5', 'title5', 'desc5', '2016-06-01');

insert into bm.publication_author(publication_id, author_id) values (1, 1);
insert into bm.publication_author(publication_id, author_id) values (1, 2);
insert into bm.publication_author(publication_id, author_id) values (1, 3);
insert into bm.publication_author(publication_id, author_id) values (2, 4);
insert into bm.publication_author(publication_id, author_id) values (2, 5);
insert into bm.publication_author(publication_id, author_id) values (3, 1);
insert into bm.publication_author(publication_id, author_id) values (3, 2);
insert into bm.publication_author(publication_id, author_id) values (4, 1);
insert into bm.publication_author(publication_id, author_id) values (5, 4);