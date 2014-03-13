binmode(STDIN, ":utf8");
binmode(STDOUT, ":utf8");

$segLevel = $ARGV[0];

%docToSegMap = ();
%numSegments = ();
%segToLineNumMap = ();
$maxSegments = 0;
$document = "";
$lineNum = 0;
while($line = <STDIN>) {
    if($line =~ m/<doc docid="(\S+)"/) {
	$document = $1;
    } elsif($line =~ m/<seg id="(\d+)">/) {
	$segID = $1;
	$line =~ s/<seg id="\d+">\s*//g;
	$line =~ s/\s*<\/seg>\s*//g;
	$docToSegMap{$document}{$segID} = $line;
	$numSegments{$document}++;
	$lineNum++;
	$segToLineNumMap{$document}{$segID} = $lineNum;
	if($numSegments{$document} > $maxSegments) {
	    $maxSegments = $numSegments{$document};
	}
    }
}

print "document,lineNum,numSegs";
for($i = 1; $i <= $maxSegments; $i++) {
    print ",seg$i";
}
print "\n";

foreach $document (keys %docToSegMap) {
    $numSegs = $numSegments{$document};

    if($segLevel == $numSegs) {
	$lineNum = $segToLineNumMap{$document}{1};
	print STDOUT "$document,$lineNum,$numSegs";
	

	for($i = 1; $i <= $numSegs; $i++) {
	    $seg = $docToSegMap{$document}{$i};

	    # formatting for MTurk
	    $seg =~ s/&/&amp;/g;
	    $seg =~ s/,/&#44;/g;
	    $seg =~ s/^\s+//g;
	    $seg =~ s/\s+$//g;
	    $seg =~ s/\s+/ /g;
	    $seg =~ s/\>/&gt;/g;
	    $seg =~ s/\</&lt;/g;
	    $seg =~ s/"/&quot;/g;
	    $seg =~ s/'/&#39;/g;
	    
	    print STDOUT ",$seg";
#	print STDOUT ",seg$i";
	}
	for($j = $i; $j <= $maxSegments; $j++) {
	    print STDOUT ",";
	}
	print STDOUT "\n";
    }
}
