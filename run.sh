#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

echo "Building Wordie..."
mvn -q clean package -DskipTests

echo "Starting Wordie on http://localhost:8080"
echo "H2 Console at http://localhost:8080/h2-console"
java -jar target/wordie-1.0.0.jar
