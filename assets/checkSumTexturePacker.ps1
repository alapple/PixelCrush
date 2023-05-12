$checkSumLocation = "./assets/other/program-files/texture-packer-checksum.txt"
$lastChecksum = Get-Content -Path $checkSumLocation
$checkSum = 0

foreach ($size in Get-ChildItem -Recurse ./assets/input  | Select-Object -Property Length)
{
    $checkSum += [int]$size.Length
}
if ($checkSum -ne $lastChecksum) {
    Write-Host "Checksums don't match... Re-compiling texture atlas"
    java -cp runnable-texturepacker.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ./assets/input ./assets/output graphics
}

$checkSum | Out-File -FilePath ./assets/other/program-files/texture-packer-checksum.txt