foreach $file (@ARGV){
    open(FILE, $file);
    print $file;
    while($line = <FILE>) {
	chomp $line;
	print  "\t" . $line;
    }
    close(FILE);
    print "\n";
}
