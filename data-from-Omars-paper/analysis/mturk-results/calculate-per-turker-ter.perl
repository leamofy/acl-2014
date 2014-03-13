open(TURKERS, $ARGV[0]);
open(SCORES, $ARGV[1]);

$turkerScores = {};
$numSegmets = {};

$maxSegmentsToConsider = 100000;

while($line = <TURKERS>) {
    chomp $line;
    @turkers = split(/\t/, $line);
    $line = <SCORES>;
    chomp $line;
    @scores = split(/\t/, $line);
    for($i = 0; $i < @turkers; $i++) {
	$turker = $turkers[$i];
	$score = $scores[$i];
	if($numSegmets{$turker} <= $maxSegmentsToConsider) {
	    $turkerScores{$turker} += $score;
	    $numSegmets{$turker}++;
	}
    }
}

foreach $turker (keys(%turkerScores)) {
    if(!($turker eq "") ) {
	$avgScore = $turkerScores{$turker} / $numSegmets{$turker};
	print "$turker\t$avgScore\t$numSegmets{$turker}\n";
    }
}

close(TURKERS);
close(SCORES);
