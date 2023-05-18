DROP TABLE IF EXISTS FLIGHT;

CREATE TABLE FLIGHT (
  id LONG AUTO_INCREMENT PRIMARY KEY NOT NULL,
  flight_number VARCHAR(250) NOT NULL,
  origin VARCHAR(250) NOT NULL,
  destination VARCHAR(250) NOT NULL,
  departure_time Time NOT NULL,
  arrival_time Time NOT NULL,
  price DOUBLE PRECISION NOT NULL
);