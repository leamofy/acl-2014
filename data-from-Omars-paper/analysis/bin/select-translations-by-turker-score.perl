open(TURKERSCORES, $ARGV[0]);
open(TRANSLATIONS, $ARGV[1]);
open(TURKERS, $ARGV[2]);

$scores = {};
while($line = <TURKERSCORES>) {
    chomp $line;
    ($turker, $score, $num_segs) = split(/\t/, $line);
    $scores{$turker} = $score;
}
$scores{""} = 1000000;
close(TURKERSCORES);

while($line = <TRANSLATIONS>) {
    chomp $line;
    @translations = split(/\t/, $line);
    $line = <TURKERS>;
    chomp $line;
    @turkers = split(/\t/, $line);
    $bestScore = $scores{$turkers[0]};
    $bestTranslation = $translations[0];
    for($i = 1; $i < @translations; $i++) {
	# we're using TER, so lower scores are better
	if($scores{$turkers[$i]} < $bestScore) {
	    $bestScore = $scores{$turkers[$i]};
	    $bestTranslation = $translations[$i];
	}
    }
    print $bestTranslation . "\n";
}

close(TRANSLATIONS);
close(TURKERS);
