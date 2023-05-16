#Function to create queues and dlq-queues


#./gradlew clean build

docker-compose rm -y

sudo rm -rf volume

docker-compose build
docker-compose up