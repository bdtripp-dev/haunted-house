# Haunted House
I built a text‑based adventure game where the player explores a haunted house and tries to find a way out.  

---

## Why This Project Matters

Originally a school assignment, *Haunted House* evolved into a project where I focused on building a clean, modular game engine grounded in real software‑engineering principles. Even though it’s a simple text-based adventure game on the surface, the project pushed me to think about architecture, state management, and scalability. Every part of the game — rooms, items, commands, player stats — is modeled as its own object, making the system easy to maintain and extend.

I also wanted to explore how a traditional CLI game could be adapted for the web. The backend exposes a small REST API that accepts the same text commands as the command‑line version, and the browser UI acts as a lightweight terminal that sends commands to the engine. This separation of concerns made it possible to reuse the same Java engine for both interfaces.

The project gave me hands‑on experience with Spring Boot, REST design, Dockerized development, and writing clean, maintainable Java code with complete Javadoc documentation. It’s a small game, but it reflects real engineering decisions and a focus on clarity, structure, and maintainability.

---

## Live Demo

Play the game at https://haunted-house.bdtripp.com/

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

View Javadoc (API documentation) for the project at: https://bdtripp-dev.github.io/haunted-house/javadoc/

---


