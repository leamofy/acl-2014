#!/usr/bin/perl

# Quick and dirty script to remove Urdu words.  Not very reliable.

while($line = <>) {
    chomp $line;
    @words = split(/\s+/, $line);
    foreach $word (@words) {
	if($word =~ m/[a-zA-Z0-9]/) {
	    print $word . " ";
	}
    } 
    print "\n";
}
