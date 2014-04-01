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

graph_data = []

for line in sys.stdin:
	t, e = line.strip().split()
	t = float(t)
	e = float(e)
	d = e - t 
	if d == 0 : continue
	graph_data.append(('%.02f'%d,t,e))

graph_data = sorted(graph_data, key=operator.itemgetter(2))
graph_data = graph_data[:20] 
mn = min([d[2] for d in graph_data]) -.1
mx = max([d[1] for d in graph_data]) +.1

graph_data = [('',-1,-1)] + graph_data
#graph_data.append(('',-1,-1))

print graph_data

fig, ax = plt.subplots()

plt.vlines([i+1 for i in range(len(graph_data))], [mn for e in range(len(graph_data))], [mx for e in range(len(graph_data))])
p1 = plt.scatter(np.arange(len(graph_data)), [d[1] for d in graph_data], color='red', s=100)
p2 = plt.scatter(np.arange(len(graph_data)), [d[2] for d in graph_data], color='blue', s=100)

plt.xlim(0,len(graph_data))
plt.xticks(np.arange(len(graph_data)), [e[0] for e in graph_data], rotation=90)
plt.yticks(rotation=90)
plt.ylim(mn,mx)
plt.ylabel('TER')
plt.xlabel('Change in TER', rotation=180)
#plt.ylim(0,len(graph_data))
#plt.yticks(np.arange(len(graph_data)), [e[0] for e in graph_data])
#plt.xlim(0.4,1)
plt.legend((p1,p2),('Before Editing','After Editing'))
#ax.xaxis.set_major_formatter(FormatStrFormatter('%.02f'))
plt.show()
#plt.savefig("quality_compare.pdf")
