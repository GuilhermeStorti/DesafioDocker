./gradlew clean build

docker-compose rm -y

sudo rm -rf volume

docker-compose build
docker-compose up -d

gnome-terminal --tab -e "docker-compose logs -f -t  async" --tab -e "docker-compose logs -f -t  api" --tab -e "docker-compose logs -f -t  localstack" --tab -e "docker-compose logs -f -t  postgres"