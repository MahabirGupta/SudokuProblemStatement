# Sudoku Game

## Requirements
- Java 9 or above

How to Run (Mac) on terminal
go to project root and type

## Compile
javac -d . sudoku/*.java

## Run
java sudoku.Main

## Run Tests

For running tests:
go to project root and type
javac -cp ".:lib/*" -d . sudoku/*.java src/test/*.java
java -cp ".:lib/*" org.junit.runner.JUnitCore src.test.ValidatorParameterizedTest
java -cp ".:lib/*" org.junit.runner.JUnitCore src.test.GameEngineE2ETest

