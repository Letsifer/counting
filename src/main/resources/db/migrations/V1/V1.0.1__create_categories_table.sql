/**
 * Author:  Евгений
 * Created: 20.08.2017
 */

create table counting.categories (
    id serial,
    title varchar not null,
    description varchar,
    parent_category_id int,

    constraint category_pk primary key(id),
    constraint parent_category_fk foreign key(parent_category_id)
    references counting.categories(id)
) with (oids = false);

comment on table counting.categories is 'Таблица категорий';

comment on column counting.categories.description is 'Описание категории';
