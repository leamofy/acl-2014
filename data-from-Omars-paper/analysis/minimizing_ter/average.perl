#!/usr/bin/perl

# Computes the average for each line of space-speperated numbers

while($line = <>) {
    chomp $line;
    @numbers = split(/\s+/, $line);
    $total = 0;
    foreach $number (@numbers) {
	$total += $number;
    }
    $average = $total / scalar(@numbers);
    print "$average\n";
}
