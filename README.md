# Setup Instruction

## To Run

Configure env variables

1. MONGO_URI (url connection)


During application start up it will create a default admin user

    
```
    email: admin@example.com
    password: Abcd@1234
```

## To Run as bundle with docker compose

1. Make a parent folder e.g. (insurance-policy-system)
2. Copy the file from -[docker-compose.yml](./dockerCompose/docker-compose.yml) to Parent
3. Clone the ui-insurance in the parent folder
4. Final Project structure
```
insurance-policy-system/
├── service-insurance/
├── ui-insurance/
└── docker-compose.yml
```
6. Command line in parent folder run as "docker-compose up --build"
7. To access UI = http://localhost:5173/
8. To Access BE = http://localhost:8080
9. To access Mongo = http://localhost:27017


## To Setup Policy can use the postman (Only Admin Role)

During initiation of application, it will create 3 policy by default HOME, LIFE and AUTO.

For Additional we can make use of postman, since there is no UI.

1. Login with the credentials. Postman(1.1 Login)
2. Request for access Token. Postman(1.2 Access Token)
3. Create Insurance Policy. Postman (3.1.1 Create)

## API Testing

You can import and test this API using [Postman](https://www.postman.com/).

Download or import this Postman collection:

- [Postman Collection (JSON)](./postman/Boltech Insurance.postman_collection.json)
- Can access swagger too http://localhost:8080/swagger-ui/index.html#/

## List Of Features implemented

1. User Authentication
2. Policy Management
3. Quote Generation
4. Policy Purchase
5. User Profile
6. Dashboard Summary
7. Basic Testing (Not Detailed)
8. DockerFile
9. Docker Compose
10. Only simple document upload (only pdf files)
11. Basic Data Visualization

## Didn't have time for
1. Localization
2. Real-time updates
3. Deploy to cloud platform



## Assumptions
1. I assume all insurance in month term.
2. Insurance type is restricted to Auto, Life, Home
3. Whenever click the policy is active and first month payment done.
4. Only pdf file upload