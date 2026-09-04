$ProjectCheckCommand = "test-check"
$ProjectRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
. "$PSScriptRoot\Resolve-RowToolAndroidCheck.ps1"
$ProjectCheckScript = Resolve-RowToolAndroidCheckFile -RelativePath "tools\InvokeProjectCheck.ps1"
& $ProjectCheckScript -ProjectCheckCommand $ProjectCheckCommand -Root $ProjectRoot -ProjectId "rowtool" -Full @args
exit $LASTEXITCODE
