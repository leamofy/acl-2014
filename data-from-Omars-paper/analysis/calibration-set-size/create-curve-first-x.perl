#!/usr/bin/perl

# This script calculates wraps text in XML and claculates Bleu scores using the 4 leave-one-out reference sets

$rootDir = "/Users/ccb/Documents/Papers/In\\ Progress/Crowdsourcing-translation/analysis";


@points = (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500);

#calculate-per-turker-ter-first-x.perl

foreach $num (@points) {
    $scores_filename = "first-x/per-turker-ter-scores.first-${num}_4-ref_calibration";
    print "perl $rootDir/calibration-set-size/calculate-per-turker-ter-first-x.perl $rootDir/mturk-results/turkers $rootDir/mturk-results/turkers.ter_segment_scores $num | sort -nk2 > $scores_filename\n";
    `perl $rootDir/calibration-set-size/calculate-per-turker-ter-first-x.perl $rootDir/mturk-results/turkers $rootDir/mturk-results/turkers.ter_segment_scores $num | sort -nk2 > $scores_filename`;

    $translations_filename = "first-x/translations.selected-by-first-${num}_4-ref_calibration";
    print "perl $rootDir/mturk-results/select-translations-by-turker-score.perl $scores_filename $rootDir/mturk-results/translations $rootDir/mturk-results/turkers > $translations_filename\n";
    `perl $rootDir/mturk-results/select-translations-by-turker-score.perl $scores_filename $rootDir/mturk-results/translations $rootDir/mturk-results/turkers > $translations_filename`;


    print "echo '$num' >> $translations_filename.bleu_scores";
    `echo '$num' >> $translations_filename.bleu_scores`;


    open(FILE, $scores_filename);
    $total_source_lines = <FILE>;
    chomp $total_source_lines;
    print "echo '$total_source_lines' >> $translations_filename.bleu_scores";
    `echo '$total_source_lines' >> $translations_filename.bleu_scores`;

    $total = 0;
    while($line = <FILE>) {
	chomp $line;
	@fields = split(/\t/, $line);
	$total += $fields[2];
    }
    close(FILE);

    print "echo '$total' >> $translations_filename.bleu_scores";
    `echo '$total' >> $translations_filename.bleu_scores`;


    print "perl $rootDir/bin/wrap-xml.perl $rootDir/mturk-full/turker-translations.1.xml en < $translations_filename > $translations_filename.xml\n";
    `perl $rootDir/bin/wrap-xml.perl $rootDir/mturk-full/turker-translations.1.xml en < $translations_filename > $translations_filename.xml`;
    for($i = 1; $i <= 4; $i ++) {
	print "perl $rootDir/bin/mteval-v13a.pl -s $rootDir/ref-sets-full/mt09_urdu_evalset_current_src.xml -r $rootDir/ref-sets-full/mt09_urdu_evalset_current_v3-ref.minus-$i.xml  -t $translations_filename.xml | perl $rootDir/bin/get-bleu-score.perl >> $translations_filename.bleu_scores\n";
	`perl $rootDir/bin/mteval-v13a.pl -s $rootDir/ref-sets-full/mt09_urdu_evalset_current_src.xml -r $rootDir/ref-sets-full/mt09_urdu_evalset_current_v3-ref.minus-$i.xml  -t $translations_filename.xml | perl $rootDir/bin/get-bleu-score.perl >> $translations_filename.bleu_scores`;
    }
}
