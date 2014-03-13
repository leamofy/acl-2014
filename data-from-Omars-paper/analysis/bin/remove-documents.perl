#!/usr/bin/perl

# This script removes documents that are not included 
# in the NIST MT09_SharedTranslations set.

$print_doc = 1;
while($line = <>) {
    if($line =~ m/BBC_URD_20090103.0021|MHR_URD_20090116.0010|VOA_URD_20090105.0020|VOA_URD_20090123.0014|urd-NG-102-174502-11516107-post5|urd-NG-102-174502-11516165-post63|urd-WL-86-174276-11370243-post1|urd-WL-86-174276-11370256-post1/) {
	$print_doc = 0;
    }
    if($print_doc == 1) {
	print $line;
    }
    if($line =~ m/<\/doc>/) {
	$print_doc = 1;
    }
}
