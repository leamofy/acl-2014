import sys

for line in sys.stdin : 
	s1, s2, s3, s4 = line.strip().split('\t')
	print (float(s1) + float(s2) + float(s3) + float(s4)) / 4
