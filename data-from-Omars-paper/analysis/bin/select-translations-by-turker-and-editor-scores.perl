open(TURKERSCORES, $ARGV[0]);
open(EDITORSCORES, $ARGV[1]);
open(EDITS, $ARGV[2]);
open(TURKERS, $ARGV[3]);
open(EDITORS, $ARGV[4]);

$turker_scores = {};
while($line = <TURKERSCORES>) {
    chomp $line;
    ($turker, $score, $num_segs) = split(/\t/, $line);
    $turker_scores{$turker} = $score;
}
$turker_scores{""} = 1000000;
close(TURKERSCORES);


$editor_scores = {};
while($line = <EDITORSCORES>) {
    chomp $line;
    ($editor, $score, $num_segs) = split(/\t/, $line);
    $editor_scores{$editor} = $score;
}
$editor_scores{""} = 1000000;
close(EDITORSCORES);


while($line = <EDITS>) {
    chomp $line;
    @edits = split(/\t/, $line);
    $line = <TURKERS>;
    chomp $line;
    @turkers = split(/\t/, $line);

    $line = <EDITORS>;
    chomp $line;
    @editors = split(/\t/, $line);

    # the editor scores are differences 
    $bestScore = $turker_scores{$turkers[0]} - $editor_scores{$editors[0]};
    $bestEdit = $edits[0];
    $best_i = 0;
#    print "----\n$bestScore\t$turker_scores{$turkers[$best_i]}\t$editor_scores{$editors[$best_i]}\t$bestEdit\n";
    for($i = 1; $i < @edits; $i++) {
	# we're using TER, so lower scores are better
	if(($turker_scores{$turkers[$i]} - $editor_scores{$editors[$i]}) < $bestScore) {
	    $bestScore = ($turker_scores{$turkers[$i]}  - $editor_scores{$editors[$i]});
	    $bestEdit = $edits[$i];
	    $best_i = $i;
#	    print "$bestScore\t$turker_scores{$turkers[$best_i]}\t$editor_scores{$editors[$best_i]}\t$bestEdit\n";
	}
    }
    print  $bestEdit . "\n";
}

close(EDITS);
close(TURKERS);
close(EDITORS);
