#!/bin/python

import sys

translations = open(sys.argv[1]).readlines()
edits = {}

for line in open(sys.argv[2]).readlines() :
	try : w, t, e = line.strip().split('\t')
	except ValueError : continue
	if t not in edits : edits[t] = []
	edits[t].append((w,e))

for line in translations : 
	try : d, l, w, t = line.strip().split('\t')
	except ValueError : continue
	if t in edits : 
		for w2, e in edits[t] : 
			print '%s\t%s\t%s\t%s\t%s\t%s'%(d,l,w,w2,t,e)
