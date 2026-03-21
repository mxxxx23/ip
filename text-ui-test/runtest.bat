@ECHO OFF
setlocal

set SCRIPT_DIR=%~dp0
set PROJECT_DIR=%SCRIPT_DIR%..
set BIN_DIR=%PROJECT_DIR%\bin\text-ui-test
set RUN_DIR=%SCRIPT_DIR%run-env
set ACTUAL_FILE=%SCRIPT_DIR%ACTUAL.TXT

if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"
if not exist "%RUN_DIR%\data" mkdir "%RUN_DIR%\data"
if exist "%ACTUAL_FILE%" del "%ACTUAL_FILE%"
if exist "%RUN_DIR%\data\sago.txt" del "%RUN_DIR%\data\sago.txt"

dir /s /b "%PROJECT_DIR%\src\main\java\*.java" | findstr /V /I "\\Main.java \\MainWindow.java \\DialogBox.java \\Launcher.java" > "%SCRIPT_DIR%sources.txt"
javac -Xlint:none -d "%BIN_DIR%" @"%SCRIPT_DIR%sources.txt"
IF ERRORLEVEL 1 (
    del "%SCRIPT_DIR%sources.txt"
    echo ********** BUILD FAILURE **********
    exit /b 1
)
del "%SCRIPT_DIR%sources.txt"

pushd "%RUN_DIR%"
java -cp "%BIN_DIR%" sago.Sago < "%SCRIPT_DIR%input.txt" > "%ACTUAL_FILE%"
popd

FC "%ACTUAL_FILE%" "%SCRIPT_DIR%EXPECTED.TXT"
