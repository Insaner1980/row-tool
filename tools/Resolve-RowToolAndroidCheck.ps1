#Requires -Version 5.1

function Resolve-RowToolAndroidCheckFile {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)]
        [ValidateNotNullOrEmpty()]
        [string]$RelativePath,
        [string]$AndroidCheckRoot = $env:ANDROID_CHECK_ROOT,
        [string]$DefaultRoot = "C:\Dev\Android-check",
        [string]$SiblingRoot
    )

    if ([string]::IsNullOrWhiteSpace($SiblingRoot)) {
        $ProjectRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
        $SiblingRoot = Join-Path (Split-Path -Parent $ProjectRoot) "Android-check"
    }

    if (-not [string]::IsNullOrWhiteSpace($AndroidCheckRoot)) {
        $ConfiguredFile = Join-Path $AndroidCheckRoot $RelativePath
        if (-not (Test-Path -LiteralPath $ConfiguredFile -PathType Leaf)) {
            throw "ANDROID_CHECK_ROOT_INVALID: '$AndroidCheckRoot' does not contain '$RelativePath'."
        }
        return (Resolve-Path -LiteralPath $ConfiguredFile).Path
    }

    foreach ($CandidateRoot in @($DefaultRoot, $SiblingRoot) | Select-Object -Unique) {
        if ([string]::IsNullOrWhiteSpace($CandidateRoot)) {
            continue
        }
        $CandidateFile = Join-Path $CandidateRoot $RelativePath
        if (Test-Path -LiteralPath $CandidateFile -PathType Leaf) {
            return (Resolve-Path -LiteralPath $CandidateFile).Path
        }
    }

    throw "ANDROID_CHECK_ENGINE_NOT_FOUND: Set ANDROID_CHECK_ROOT to an Android-check checkout."
}
