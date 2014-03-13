while($line = <>) {
    if($line =~ m/<seg/) {
	$line =~ s/<seg[^>]*>//g;
	$line =~ s/<\/seg[^>]*>//g;
	$line =~ s/^\s*//g;
	$line =~ s/\s*$//g;
	print $line . "\n";
    }
}
