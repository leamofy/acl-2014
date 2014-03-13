while($line = <>) {
    if($line =~ m/\s*<seg/) {
	$line =~ s/^\s*<seg[^>]+>//g;
	$line =~ s/<\/seg>\s*//g;
	print $line . "\n";
    }
}
