#Requires -Version 5.1

$ErrorActionPreference = "Stop"
$ProjectRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..\..")).Path
$TemporaryRoot = Join-Path ([System.IO.Path]::GetTempPath()) "rowtool-sonar-token-$([guid]::NewGuid())"
$PropertiesPath = Join-Path $TemporaryRoot "gradle.properties"

try {
    New-Item -ItemType Directory -Path $TemporaryRoot | Out-Null
    Set-Content -LiteralPath $PropertiesPath -Encoding utf8 -Value "systemProp.sonar.token=   "
    Import-Module (Join-Path $ProjectRoot "tools\SonarToken.psm1") -Force

    $Configured = Test-SonarTokenConfigured `
        -ProcessToken $null `
        -UserToken $null `
        -PropertyPaths @($PropertiesPath)

    if ($Configured) {
        throw "Whitespace-only systemProp.sonar.token was accepted."
    }
}
finally {
    if (Test-Path -LiteralPath $TemporaryRoot) {
        Remove-Item -LiteralPath $TemporaryRoot -Recurse -Force
    }
}
