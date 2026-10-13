@echo off
setlocal
rem Bootstrap launcher for the pinned Gradle distribution (8.14.6).
rem For a canonical Gradle Wrapper, regenerate with: gradle wrapper --gradle-version 8.14.6
set "GRADLE_VERSION=8.14.6"
set "GRADLE_HOME=%USERPROFILE%\.gradle\wrapper\dists\gradle-%GRADLE_VERSION%-bin\bootstrap\gradle-%GRADLE_VERSION%"
if not exist "%GRADLE_HOME%\bin\gradle.bat" (
  echo Downloading Gradle %GRADLE_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $version='%GRADLE_VERSION%'; $root=Join-Path $env:USERPROFILE '.gradle\wrapper\dists\gradle-'+$version+'-bin\bootstrap'; New-Item -ItemType Directory -Force -Path $root | Out-Null; $zip=Join-Path $root ('gradle-'+$version+'-bin.zip'); Invoke-WebRequest -Uri ('https://services.gradle.org/distributions/gradle-'+$version+'-bin.zip') -OutFile $zip; $expected='7988ed071b2a07900e2ec715fca15c6ab72bce6db433af301fa7fa7e9407bd24'; $actual=(Get-FileHash $zip -Algorithm SHA256).Hash.ToLower(); if ($actual -ne $expected) { throw 'Gradle distribution checksum mismatch'; }; Expand-Archive -Path $zip -DestinationPath $root -Force; Remove-Item $zip -Force"
  if errorlevel 1 exit /b 1
)
call "%GRADLE_HOME%\bin\gradle.bat" %*
exit /b %ERRORLEVEL%
