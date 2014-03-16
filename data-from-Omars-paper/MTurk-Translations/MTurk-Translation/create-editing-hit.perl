binmode(STDIN, ":utf8");
binmode(STDOUT, ":utf8");

$numSegments = 10;
$lineNum = 1;

print "lineNum";
for($i = 1; $i <= $numSegments; $i++) {
    print ",sentence$i";
}
print "\n";


while(1 == 1) {
    print STDOUT "$lineNum";

    for($i = 0; $i < $numSegments; $i++) {
	$line = <STDIN>;
	chomp $line;
# formatting for MTurk
	$line =~ s/&/&amp;/g;
	$line =~ s/,/&#44;/g;
	$line =~ s/^\s+//g;
	$line =~ s/\s+$//g;
	$line =~ s/\s+/ /g;
	$line =~ s/\>/&gt;/g;
	$line =~ s/\</&lt;/g;
	$line =~ s/"/&quot;/g;
	$line =~ s/'/&#39;/g;
	print STDOUT ",$line";
	$lineNum++;
    }
    print STDOUT "\n";
}
