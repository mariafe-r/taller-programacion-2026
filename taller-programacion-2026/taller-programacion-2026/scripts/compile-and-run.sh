#!/usr/bin/env bash
# Compila y ejecuta la aplicación principal (Main) sin necesidad de Gradle
# ni de conexión a internet. Solo requiere un JDK 17+ instalado.
#
# Uso:
#   bash scripts/compile-and-run.sh
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_DIR="$ROOT_DIR/out/main"

echo "==> Limpiando compilación previa..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "==> Compilando código fuente principal..."
find "$ROOT_DIR/src/main/java" -name "*.java" > "$ROOT_DIR/out/sources.txt"
javac -d "$OUT_DIR" @"$ROOT_DIR/out/sources.txt"

echo "==> Ejecutando com.umb.taller.Main..."
echo "------------------------------------------------------------"
java -cp "$OUT_DIR" com.umb.taller.Main
