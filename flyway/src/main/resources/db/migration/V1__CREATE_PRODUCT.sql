-- product 생성
create table product
(
    product_id    bigint auto_increment
        primary key,
    product_price bigint null,
    product_name  varchar(255) null
);

