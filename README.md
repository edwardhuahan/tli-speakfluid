# UofT TLI Chatbot Builder Project
Team Speakfluid: Aurora, Edward, Kai, Minh, Sarah, Zoey 

# Setup
## Docker setup

![image](https://user-images.githubusercontent.com/17802344/194219645-6b9512c3-ebb6-4e05-9829-f4bfcfebbe1d.png)

1. Install [Docker](https://www.docker.com/)
2. Open Docker
3. Navigate to root folder of the project and run `docker compose up --build` in terminal to build the project
4. Ctrl + C to stop it
5. Run `docker compose up` whenever you want to run without building the project.

## MongoDB setup

Make sure to edit `backend/src/main/application.properties` to have the proper MongoDB URI. This would be shared among us privately.

## Node.js setup

`cd frontend`
`npm install`

