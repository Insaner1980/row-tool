function Test-SonarTokenConfigured {
    [CmdletBinding()]
    param(
        [AllowNull()]
        [string]$ProcessToken,
        [AllowNull()]
        [string]$UserToken,
        [Parameter(Mandatory)]
        [string[]]$PropertyPaths
    )

    if (
        -not [string]::IsNullOrWhiteSpace($ProcessToken) -or
        -not [string]::IsNullOrWhiteSpace($UserToken)
    ) {
        return $true
    }

    foreach ($Path in $PropertyPaths) {
        if (-not (Test-Path -LiteralPath $Path -PathType Leaf)) {
            continue
        }
        foreach ($Line in Get-Content -LiteralPath $Path -Encoding utf8) {
            if (
                $Line -match "^\s*systemProp\.sonar\.token\s*=(.*)$" -and
                -not [string]::IsNullOrWhiteSpace($Matches[1])
            ) {
                return $true
            }
        }
    }

    return $false
}

Export-ModuleMember -Function Test-SonarTokenConfigured
