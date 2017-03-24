use mysql;

CREATE DATABASE earthquakes;

-- Grants for devusr@'%' with password 'ab1234'
grant all privileges on *.* to 'devusr'@'%' identified by password '*EB0C338055B4AE228BCD011BA413F77FE65915B2' with grant option;

flush privileges;