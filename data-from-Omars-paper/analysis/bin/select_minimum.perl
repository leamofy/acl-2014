open(SCORES, $ARGV[0]);
open(VALUES, $ARGV[1]);
while($line = <SCORES>) {
    chomp $line;
    @fields = split(/\t/, $line);
    $min = $fields[0];
    $min_index = 0;
    for($i = 1; $i < @fields; $i++) {
	if($min > $fields[$i]) {
	    $min = $fields[$i];
	    $min_index = $i;
	}
    }
    $line = <VALUES>;
    chomp $line;
    @values = split(/\t/, $line);
    print $values[$min_index] . "\n";
}
close(SCORES);
close(VALUES);
