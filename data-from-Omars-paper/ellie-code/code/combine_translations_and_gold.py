#!/bin/python

import sys

translations = open(sys.argv[1]).readlines()
gold = {}

#VOA_URD_20090129.0012	734	A3U16UHGUAKTZS	A3OHQRF1MDQ99B	Why is there decrease in exercise with increase in age?	Why do people exercise less as they get older?
#mt09_urdu_evalset_current_v0	BBC_URD_20090102.0020	2	The Committee meeting was held in Parliament House on Friday under the direction of Committee Chairman Mushahid Hussain Sayed, wherein it was recommended to the government that, in addition to sending relief supplies, it should also send a parliamentary delegation of the representatives of all political parties to Palestine.


for line in open(sys.argv[2]).readlines() :
	try : s, d, l, t = line.strip().split('\t')
	except ValueError : continue
	tid = '%s-%s'%(d,l)
	if tid not in gold : gold[tid] = t

for line in translations : 
	try : d, l, w, w2, t, e = line.strip().split('\t')
	except ValueError : continue
	tid = '%s-%s'%(d,l)
	if tid in gold: print '%s\t%s\t%s\t%s\t%s\t%s\t%s'%(d,l,w,w2,t,e,gold[tid])
