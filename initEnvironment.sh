./gradlew clean build

docker-compose rm -y

sudo rm -rf volume

docker-compose build
docker-compose up