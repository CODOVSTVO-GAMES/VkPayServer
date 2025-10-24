# Windows
.\mvnw clean install -DskipTests=true

# Linux
sudo mvn clean install -DskipTests=true -e

docker-compose build
docker-compose up

sudo chmod +x start.sh
sudo sed -i -e 's/\r$//' start.sh
sudo ./start.sh