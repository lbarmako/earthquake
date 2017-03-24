CREATE TABLE earthquake_data (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  source_name CHAR(2) NOT NULL,
  earthquake_datetime DATETIME NOT NULL,
  latitude DOUBLE(10,6) NOT NULL,
  longitude DOUBLE(10,6) NOT NULL,
  magnitude DOUBLE(3,1) NOT NULL,
  depth DOUBLE(6,1) NOT NULL,
  region VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE earthquake_data
  ADD UNIQUE earthquake_data_natural_key(
  earthquake_datetime, latitude, longitude,magnitude, depth
);

CREATE TABLE dummy_tbl (
  id INT PRIMARY KEY,
  val VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;