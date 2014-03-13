# This script processes a set of Batch.csv files returned for
# a translation task from Mecahnical Turk.  It concatenates
# all of translations into their original ordering.  This version 
# assumes only one translation per source segment. 

%sources = ();
%translations = ();
%translators = ();

$maxSegs = 10;
$maxTranslations = 0;
$maxLines = 0;

foreach $filename (@ARGV) {
    open(TRANSLATIONRESULTS, $filename) or die("Couldn't open $filename\n");

    %fieldNamesToIndexMap = ();
    $line = <TRANSLATIONRESULTS>;
    chomp $line;
    $line =~ s/^"//;
    $line =~ s/"\s*$//;
    @fields = split(/\",\"/, $line);
    for($i = 0; $i < @fields; $i++) {
	$field = $fields[$i];
	$fieldNamesToIndexMap{$field} = $i;
    }


    while($line = <TRANSLATIONRESULTS>) {
	chomp $line;
	# Sometimes there is a newline in the translation.
	# This fixes that problem.
	while(!($line =~ m/"\s*$/)) {
	    $nextline = <TRANSLATIONRESULTS>;
	    $line = $line . " " . $nextline;
	    chomp $line;
	}
 
	$line =~ s/^"//;
	$line =~ s/"\s*$//;
	$line =~ s/\t/ /g;
	$line =~ s/\s/ /g;
	#change the delimiter between fields to a tab
	$line =~ s/","/\t/g;
	$line =~ s/&amp;/&/g;
        $line =~ s/&gt;/>/g;
        $line =~ s/&lt;/</g;
        $line =~ s/&#39;/'/g;
        $line =~ s/&#44;/,/g;
        $line =~ s/&quot;/"/g;
#	$line =~ s/&/&amp;/g;
#	$line =~ s/\>/&gt;/g;
#	$line =~ s/\</&lt;/g;
#	$line =~ s/'/&#39;/g;
#	$line =~ s/,/&#44;/g;
#	$line =~ s/"/&quot;/g;
	
	@fields = split(/\t/, $line);
	
	for($i = 1; $i <= $maxSegs; $i++) {
	    $translator = $fields[$fieldNamesToIndexMap{"WorkerId"}];
	    print $translator . "\n";
	}
    }

    close(TRANSLATIONRESULTS);
}

