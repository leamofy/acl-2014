open(OLD, $ARGV[0]) or die("Couldn't open $ARGV[0]\n");
open(NEW, $ARGV[1]) or die("Couldn't open $ARGV[1]\n");
open(MT, $ARGV[2]) or die("Couldn't open $ARGV[2]\n");

$lineNum = 1;
while($oldLine = <OLD>) {
    $newLine = <NEW>;
    $mt = <MT>;
    chomp $oldLine; 
    chomp $newLine;
    chomp $mt;
    if(!($oldLine eq $newLine)) {
	if($newLine =~ m/NO TRANSLATION FOUND/ || $newLine =~ m/sentence goes here/) {
	    print "$lineNum\t$mt\n";
	} else {
	    print "$lineNum\t$newLine\n";
	}
    } 
    $lineNum++;
}
close(OLD);
close(NEW);
close(MT);
