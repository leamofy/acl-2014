#!/bin/python

import sys

for line in sys.stdin : 
	n, d = line.strip().split('/')
	n = float(n); d = float(d)
	if d == 0 : print 0
	else : print n / d
