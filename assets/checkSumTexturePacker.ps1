$checkSumLocation = "./assets/other/program-files/texture-packer-checksum.txt"
$lastChecksum = Get-Content -Path $checkSumLocation
$checkSum = 0

foreach ($size in Get-ChildItem -Recurse ./assets/input  | Select-Object -Property Length)
{
    $checkSum += [int]$size.Length
}
if ($checkSum -ne $lastChecksum) {
    Write-Host "Checksums don't match... Re-compiling texture atlas"
    Remove-Item ./assets/output

    foreach ($dir in Get-ChildItem ./assets/input) {
        java -cp ./assets/other/program-files/runnable-texturepacker.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ./assets/input/$dir ./assets/output $dir
    }
}

$checkSum | Out-File -FilePath ./assets/other/program-files/texture-packer-checksum.txt