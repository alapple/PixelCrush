$checkSumLocation = "./assets/other/program-files/texture-packer-checksum.txt"
$checkSumFileExists = Test-Path -Path $checkSumLocation -PathType Leaf
if ($checkSumFileExists -eq $false)
{
    New-Item -Path $checkSumLocation
}
$lastChecksumInput, $lastChecksumOutput = Get-Content -Path $checkSumLocation
$checkSumInput = 0
$checkSumOutput = 0

foreach ($size in Get-ChildItem -Recurse ./assets/input  | Select-Object -Property Length)
{
    $checkSumInput += [int]$size.Length
}

foreach ($size in Get-ChildItem -Recurse ./assets/output  | Select-Object -Property Length)
{
    $checkSumOutput += [int]$size.Length
}

if ($checkSumInput -ne $lastChecksumInput -or $checkSumOutput -ne $lastChecksumOutput)
{
    Write-Host "Checksums don't match... Re-compiling texture atlas"
    Remove-Item -Force -Recurse ./assets/output

    foreach ($dir in Get-ChildItem ./assets/input)
    {
        java -cp ./assets/other/program-files/runnable-texturepacker.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ./assets/input/$dir ./assets/output $dir
    }
}

$checkSumInput | Out-File -FilePath ./assets/other/program-files/texture-packer-checksum.txt
$checkSumOutput | Out-File -Append -FilePath ./assets/other/program-files/texture-packer-checksum.txt

Write-Host "done!"