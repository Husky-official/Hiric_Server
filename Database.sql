CREATE TABLE users_table{
    user_id int auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50),
    email varchar(80) not null,
    account_type smallint not null default 1,
    user_role smallint not null default 1,
    password varchar(255) not null,
    telephone varchar(20),
    PRIMARY KEY (user_id),
    UNIQUE KEY (email),
    FOREIGN KEY (user_role) REFERENCES roles_table(role_id),
    FOREIGN KEY (account_type) REFERENCES account_types_table(account_type_id)
    };
CREATE TABLE roles_table{
    id int auto_increment,
    role varchar(30) not null,
    PRIMARY KEY (id)
    };
CREATE TABLE account_types_table{
    id int auto_increment,
    account_type varchar(30) not null,
    PRIMARY KEY (id)
    }