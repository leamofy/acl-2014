#!/usr/bin/perl

# This selects the best translation on a segment-by-segment basis.
# Example usage: perl select-translations-by-segment-score.perl selection-by-turkers/best-turker-per-segment mturk-results/translations mturk-results/turkers mturk-results/seg_ids

use Encode;
use utf8; 

open(SEGMENTSCORES, $ARGV[0]);
open(TRANSLATIONS, $ARGV[1]);
open(TURKERS, $ARGV[2]);
open(SEGIDS, $ARGV[3]);


my %bestTranslatorPerSegment = ();
while($line = <SEGMENTSCORES>) {
    chomp $line;
    @fields = split(/\t/, $line);
    $seg_id = $fields[0];
    $turker = $fields[1];
    
    $bestTranslatorPerSegment{$seg_id} = $turker;
}
close(SEGMENTSCORES);


while($line = <SEGIDS>) {
    chomp $line;
    $seg_id = decode_utf8($line);
    $bestTurker = $bestTranslatorPerSegment{$seg_id};

    print "$seg_id\t$bestTurker ";

    $index_of_best = 0;
    $line = <TURKERS>;
    chomp $line;

    @turkers = split(/\t/, $line);
    for($i = 0; $i < @turkers; $i++) {
	if($bestTurker eq $turkers[$i]) {
	    $index_of_best = $i;
	}
    }

    print $turkers[$index_of_best] . "\t";

    $line = <TRANSLATIONS>;
    chomp $line;
    @translations = split(/\t/, $line);
    $bestTranslation = $translations[$index_of_best];
    print $bestTranslation . "\n";
}

close(TRANSLATIONS);
close(TURKERS);
close(SEGIDS);
