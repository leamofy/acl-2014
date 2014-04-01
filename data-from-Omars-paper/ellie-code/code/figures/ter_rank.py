#!/bin/python
import csv
import sys
import math
import operator
import numpy as np
from scipy import stats
import matplotlib.pyplot as plt
from scipy.stats import ttest_1samp
from matplotlib.ticker import MultipleLocator, FormatStrFormatter
from matplotlib.transforms import Bbox
import textwrap


segments = {}
lookup = {}

sids = {}

for line in sys.stdin:
	sid, wt, we, st, se, t, e = line.strip().split('\t')
	t = float(t)
	e = float(e)
	if sid not in segments : segments[sid] = {}; sids[sid] = 0
	if wt not in segments[sid] : segments[sid][wt] = {}
	segments[sid][wt][we] = (wt,we,t,e,st,se)
	sids[sid] += 1

segments_order = [s[0] for s in sorted(sids.iteritems(), key=operator.itemgetter(1), reverse=True)[20:40]]

colors = ['b', 'r', 'g', 'k']

for sid in segments_order : 
	print sids[sid]
	graph_data_dict = segments[sid]

	before = []
	after = []
	seen = set()
	for kt in graph_data_dict : 
		for ke in graph_data_dict[kt] : 
			wt, we, t, e, st, se = graph_data_dict[kt][ke]
			if wt not in seen : before.append((t,wt)); seen.add(wt)
			after.append((e, (wt,we)))
	before_rank = [e[1] for e in sorted(before, key=operator.itemgetter(0))]
	after_rank = [e[1] for e in sorted(after, key=operator.itemgetter(0))]

	fig, ax = plt.subplots()
	
	for i,kt in enumerate(graph_data_dict.keys()) : 
		plotted = False
		for ke in graph_data_dict[kt] : 
			wt, we, t, e, st, se = graph_data_dict[kt][ke]
			b = before_rank.index(wt)
			a = after_rank.index((wt,we))
			print b, a
			if not plotted : 
				plt.annotate('\n'.join(textwrap.wrap(st,80)),xy=(-2,-b), xytext=(-3,-b),fontsize=10,ha='right',color=colors[i])
				plotted = True
			plt.annotate('\n'.join(textwrap.wrap(se,80)),xy=(3,-a), xytext=(4,-a),fontsize=10,ha='left', color=colors[i]) 
			plt.plot([-2,3], [-b,-a], marker='o', color=colors[i])
	plt.ylim([-10,1])
	plt.xlim([-17,17])
	plt.xticks([])
	plt.yticks([])
	plt.show()
