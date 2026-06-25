#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

echo "Building JAR locally..."
mvn package -DskipTests -P!dev -q

echo "Building Docker image for deployment..."
TMPDIR=$(mktemp -d)
cp target/wordie-1.0.0.jar "$TMPDIR/"
cp docker/Dockerfile.prod "$TMPDIR/Dockerfile"
docker build -t wordie "$TMPDIR"
rm -rf "$TMPDIR"

echo "Done. Image 'wordie' built. Push to your registry with:"
echo "  docker tag wordie <registry>/wordie:latest && docker push <registry>/wordie:latest"
