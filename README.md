# Haunted House
I built a text‑based adventure game where the player explores a haunted house and tries to find a way out.  

---

## Why This Project Matters

Originally a school assignment, *Haunted House* evolved into a project where I focused on building a clean, modular game engine grounded in real software‑engineering principles. Even though it’s a simple text-based adventure game on the surface, the project pushed me to think about architecture, state management, and scalability. Every part of the game — rooms, items, commands, player stats — is modeled as its own object, making the system easy to maintain and extend.

I also wanted to explore how a traditional CLI game could be adapted for the web. The backend exposes a small REST API that accepts the same text commands as the command‑line version, and the browser UI acts as a lightweight terminal that sends commands to the engine. This separation of concerns made it possible to reuse the same Java engine for both interfaces.

The project gave me hands‑on experience with Spring Boot, REST design, Dockerized development, and writing clean, maintainable Java code with complete Javadoc documentation. It’s a small game, but it reflects real engineering decisions and a focus on clarity, structure, and maintainability.

---

## Live Demo

<a href="https://haunted-house.bdtripp.com/" target="_blank">Play Haunted House</a><br><br>
(Runs in your browser with no installation required.)

---

## Some Commands to Get You Started

`go`: move to another room. Type `go` + "space" + "a direction"
Hint: Directions are north, south, east, or west 

`help`: get info on how to play the game and a list of all the commands  

`look`: get a description of your location and directions that you are able to move 

`items`: print a list of items that you are carrying and descriptions of each item  

`stats`: print a list of the player's current stats  

`quit`: Use to quit the program  

---

## Tech Stack

- Languages:  
    - Front-end: HTML, CSS, JS
    - Back-end: Java
- Framework: Spring Boot
- DevOps / Workflow: Docker, Dev Container, GitHub Actions

---

## Documentation

<a href="https://bdtripp-dev.github.io/haunted-house/javadoc/" target="_blank">View Javadoc</a>

---

## Key Engineering Decisions

- **Designed a modular game engine** using object‑oriented principles where rooms, items, commands, and player state are modeled as independent components with clear responsibilities.

- **Implemented a command‑parsing system** that maps user input to executable game actions, making it easy to add new commands without modifying existing logic.

- **Separated engine logic from the UI layer**, enabling the same Java backend to power both the CLI version and the web-based terminal.

- **Built a REST API** that exposes game actions (`/start`, `/command`) and returns structured JSON responses for the browser client.

- **Used Spring Boot’s dependency injection** to wire together the controller, service layer, and game engine, keeping components loosely coupled and easy to test or replace.

- **Containerized the application with Docker** to ensure consistent local development and deployment environments.

- **Set up GitHub Actions CI/CD** to automatically build and deploy the project on each push.

- **Wrote complete Javadoc documentation** to make the engine understandable, maintainable, and easy to extend.

## What I Learned

- How to break down oversized classes and keep architecture clean as a project grows

- How to design a system using components with clearly defined responsibilities and separation of concerns

- How to expose backend logic through REST endpoints and structure JSON responses

- How to learn a new framework quickly by focusing on core concepts and applying them immediately to a real problem

- How to containerize a Java/Spring Boot app for consistent development and deployment

- How to write maintainable, well‑documented code using Javadoc
