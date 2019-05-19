CREATE TABLE user_permission
(
  id SERIAL PRIMARY KEY NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  permission_id VARCHAR(255) NOT NULL,
  permission VARCHAR(255) NOT NULL
);

CREATE TABLE myuser
(
  id VARCHAR(255) PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL
);

CREATE TABLE permission
(
  id VARCHAR(255) PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  permission VARCHAR(255) NOT NULL
);
