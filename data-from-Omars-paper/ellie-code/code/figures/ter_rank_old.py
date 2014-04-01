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

graph_data = []

seen_editors = set()
lookup = {}

for line in sys.stdin:
	wt, we, st, se, t, e = line.strip().split('\t')
	t = float(t)
	e = float(e)
	d = e - t 
	if d == 0 : continue
	if wt == 'A8V7WA74IOHZ9' : 
		if we not in seen_editors : 
			graph_data.append((wt,we,t,e,d,st,se))
			lookup[st] = se
			seen_editors.add(we)

graph_data = sorted(graph_data, key=operator.itemgetter(4))
graph_data = graph_data[:10] 

before_rank = [e[5] for e in sorted(graph_data, key=operator.itemgetter(2))]
after_rank = [e[5] for e in sorted(graph_data, key=operator.itemgetter(3))]

graph_data = [(st, before_rank.index(st), after_rank.index(st)) for wt,we,t,e,d,st,se in graph_data]

fig, ax = plt.subplots()

for d in graph_data : 
	plt.annotate('\n'.join(textwrap.wrap(d[0],85)),xy=(0,-d[1]), xytext=(-1,-d[1]),fontsize=10,ha='right')
	plt.annotate('\n'.join(textwrap.wrap(lookup[d[0]],85)),xy=(1,-d[2]), xytext=(2,-d[2]),fontsize=10,ha='left') 
	plt.plot([0,1], [-d[1],-d[2]], color='k', marker='o')
plt.ylim([-10,1])
plt.xlim([-17,17])
plt.xticks([])
plt.yticks([])
plt.show()
