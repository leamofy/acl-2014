open(EDITS, $ARGV[0]) or die("Couldn't open $ARGV[0]\n");
open(RAW, $ARGV[1]) or die("Couldn't open $ARGV[1]\n");
open(MT, $ARGV[2]) or die("Couldn't open $ARGV[2]\n");


$lineNum = 1;
while($edited = <EDITS>) {
    $unedited = <RAW>;
    $mt = <MT>;

    $edited =~ s/\s/ /g;
    $unedited =~ s/\s/ /g;
    $mt =~ s/\s/ /g;

    chomp $edited;
    chomp $unedited;
    if($edited =~ m/NO TRANSLATION FOUND/ || $edited =~ m/sentence goes here/) {
	if($unedited =~ m/NO TRANSLATION FOUND/ || $unedited =~ m/sentence goes here/) {
	    print "$lineNum\t$mt\n";
	} else {
	    print "$lineNum\t$unedited\n";
	}
    } else {
	print "$lineNum\t$edited\n";
    }

    $lineNum++;
}
close(EDITS);
close(RAW);
