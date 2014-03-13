open(TURKERS, $ARGV[0]);
open(SCORES, $ARGV[1]);

$turkerScores = {};
$numSegmets = {};

$counter = {};
$interval = 10;

while($line = <TURKERS>) {
    chomp $line;
    @turkers = split(/\t/, $line);
    $line = <SCORES>;
    chomp $line;
    @scores = split(/\t/, $line);
    for($i = 0; $i < @turkers; $i++) {
	$turker = $turkers[$i];
	$score = $scores[$i];
	if($counter{$turker} % $interval == 0) {
	    $turkerScores{$turker} += $score;
	    $numSegmets{$turker}++;
	}
	$counter{$turker}++;
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
