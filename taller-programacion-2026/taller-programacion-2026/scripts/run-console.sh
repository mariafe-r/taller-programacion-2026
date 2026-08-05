#!/usr/bin/env bash
# Compila y ejecuta el menú interactivo de consola (ConsoleMenu) sin
# necesidad de Gradle ni de conexión a internet. Solo requiere un JDK 17+.
#
# Uso:
#   bash scripts/run-console.sh
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_DIR="$ROOT_DIR/out/main"

echo "==> Compilando código fuente principal..."
mkdir -p "$OUT_DIR"
find "$ROOT_DIR/src/main/java" -name "*.java" > "$ROOT_DIR/out/sources.txt"
javac -d "$OUT_DIR" @"$ROOT_DIR/out/sources.txt"

echo "==> Iniciando menú interactivo de consola..."
echo "------------------------------------------------------------"
java -cp "$OUT_DIR" com.umb.taller.console.ConsoleMenu
