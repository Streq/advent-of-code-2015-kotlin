#!/bin/bash

SOURCE="src/main/kotlin/dayN/DayN.kt"
DEST="src/main/kotlin/day$1/Day$1.kt"
mkdir -p "$(dirname $DEST)"
cp "$SOURCE" "$DEST"
sed -i "s/DayN/Day$1/g" "$DEST"
sed -i "s/dayN/day$1/g" "$DEST"

mkdir -p "src/main/resources/day$1/"
mkdir -p "src/test/resources/day$1/"

touch "src/test/resources/day$1/example.txt"

SOURCE="src/test/kotlin/dayN/DayNTest.kt"
DEST="src/test/kotlin/day$1/Day$1Test.kt"
mkdir -p "$(dirname $DEST)"
cp "$SOURCE" "$DEST"
sed -i "s/DayN/Day$1/g" "$DEST"
sed -i "s/dayN/day$1/g" "$DEST"
