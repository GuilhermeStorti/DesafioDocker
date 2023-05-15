#Function to create queues and dlq-queues


./gradlew clean build

docker-compose rm -y
docker-compose build
docker-compose up