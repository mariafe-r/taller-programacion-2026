#!/usr/bin/env bash
# Compila y ejecuta las pruebas "sin dependencias" (SimpleLibraryServiceTest)
# sin necesidad de Gradle, JUnit ni conexión a internet.
# Solo requiere un JDK 17+ instalado.
#
# Uso:
#   bash scripts/run-simple-tests.sh
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_DIR="$ROOT_DIR/out/simple-tests"

echo "==> Limpiando compilación previa..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "==> Compilando código fuente principal + prueba simple..."
find "$ROOT_DIR/src/main/java" -name "*.java" > "$ROOT_DIR/out/sources-main.txt"
javac -d "$OUT_DIR" @"$ROOT_DIR/out/sources-main.txt"
javac -cp "$OUT_DIR" -d "$OUT_DIR" \
  "$ROOT_DIR/src/test/java/com/umb/taller/service/SimpleLibraryServiceTest.java"

echo "==> Ejecutando SimpleLibraryServiceTest..."
echo "------------------------------------------------------------"
java -cp "$OUT_DIR" com.umb.taller.service.SimpleLibraryServiceTest
