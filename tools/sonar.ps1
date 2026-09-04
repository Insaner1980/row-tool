#Requires -Version 5.1

[CmdletBinding()]
param(
    [switch]$PlanOnly,
    [ValidateRange(1, 86400)]
    [int]$GradleTimeoutSeconds = 3600
)

$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$OutputEncoding = [Console]::OutputEncoding

$ProjectRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
$PropertiesPath = Join-Path $ProjectRoot "sonar-project.properties"
$ReportsDirectory = Join-Path $ProjectRoot "reports"
$ReportPath = Join-Path $ReportsDirectory "sonar.txt"
Import-Module (Join-Path $PSScriptRoot "SonarToken.psm1") -Force -ErrorAction Stop

if (-not (Test-Path -LiteralPath $PropertiesPath -PathType Leaf)) {
    throw "sonar-project.properties ei löytynyt: $PropertiesPath"
}

$SonarProperties = @{}
foreach ($Line in Get-Content -LiteralPath $PropertiesPath -Encoding utf8) {
    $Trimmed = $Line.Trim()
    if ([string]::IsNullOrWhiteSpace($Trimmed) -or $Trimmed.StartsWith("#")) {
        continue
    }

    $Separator = $Trimmed.IndexOf("=")
    if ($Separator -gt 0) {
        $SonarProperties[$Trimmed.Substring(0, $Separator).Trim()] = $Trimmed.Substring($Separator + 1).Trim()
    }
}

$ProjectKey = [string]$SonarProperties["sonar.projectKey"]
$HostUrl = [string]$SonarProperties["sonar.host.url"]
if ([string]::IsNullOrWhiteSpace($ProjectKey)) {
    throw "sonar.projectKey puuttuu sonar-project.properties-tiedostosta."
}
if ([string]::IsNullOrWhiteSpace($HostUrl)) {
    $HostUrl = "https://sonarcloud.io"
}

if ($PlanOnly) {
    Write-Output @(
        "sonar"
        "  - Gradle :app:assembleDebug + sonar -> reports/sonar.txt"
        "  - requires SONAR_TOKEN or systemProp.sonar.token"
        "  - uploads analysis to SonarCloud"
        "  - project: $ProjectKey"
        "  - host: $HostUrl"
    )
    exit 0
}

New-Item -ItemType Directory -Force -Path $ReportsDirectory | Out-Null
Set-Content -LiteralPath $ReportPath -Encoding utf8 -Value @(
    "sonar"
    "Root: $ProjectRoot"
    "Project: $ProjectKey"
    "Command: .\gradlew.bat sonar --console=plain --no-configuration-cache"
    "Started: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
    ""
)

$UserSonarToken = [Environment]::GetEnvironmentVariable("SONAR_TOKEN", "User")
if ([string]::IsNullOrWhiteSpace($env:SONAR_TOKEN) -and -not [string]::IsNullOrWhiteSpace($UserSonarToken)) {
    $env:SONAR_TOKEN = $UserSonarToken
}

$TokenConfigured =
    Test-SonarTokenConfigured `
        -ProcessToken $env:SONAR_TOKEN `
        -UserToken $UserSonarToken `
        -PropertyPaths @(
        (Join-Path $env:USERPROFILE ".gradle\gradle.properties"),
        (Join-Path $ProjectRoot "gradle.properties")
        )

if (-not $TokenConfigured) {
    Add-Content -LiteralPath $ReportPath -Encoding utf8 -Value "ERROR: SONAR_TOKEN_MISSING"
    Get-Content -LiteralPath $ReportPath
    exit 2
}

. "$PSScriptRoot\Resolve-RowToolAndroidCheck.ps1"
$CheckRuntimeModule = Resolve-RowToolAndroidCheckFile -RelativePath "tools\CheckRuntime.psm1"
Import-Module $CheckRuntimeModule -Force -ErrorAction Stop
$Result = Invoke-ManagedProcess `
    -Executable (Join-Path $ProjectRoot "gradlew.bat") `
    -Arguments @("sonar", "--console=plain", "--no-configuration-cache") `
    -WorkingDirectory $ProjectRoot `
    -TimeoutSeconds $GradleTimeoutSeconds

foreach ($Text in @($Result.StandardOutput, $Result.StandardError)) {
    if (-not [string]::IsNullOrWhiteSpace($Text)) {
        Add-Content -LiteralPath $ReportPath -Encoding utf8 -Value $Text
        Write-Output $Text
    }
}

if ($Result.TimedOut) {
    Add-Content -LiteralPath $ReportPath -Encoding utf8 -Value "ERROR: SONAR_ANALYSIS_TIMEOUT ($GradleTimeoutSeconds s)"
    exit 2
}
if ($Result.ExitCode -ne 0) {
    Add-Content -LiteralPath $ReportPath -Encoding utf8 -Value "ERROR: SONAR_ANALYSIS_FAILED (exit $($Result.ExitCode))"
    exit 2
}

exit 0
