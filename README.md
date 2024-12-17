# Guessers Server

Client code: https://github.com/Mert18/guessers-client

## Deploying for development
- Ensure you have docker and docker compose installed. (`docker version` and `docker compose version` commands return meaningful responses)
- If you want to develop for this repository (guessers-server), run `docker compose up -d --scale guessers-app=0`. This will not deploy backend app (only postgres and keycloak), so you will be able to run the backend locally and develop.
- If you want to develop for frontend repository (guessers-client), run `docker-compose up -d`. This will deploy everything (postgres, keycloak and backend app) so you can connect from frontend directly.
- Ensure postgres keycloak and (optionally) backend containers is running: `docker ps`.
- :clap:
