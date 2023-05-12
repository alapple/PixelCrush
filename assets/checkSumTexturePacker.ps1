$checkSumLocation = "./other/program-files/texture-packer-checksum.txt"
$checkSumFileExists = Test-Path -Path $checkSumLocation -PathType Leaf
if ($checkSumFileExists -eq $false)
{
    New-Item -Path $checkSumLocation
}
$lastChecksumInput, $lastChecksumOutput = Get-Content -Path $checkSumLocation
$checkSumInput = 0
$checkSumOutput = 0

foreach ($size in Get-ChildItem -Recurse ./input  | Select-Object -Property Length)
{
    $checkSumInput += [int]$size.Length
}

foreach ($size in Get-ChildItem -Recurse ./output  | Select-Object -Property Length)
{
    $checkSumOutput += [int]$size.Length
}

if ($checkSumInput -ne $lastChecksumInput -or $checkSumOutput -ne $lastChecksumOutput)
{
    Write-Host "Checksums don't match... Re-compiling texture atlas"
    Remove-Item -Force -Recurse ./output

    foreach ($dir in Get-ChildItem ./input)
    {
        java -cp ./other/program-files/runnable-texturepacker.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ./input/$dir ./output $dir
    }
}

$checkSumInput | Out-File -FilePath ./other/program-files/texture-packer-checksum.txt
$checkSumOutput | Out-File -Append -FilePath ./other/program-files/texture-packer-checksum.txt

Write-Host "done!"