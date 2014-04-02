#!/bin/python
import csv
import sys
import math
import operator
import random
import numpy as np
from scipy import stats
import matplotlib.pyplot as plt
from scipy.stats import ttest_1samp
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

editor_data = {}

sentence_data = {}
n = 0
for line in sys.stdin:
	try : gold, trans, edit, t, e, tr_ter, gold_ter = line.strip().split('\t')
	except ValueError : print line; continue
	tr_ter = float(tr_ter)
	gold_ter = float(gold_ter)
	if tr_ter > 2 or gold_ter >2 : n+=1; continue
	if tr_ter == 0 and not(gold_ter == 0) : continue
	if (e,t) not in editor_data : editor_data[(e,t)] = [0,0,0] #ter from translation, ter from gold, total
	if (e,t) not in sentence_data : sentence_data[(e,t)] = (gold, trans, edit)
	if len(trans) < len(sentence_data[(e,t)][1]) : sentence_data[(e,t)] = (gold, trans, edit)
	editor_data[(e,t)][0] += tr_ter
	editor_data[(e,t)][1] += gold_ter
	editor_data[(e,t)][2] += 1

graph_data = []
graph_data2 = []
print n 
n = 0

for e in editor_data : 
	tr, gold, total = tuple(editor_data[e])
	avg_t = tr/total
	avg_g = gold/total
	if avg_t > 5 or avg_g > 5 : n += 1; continue
	if avg_g > 0 : graph_data.append((avg_t, avg_g, sentence_data[e]))
	if avg_g <= 0 : graph_data2.append((avg_t, avg_g, sentence_data[e]))


print n

me_x, me_y, me_s = sorted(graph_data, key=operator.itemgetter(0), reverse=True)[0]
wr_x, wr_y, wr_s = sorted(graph_data, key=operator.itemgetter(1), reverse=True)[0]

plt.scatter([d[0] for d in graph_data], [d[1] for d in graph_data],color='r', alpha=0.1, marker='^')
plt.scatter([d[0] for d in graph_data2], [d[1] for d in graph_data2], color='b', alpha=0.1)

ax = plt.subplot(1,1,1)
ax.spines['top'].set_color('white')
ax.spines['right'].set_color('white')
ax.spines['left'].set_color('white')
ax.spines['bottom'].set_color('white')
for t in ax.xaxis.get_ticklines(): t.set_color('white')
for t in ax.yaxis.get_ticklines(): t.set_color('white')
ax.axhline(y=0, xmin=0, xmax=6,color='k')

plt.xlim(0,2)
plt.xlabel('TER between pre- and post-edit translation', fontsize="large")
#plt.ylabel('Average TER between post-edit translation and gold translation')
plt.ylabel(r'Average change in TER$_{gold}$', fontsize="large")
plt.xticks(fontsize="large")
plt.yticks(fontsize="large")
#plt.title("Translation/editor pairs")

plt.show()
