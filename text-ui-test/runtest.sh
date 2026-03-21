#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
BIN_DIR="$PROJECT_DIR/bin/text-ui-test"
RUN_DIR="$SCRIPT_DIR/run-env"
ACTUAL_FILE="$SCRIPT_DIR/ACTUAL.TXT"

mkdir -p "$BIN_DIR" "$RUN_DIR/data"
rm -f "$ACTUAL_FILE" "$RUN_DIR/data/sago.txt"

if ! find "$PROJECT_DIR/src/main/java" -name '*.java' \
    ! -name 'Main.java' \
    ! -name 'MainWindow.java' \
    ! -name 'DialogBox.java' \
    ! -name 'Launcher.java' \
    -print0 | xargs -0 javac -Xlint:none -d "$BIN_DIR"
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

(cd "$RUN_DIR" && java -cp "$BIN_DIR" sago.Sago < "$SCRIPT_DIR/input.txt" > "$ACTUAL_FILE")

if diff "$ACTUAL_FILE" "$SCRIPT_DIR/EXPECTED.TXT"
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
