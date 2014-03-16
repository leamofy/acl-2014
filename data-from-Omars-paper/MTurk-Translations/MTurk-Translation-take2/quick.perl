$docid = "";
while($line = <>) {
    if($line =~ m/docid\s*=\s*"([^"]+)"/) {
	$docid = $1;
    }
    if($line =~ m/seg id\s*=\s*"([^"]+)"/) {
	$segid = $1;
	print $docid . "__" . $segid . "\n";
    }
}
