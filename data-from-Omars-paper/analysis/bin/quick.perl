open(TURKERSCORES, $ARGV[0]);
open(SEGMENTSCORES, $ARGV[1]);
open(TURKERS, $ARGV[2]);

%turkerScore = ();
while($line = <TURKERSCORES>) {
    chomp $line;
    ($turker, $score, $count) = split(/\s+/, $line);
    $turkerScore{$turker} = $score;
}
close(TURKERSCORES);

while($line = <SEGMENTSCORES>) {
    chomp $line;
    @scores = split(/\s+/, $line);
    $line = <TURKERS>;
    chomp $line;
    @turkers = split(/\s+/, $line);
    for($i = 0; $i < @scores; $i++) {
	$score = $scores[1] + $turkerScore{$turkers[$i]};
	if($i != 0) { 
	    print "\t";
	}
	print $score; 
    }
    print "\n";
    
}
close(TURKERS);
close(SEGMENTSCORES);
