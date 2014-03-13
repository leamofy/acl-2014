open(FILE1, $ARGV[0]);
open(FILE2, $ARGV[1]);

while($line1 = <FILE1>) {
    $line2 = <FILE2>;
    chomp $line1;
    chomp $line2;

    @fields1 = split(/\t/, $line1);
    @fields2 = split(/\t/, $line2);
    $diff = $fields1[0] - $fields2[0];
    print $diff;
    for($i = 1; $i < @fields1; $i++) {
	$diff = $fields1[$i] - $fields2[$i];
	print "\t$diff";
    }
    print "\n";

}
