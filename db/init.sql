CREATE DATABASE if not exists salary_expectansee;
USE salary_expectansee;

CREATE TABLE admins (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    hourly_rate DOUBLE NOT NULL
);

CREATE TABLE work_hours (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    hours_worked DOUBLE NOT NULL,
    FOREIGN KEY (username) REFERENCES employees(username)
);

INSERT INTO admins (username, password) VALUES ('admin', 'adminpass');
INSERT INTO employees (username, password, hourly_rate) VALUES ('employee1', 'emp123', 15.0);

