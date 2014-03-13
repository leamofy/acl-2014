#!/usr/bin/perl

# This script calculates wraps text in XML and claculates Bleu scores using the 4 leave-one-out reference sets

$rootDir = "/Users/ccb/Documents/Papers/In\\ Progress/Crowdsourcing-translation/analysis";


$setSize = 10;
for($num = 1; $num <= $setSize; $num++) {
    $scores_filename = "per-turker-ter-scores.$num-in-${setSize}_4-ref_calibration";
    print "perl $rootDir/calibration-set-size/calculate-per-turker-ter-x-in-10.perl $rootDir/mturk-results/turkers $rootDir/mturk-results/turkers.ter_segment_scores $num $setSize | sort -nk2 > $scores_filename\n";
    `perl $rootDir/calibration-set-size/calculate-per-turker-ter-x-in-10.perl $rootDir/mturk-results/turkers $rootDir/mturk-results/turkers.ter_segment_scores $num $setSize | sort -nk2 > $scores_filename`;

    $translations_filename = "translations.selected-by-$num-in-${setSize}_4-ref_calibration";
    print "perl $rootDir/mturk-results/select-translations-by-turker-score.perl $scores_filename $rootDir/mturk-results/translations $rootDir/mturk-results/turkers > $translations_filename\n";
    `perl $rootDir/mturk-results/select-translations-by-turker-score.perl $scores_filename $rootDir/mturk-results/translations $rootDir/mturk-results/turkers > $translations_filename`;

    open(FILE, $scores_filename);
    $total_source_lines = <FILE>;
    chomp $total_source_lines;
    print "echo '$total_source_lines' >> $translations_filename.bleu_scores";
    `echo '$total_source_lines' >> $translations_filename.bleu_scores`;
    close(FILE);

    print "perl $rootDir/bin/wrap-xml.perl $rootDir/mturk-full/turker-translations.1.xml en < $translations_filename > $translations_filename.xml\n";
    `perl $rootDir/bin/wrap-xml.perl $rootDir/mturk-full/turker-translations.1.xml en < $translations_filename > $translations_filename.xml`;
    for($i = 1; $i <= 4; $i ++) {
	print "perl $rootDir/bin/mteval-v13a.pl -s $rootDir/ref-sets-full/mt09_urdu_evalset_current_src.xml -r $rootDir/ref-sets-full/mt09_urdu_evalset_current_v3-ref.minus-$i.xml  -t $translations_filename.xml | perl $rootDir/bin/get-bleu-score.perl >> $translations_filename.bleu_scores\n";
	`perl $rootDir/bin/mteval-v13a.pl -s $rootDir/ref-sets-full/mt09_urdu_evalset_current_src.xml -r $rootDir/ref-sets-full/mt09_urdu_evalset_current_v3-ref.minus-$i.xml  -t $translations_filename.xml | perl $rootDir/bin/get-bleu-score.perl >> $translations_filename.bleu_scores`;
    }
}
