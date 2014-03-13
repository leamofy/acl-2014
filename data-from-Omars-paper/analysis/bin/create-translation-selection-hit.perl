#!/usr/bin/perl

# This script formats the MTurk translations along with their WorkerIDs and seg_ids 
# for a HIT that lets a second set of Turkers vote on the best translation from 
# the multiple translations. 

# The control assumes a positive control followed by 3 negatives (for instance, and LDC ref followed by 3 MT outputs).

# Usage: perl create-translation-selection-hit.perl mturk-results/translations mturk-results/turkers mturk-results/seg_ids selection-by-turkers/pos-and-neg-control

open(TRANSLATIONS, $ARGV[0]);
open(TURKERS, $ARGV[1]);
open(SEGIDS, $ARGV[2]);
open(CONTROL, $ARGV[3]);
$num_segs_per_hit = 5;
$counter = 0;
$control_id = $num_segs_per_hit;

$header = "num_segs,control_id,";
for($i = 1; $i <= $num_segs_per_hit; $i++) {
    $header = $header . "seg_id_$i,translator${i}_1,translator${i}_2,translator${i}_3,translator${i}_4,translation${i}_1,translation${i}_2,translation${i}_3,translation${i}_4,";
}
chop $header;
print $header . "\n";



$output_csv_line = $num_segs_per_hit . "," . $control_id  . ",";
$segID = <SEGIDS>;
chomp $segID;

# the control is always one ahead.
$control = <CONTROL>;

while($translations = <TRANSLATIONS>) {
    chomp $translations;

    # escape characters for MTurk
    $translations =~ s/&/&amp;/g;
    $translations =~ s/,/&#44;/g;
    $translations =~ s/^\s+//g;
    $translations =~ s/\s+$//g;
    $translations =~ s/\>/&gt;/g;
    $translations =~ s/\</&lt;/g;
    $translations =~ s/"/&quot;/g;
    $translations =~ s/'/&#39;/g;
    # replace tabs with comma
    $translations =~ s/\t/,/g;
    
    $turkers = <TURKERS>;
    chomp $turkers;
    $turkers =~ s/\t/,/g;


    $output_csv_line = $output_csv_line . "$segID,$turkers,$translations,";
    $control = <CONTROL>;
    $counter++;


    if($counter % ($num_segs_per_hit-1) == 0) {
	# generate the control.
	$segID = <SEGIDS>;
	chomp $segID;
	# escape characters for MTurk
	$control =~ s/&/&amp;/g;
	$control =~ s/,/&#44;/g;
	$control =~ s/^\s+//g;
	$control =~ s/\s+$//g;
	$control =~ s/\>/&gt;/g;
	$control =~ s/\</&lt;/g;
	$control =~ s/"/&quot;/g;
	$control =~ s/'/&#39;/g;
	# replace tabs with comma
	$control =~ s/\t/,/g;
	$control_labels = "positive,negative,negative,negative";

	#append it
	$output_csv_line = $output_csv_line . "$segID,$control_labels,$control";
	print $output_csv_line . "\n";
	$output_csv_line = $num_segs_per_hit . "," . $control_id . ",";
	$counter = 0;
    } else {
	$segID = <SEGIDS>;
	chomp $segID;
    }



}

close(SEGIDS);
close(TURKERS);
close(TRANSLATIONS);
