#!/usr/bin/perl

# This script extracts the 10 sentences from each assignment in a translation HIT
# and repurposes them for a editing HIT.

$line = <>;
%headers = ();
$line =~ s/"//g;
@fields = split(/,/, $line);
for($i = 0; $i < @fields; $i++) {
    $headers{$fields[$i]} = $i;
}

$maxSegs = 10;

$is_start = 1;
foreach $header (sort keys %headers) {
    if($header =~ m/Answer|Input|ID/ && !($header =~ m/seg\d|tag|url|comment|ipAddress|browserInfo|can_translate_into_urdu|machine|userDisplayLanguage/)) {
	$printable_header = $header;
	$printable_header =~ s/Answer.//g;
	$printable_header =~ s/Input.//g;
	if($is_start == 1) {
	    print $printable_header;
	    $is_start = 0;
	} else {
	    print ",$printable_header";
	}
    }
}
print "\n";


while($line = <>) {
    chomp $line;
    # Sometimes there is a newline in the translation.
    # This fixes that problem.
    while(!($line =~ m/"\s*$/)) {
	$nextline = <>;
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

    # formatting for MTurk
    $line =~ s/&/&amp;/g;
    $line =~ s/,/&#44;/g;
    $line =~ s/^\s+//g;
    $line =~ s/\s+$//g;
    $line =~ s/\>/&gt;/g;
    $line =~ s/\</&lt;/g;
    $line =~ s/"/&quot;/g;
    $line =~ s/'/&#39;/g;
    $line =~ s/Translation of the first sentence goes here.?//g;
    $line =~ s/Translation of the second sentence goes here.?//g;
    @fields = split(/\t/, $line);

    if(!($fields[$headers{"AssignmentStatus"}] =~ /Reject/)) {
	$is_start = 1;
	foreach $header (sort keys %headers) {
	    if($header =~ m/Answer|Input|ID/ && !($header =~ m/seg\d|tag|url|comment|ipAddress|browserInfo|can_translate_into_urdu|machine|userDisplayLanguage/)) {
		$index = $headers{$header};
		if($is_start == 1) {
		    print $fields[$index];
		    $is_start = 0;
		} else {
		    print "," . $fields[$index];
		}
	    }
	}
	print "\n";
    }
}

