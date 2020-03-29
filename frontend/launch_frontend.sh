docker stop easify-frontend
docker rm easify-frontend
docker image rm easify-frontend:latest
docker build -t easify-frontend .
docker run -it -p 8080:8080 --name easify-frontend --rm easify-frontend
