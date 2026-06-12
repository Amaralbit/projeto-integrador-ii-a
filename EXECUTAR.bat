@echo off
REM ====================================================================
REM  Agenda Telefonica - CRUD em Java + MySQL
REM  Dois cliques neste arquivo compilam e executam a aplicacao.
REM ====================================================================
chcp 65001 >nul
setlocal enabledelayedexpansion
cd /d "%~dp0"

REM Caminho do Java (JDK) que vem dentro do IntelliJ IDEA.
set "JBR=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\jbr\bin"

if not exist "%JBR%\java.exe" (
    echo [ERRO] Nao encontrei o Java do IntelliJ em:
    echo        %JBR%
    echo Abra este arquivo .bat e ajuste a variavel JBR com o caminho correto.
    pause
    exit /b 1
)

REM Classpath: classes compiladas + driver do MySQL.
set "CP=out;lib\mysql-connector-j-8.4.0.jar"

echo Compilando o projeto...
if not exist out mkdir out
REM Monta a lista de arquivos .java (cada um entre aspas) numa variavel.
set "SOURCES="
for /r "src" %%f in (*.java) do set "SOURCES=!SOURCES! "%%f""
"%JBR%\javac.exe" -encoding UTF-8 -d out !SOURCES!

if errorlevel 1 (
    echo.
    echo [ERRO] Falha na compilacao. Veja as mensagens acima.
    pause
    exit /b 1
)

echo.
echo ====================================================================
echo  Iniciando a aplicacao... (digite as opcoes do menu e tecle ENTER)
echo ====================================================================
echo.
"%JBR%\java.exe" -Dfile.encoding=UTF-8 -cp "%CP%" com.agenda.AgendaTeste

echo.
echo ====================================================================
echo  Aplicacao encerrada. Pressione qualquer tecla para fechar.
echo ====================================================================
pause >nul
