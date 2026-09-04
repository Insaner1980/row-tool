#Requires -Version 5.1

$ErrorActionPreference = "Stop"
$ProjectRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..\..")).Path
$TemporaryRoot = Join-Path ([System.IO.Path]::GetTempPath()) "rowtool-android-check-$([guid]::NewGuid())"
$PreviousAndroidCheckRoot = $env:ANDROID_CHECK_ROOT

try {
    $ToolsRoot = Join-Path $TemporaryRoot "tools"
    $EntryPoint = Join-Path $ToolsRoot "InvokeProjectCheck.ps1"
    New-Item -ItemType Directory -Path $ToolsRoot | Out-Null
    Set-Content -LiteralPath $EntryPoint -Encoding utf8 -Value "# test entry point"

    $env:ANDROID_CHECK_ROOT = $TemporaryRoot
    . (Join-Path $ProjectRoot "tools\Resolve-RowToolAndroidCheck.ps1")

    $ResolvedEntryPoint = Resolve-RowToolAndroidCheckFile -RelativePath "tools\InvokeProjectCheck.ps1"
    if ($ResolvedEntryPoint -ne (Resolve-Path -LiteralPath $EntryPoint).Path) {
        throw "ANDROID_CHECK_ROOT was not used to resolve the configured entry point."
    }

    $env:ANDROID_CHECK_ROOT = Join-Path $TemporaryRoot "missing"
    try {
        Resolve-RowToolAndroidCheckFile `
            -RelativePath "tools\InvokeProjectCheck.ps1" `
            -DefaultRoot (Join-Path $TemporaryRoot "default") `
            -SiblingRoot (Join-Path $TemporaryRoot "sibling")
        throw "An invalid ANDROID_CHECK_ROOT was accepted."
    }
    catch {
        if ($_.Exception.Message -notmatch "ANDROID_CHECK_ROOT_INVALID") {
            throw
        }
    }
}
finally {
    $env:ANDROID_CHECK_ROOT = $PreviousAndroidCheckRoot
    if (Test-Path -LiteralPath $TemporaryRoot) {
        Remove-Item -LiteralPath $TemporaryRoot -Recurse -Force
    }
}
