import sys

for line in sys.stdin : 
	s1, s2 = line.strip().split('\t')
	print float(s1) - float(s2) 
