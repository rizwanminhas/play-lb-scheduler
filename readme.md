## About

This is a demo app for a `play framework scheduler`. 
It runs 2 play applications behind an `nginx` load balancer proxy. 
It uses `mongodb` to store a shared lock.

#### How to Run

1. `chmod +x ./startup.sh`

2. `./startup.sh`

#### Gotchas

1. In `docker-compose.yml` make sure your `volumes` under `playapp` point to the correct folder.
