create database ibike;

use ibike;

--status 0未启用，1启用但未解锁，2开锁使用，3，维修
create table bike(
    bid bigint primary  key  auto_increment,
    status int default 0,
    qrcode varchar(100) default '',
    latitude double ,
    longitude double
);