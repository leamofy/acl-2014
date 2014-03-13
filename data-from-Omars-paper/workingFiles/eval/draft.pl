#!/usr/bin/env perl
#
# Purpose: To pull out doc bleu scores. 
#
# Usage:  % draft.pl f1
# where f1 is a file containing the output of mteval13.pl run with the -d 1 flag. 
# 
#
# Example: % draft.pl /home/hltcoe/mbloodgood/papers/active-learning-for-smt/mturk-paper/workingFiles/eval/noeditingmteval13Scores
# 
#################################################

use strict;
#use File::Copy;
#use File::Path;
use ArrayProcessing qw( avg percentiles variance );

my ($f1) = @ARGV[0];

open(IN,"$f1") || die "cant $f1:$!";
my @bleu =(); # array of bleu scores
while(my $line = <IN>) {
  my $bleu=0; 
  if ($line =~ /^BLEU\s+score\s+using\s+4-grams\s+=\s+(\d\.\d\d\d\d)/) {
    $bleu=$1;
    push(@bleu,$bleu);
  }
}

=pod
for(my $i=0; $i<@bleu; $i++) {
  print "$bleu[$i]\n";
}
=cut

print "Num Docs: ",scalar(@bleu),"\n";
print "AVG: ",avg(@bleu),"\n";
print "VAR: ",variance(@bleu),"\n";
my @sorted = sort {$a <=> $b} (@bleu);
percentiles(\@sorted,10);
