while($line = <>) {
    chomp $line;
    ($numerator, $denominator) = split(/\s+\/\s+/, $line);
    if($denominator != 0) {
	$quotient = $numerator/$denominator;
	print $quotient . "\n";
    } else {
	print "0\n";
    }
}
