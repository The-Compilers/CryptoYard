# CryptoYard

A system that helps to keep track of a crypto-investment wallet and to configure automated trading bots.

## Purpose

The main purpose of the project is to:

* Learn technologies relevant in real-life projects.
* Build a developer's portfolio
* Have fun and social interaction

While the system can be used to earn money, monetary profit is not the main goal. The project is considered a success if
the programmers have learned during the process and have grown as system developers, even when no money is earned. No
developer takes responsibility for gain or loss of money for the system users!

## Getting started

Here are the instructions for running the system.

### Deployment on a server

The system is described in the `docker-compose.yml` file, therefore, to run it:

* Describe the necessary environment variables in an environment file (`.env`). 
  See `env.template` file as a reference on the necessary variables.
* Run the system by executing the following command inside the root directory of the
  project: `docker compose --env-file .env up --build --detach` (replace the `.env` with the name of your environment file)

### Local development

During development, you can run the parts of the system in the _usual way_:

* Run a MySQL database either in a Docker container or as a standalone server. If you want to run the MySQL database inside a container:
    `docker run --name cy_db_dev -p 3306:3306 --env-file your-env-file.env  mysql:8.0.29-oracle`
* Describe access to MySQL (and other necessary environment variables) in an environment file (`.env`).
  See `env.template` file as a reference on the necessary variables.
* Run the backend as a Spring Boot project: either directly from your IDE or `mvn spring-boot:run`
* Install the necessary frontend libraries: `npm install` inside the `frontend` directory
* Run frontend as a regular React application: `npm start`

## Architecture

The project consists of a backend written with Spring Boot and frontend - a ReactJS application. Nginx proxy server is
used.

Backend application consists of independent services which communicate with each other using messages. Currently, only
messages between services inside a single application can be exchanged, but the architecture is prepared for
inter-process-communication between several microservices. Apache Kafka will be used for message transport.

![architecture diagram](./documentation/architecture-diagram-v1.0.jpg)

[//]: # (TODO: Diagram for messaging architecture)

## Workflow

In this project there are no specific roles among the members. You can work with something you find fun or interesting
and helps towards the milestone.

Here's the general workflow:

1. Find an issue you want to work with from the listing. If you want to work with something that is currently not
   listed, you can create an issue yourself specifying what you want to work with.
2. When you have your issue, create a branch from the dev branch named _issue_number-something-describing-the-issue_.
   E.g. _43-add-workflow-to-readme_.
3. Work with the issue in your newly created branch and have fun with it.
4. When you have finished your issue, send a pull request to pull your branch back into the dev branch. Now someone else
   can review what you've worked with and approve it. (P.s. if it takes too long for someone else to aprove it, you can
   merge it yourself into the dev branch.)

**Note:** The main branch should always be functional and operational.

## Design guideline

Check out the [design guideline](documentation/design-guidelines) for frontend user interface.

## Story point rules
When estimating story points for issues, one can use the following methodology:
* I know how to do it (have done something similar before) and it can be done in:
  * minutes => 1 point
  * 1 hour => 2 points
  * 2-4 hours => 4 points
  * a day => 8 points
  * 1-2 days => 16 points
* I have not done it before, but I have a fair idea how it could be done, and I think it can be done in:
  * 1 hour => 4 points
  * 2-4 hours => 8 points
  * a day => 16 points
  * 1-2 days => 32 points
* I have no idea how to do it, but I know how to start the research, and it looks like something that can be done in:
  * 1-4 hours: 12 points 
  * a day: 24 points
  * 1-2 days: 40 points
* I have no clue how to attack the problem, and it looks like something that takes:
  * hours: 24 points 
  * days: 48 points

Anything that feels larger than 2 days should be broken down into several issues!
