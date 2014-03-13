$total = 0;
while($line = <>) {
    chomp $line;
    $total += $line;
}
print $total . "\n";
