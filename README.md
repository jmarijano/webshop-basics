
# Setup
1. [Download and install Docker](https://docs.docker.com)
2. Open PowerShell and pull postgres image with command: docker pull postgres
3. Start postgres instance:  docker run --name webshop -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres
