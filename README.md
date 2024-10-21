# Cars (for Java)

## Description
This project is to give a sample application that explores Spring Boot by creating a set of REST APIs to manage cars and the customers that own them.

### Getting Started
1. Clone the repo
2. Ensure that you are using jdk 17 in your terminal
3. Run the following:
   ```sh
   docker compose up
   ```
4. In a separate terminal, run the following:
    ```sh
    gradle bootRun
    ```
5. The server should now be started on `localhost:8080`.
6. Import the postman collection from `./postman-collection.json` and enjoy!

### Testing

1. Start the service with database, as described in the previous section.
2. Ensure that you have installed all necessary dependencies with ```npm install```.
3. Then run ```npm run test```