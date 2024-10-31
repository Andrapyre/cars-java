INSERT INTO customers(first_name,last_name,email,city,country)
VALUES('John', 'Smith', 'john@gmail.com', 'Los Angeles', 'USA');

INSERT INTO cars(id,customer_id,model,make,produced_in)
VALUES('5d37c2cc-45dd-40a3-8c0e-1fb5dd92f131',1,'Jetta','VW',2020);

INSERT INTO api_keys(id,customer_id,api_key)
VALUES(1,1,'7E6C353555C640FD80643925FB54E046');