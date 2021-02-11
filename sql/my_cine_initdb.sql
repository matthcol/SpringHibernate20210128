-- connect with user root on none (database) 
-- mysql -u root (xampp)
-- sudo mysql -u root (linux without password)
-- mysql -u root -p (linux with password)

-- create database
create database dbmovie CHARACTER SET utf8;

-- create user with all privileges to database
create user 'movie'@'localhost' identified by 'password';
grant all privileges on dbmovie.* to 'movie'@'localhost';
flush privileges;

-- connect to database
use dbmovie;
