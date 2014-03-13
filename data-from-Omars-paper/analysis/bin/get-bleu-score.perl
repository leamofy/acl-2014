while($line = <>) {
    if($line =~ m/BLEU score = (\d?\.\d+)\s/) {
	print $1 . "\n";
    }
}
