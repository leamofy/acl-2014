# This script processes a set of Batch.csv files returned for
# a translation task from Mecahnical Turk.  It concatenates
# all of translations into their original ordering.  This version 
# assumes only one translation per source segment. 

%translations = ();

$maxSegs = 0;
$maxTranslations = 0;
$maxLines = 1792;

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
	# keep track of the number of segments
	if($field =~ m/Input.sentence(\d+)/g) {
	    $segNum = $1;
	    if($segNum > $maxSegs) {
		$maxSegs = $segNum;
	    }
	}
	# ccb - debugging
#	print "$i\t$field\n";
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
	# map HTML characters back to their original form
	$line =~ s/&amp;/&/g;
        $line =~ s/&gt;/>/g;
        $line =~ s/&lt;/</g;
        $line =~ s/&#39;/'/g;
        $line =~ s/&#44;/,/g;
        $line =~ s/&quot;/"/g;
	
	@fields = split(/\t/, $line);
	
	
	


	if(!($status =~ m/Reject/)) {
	    for($i = 1; $i <= $maxSegs; $i++) {
		$translation = $fields[$fieldNamesToIndexMap{"Answer.edited${i}"}];
		$translation =~ s/^\s+//g;
		
		
		$lineNum = -1;
		if($fieldNamesToIndexMap{"Input.lineNum$i"} == 0) {
		    $docStart = $fields[$fieldNamesToIndexMap{"Input.lineNum"}];
		    $lineNum = $docStart + $i -1;
		} else {
		    $lineNum = $fields[$fieldNamesToIndexMap{"Input.lineNum$i"}];
		}
		$translations{$lineNum} = $translation;
		

	    }
	}
    }
    close(TRANSLATIONRESULTS);
}


for($lineNum = 1; $lineNum <= $maxLines; $lineNum++) {
    $translation = $translations{$lineNum};

    if($translation eq "") {
	print "NO TRANSLATION FOUND ON $lineNum\n";
    } else {
	print $translation . "\n";
    }
}
