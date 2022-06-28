IMAGE_NAME=GPGP-IMAGE
CONTAINER_NAME=GPGP-CONTAINER

echo "=> Remove previous container..."
docker stop ${CONTAINER_NAME}
docker rm ${CONTAINER_NAME}
docker rmi ${IMAGE_NAME}

docker build \
-t $IMAGE_NAME \
.

docker run \
-d \
-p 8080:8080 \
--name $CONTAINER_NAME \
--build-arg SPRING_PROFILE=local \
IMAGE_NAME