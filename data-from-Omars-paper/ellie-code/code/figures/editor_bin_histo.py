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
import matplotlib.ticker as ticker
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

editor_data = {}
translator_data = {}
pair_data = {}

n = 0
for line in sys.stdin:
	try : t, e, tr_ter, gold_ter = line.strip().split()
	except ValueError : print line; continue
	tr_ter = float(tr_ter)
	gold_ter = float(gold_ter)
	#some bugs in how omar's tsv was format causes some of the fields to concatenate together or to be blank, leaving very high TERs
	if tr_ter > 2 or gold_ter >2 : n+=1; continue
	if tr_ter == 0 and not(gold_ter == 0) : continue
	if e not in editor_data : editor_data[e] = [0,0,0] #ter from translation, ter from gold, total
	if t not in translator_data : translator_data[t] = [0,0,0] #ter from translation, ter from gold, total
	if (e,t) not in pair_data : pair_data[(e,t)] = [0,0,0] #ter from translation, ter from gold, total
	editor_data[e][0] += tr_ter
	editor_data[e][1] += gold_ter
	editor_data[e][2] += 1
	translator_data[t][0] += tr_ter
	translator_data[t][1] += gold_ter
	translator_data[t][2] += 1
	pair_data[(e,t)][0] += tr_ter
	pair_data[(e,t)][1] += gold_ter
	pair_data[(e,t)][2] += 1

editors = []
translators = []

for e in editor_data : 
	tr, gold, total = tuple(editor_data[e])
	avg_t = tr/total
	avg_g = gold/total
#	if avg_t > 5 or avg_g > 5 : n += 1; continue
	editors.append((e, avg_g))
editors = [e for e in sorted(editors, key=operator.itemgetter(1), reverse=True)]

for t in translator_data : 
	tr, gold, total = tuple(translator_data[t])
	avg_t = tr/total
	avg_g = gold/total
#	if avg_t > 5 or avg_g > 5 : n += 1; continue
	translators.append((t, avg_t))
translators = [t for t in sorted(translators, key=operator.itemgetter(1), reverse=True)]

t_bins = {}; e_bins = {}; e_bin_range = {}; t_bin_range = {}

#bin 1 editors - most change from original
#bin 1 translators - highest TER translations
for i in range(5) : 
	ne = len(editors)/5
	nt = len(translators)/5
	es = editors[i*ne:(i+1)*ne]
	e_bins[i] = [e[0] for e in es]
	e_bin_range[i] = r'%.2f $\endash$ %.2f'%(min([e[1] for e in es]), max([e[1] for e in es]))
	ts = translators[i*nt:(i+1)*nt]
	t_bins[i] = [t[0] for t in ts]
#	t_bin_range[i] = r'%.2f $\endash$'%(min([t[1] for t in ts]))+'\n%.2f'%(max([t[1] for t in ts]))
	t_bin_range[i] = r'%.1f $\endash$ %.1f'%(min([t[1] for t in ts]), max([t[1] for t in ts]))

for i in range(5) : 
	
	ax = plt.subplot(5, 1, i+1)
#	ax.patch.set_visible(False)
#	ax.axis('off')
	plt.tight_layout()
	ax.spines['top'].set_color('white')
	ax.spines['right'].set_color('white')
	ax.spines['left'].set_color('white')
	if i >= 2 : ax.spines['bottom'].set_color('white')
	for t in ax.xaxis.get_ticklines(): t.set_color('white')

	es = e_bins[i]
	graph_data = [0 for _ in range(5)]
	totals = [0 for _ in range(5)]

	for j in range(5) : 
		for t in t_bins[j] : 
			for e in es : 
				if (e,t) in pair_data : 
					tr, gold, total = tuple(pair_data[(e,t)])
					graph_data[j] += (gold/total)
					totals[j] += 1 

	graph_data = [v/t for v,t in zip(graph_data, totals)]
	colors = []
	for g in graph_data : 
		if g > 0 : colors.append('r')
		else : colors.append('b')
	ax.bar(np.arange(1,6), graph_data, color=colors)
	ax.axhline(y=0, xmin=0, xmax=6,color='k')
	
	#plt.ylim(-0.4,0.1)
	l = [min(graph_data), (min(graph_data) + max(graph_data)) / 2, max(graph_data)]
	plt.yticks(l,fontsize='large')
	ax.yaxis.set_major_formatter(ticker.FormatStrFormatter('%0.2f'))
	plt.ylabel(r'$\Delta$ TER$_{gold}$', fontsize='large')
	if i < 4 : plt.xticks([])

	#plt.xlabel('Average TER between pre- and post-edit translation')
#	if i == 0 : plt.title(r'Editor $\Delta$ TER'+'\n%s'%(e_bin_range[i]), fontsize='large')
	plt.title(r'Editor $\Delta$ TER$_{gold}$ %s'%(e_bin_range[i]), fontsize='large')
#	else : plt.title(r'%s'%(e_bin_range[i]), fontsize='large')
	#if i == 4 : plt.xticks(np.arange(1,6)+0.5, ['Translator TER %s'%(t_bin_range[ii-1]) for ii in np.arange(1,6)], fontsize='large') #, ha='center') 
	if i == 4 : plt.xticks(np.arange(1,6)+0.5, ['%s'%(t_bin_range[ii-1]) for ii in np.arange(1,6)], fontsize='large') #, ha='center') 
	if i == 4 : plt.xlabel(r'Translation TER$_{gold}$', fontsize='large')
	#plt.title("Translation/e11ditor pairs")
	plt.xlim((1,6))

plt.show()
