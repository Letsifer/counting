/**
 * Author:  Евгений
 * Created: 20.08.2017
 */

create table counting.purchases (
    id serial,
    amount int not null,
    category_id int not null,
    description varchar,

    constraint purchase_pk primary key(id),
    constraint purchase_category_fk foreign key(category_id)
    references counting.categories(id)
) with (oids = false);

comment on table counting.purchases is 'Таблица покупок';

comment on column counting.purchases.category_id is 'Категория покупки';
comment on column counting.purchases.description is 'Описание покупки';