$hl = 0;
while($line = <>) {
    if($line =~ m/<hl>/){
	$hl = 1;
    }
    if($line =~ m/<\/hl>/){
	$hl = 0;
    }
    if($line =~ m/seg id/) {
	if($hl == 1) {
	    print "hl\n";
	} else {
	    print "seg\n";
	}
    }
}
