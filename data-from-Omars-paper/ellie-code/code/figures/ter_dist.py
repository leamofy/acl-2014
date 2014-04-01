#!/bin/python
import csv
import math
import operator
import itertools
import numpy as np
import sys
import pylab as plt
from scipy import stats 

graph_data = []

for line in sys.stdin : 
	t, e = line.strip().split()
	t = float(t)
	e = float(e)
	if t - e == 0 : continue
	if e > 1.5 : e = 1.5
	if t > 1.5 : t = 1.5
	#if sys.argv[1] == 'Unedited' : graph_data.append([float(t)])
	#elif sys.argv[1] == 'Edited' : graph_data.append([float(e)])
	graph_data.append([float(t), float(e)])
	#graph_data.append([t - e])

x = np.array(graph_data) 

n, bins, patches = plt.hist(x, 15, normed=1, histtype='bar', label=['Unedited', 'Edited'])
plt.xlabel('TER against gold standard translation')
plt.ylabel('# of translations')
plt.legend()
plt.show()

