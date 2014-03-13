open(TURKERS, $ARGV[0]);
open(SCORES, $ARGV[1]);


$allTurkers = {};
$turkerScores = {};
$numSegmets = {};

$num_sentences_for_calibration = $ARGV[2];
$counter = {};
$line_counter = 0;

while($line = <TURKERS>) {
    chomp $line;
    @turkers = split(/\t/, $line);
    $line = <SCORES>;
    chomp $line;
    @scores = split(/\t/, $line);
    $used_line = 0;
    for($i = 0; $i < @turkers; $i++) {
	$turker = $turkers[$i];
	$allTurkers{$turker} = 1;
	$score = $scores[$i];
	if($counter{$turker} < $num_sentences_for_calibration) {
	    $turkerScores{$turker} += $score;
	    $numSegmets{$turker}++;
	    $used_line = 1;
	}
	$counter{$turker}++;
    }
    if($used_line) {
	$line_counter++;
    }
}

print $line_counter . "\n";
foreach $turker (keys(%allTurkers)) {
    if(!($turker eq "") ) {
	if($numSegmets{$turker} > 0) {
	    $avgScore = $turkerScores{$turker} / $numSegmets{$turker};
	    print "$turker\t$avgScore\t$numSegmets{$turker}\n";
	} else {
	    print "$turker\t1\t0\n";
	}
    }
}

close(TURKERS);
close(SCORES);
