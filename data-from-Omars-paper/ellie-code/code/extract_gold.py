#!/bin/python

import sys
from nltk.tokenize import word_tokenize
import xml.etree.ElementTree as ET

tree = ET.parse(sys.argv[1])
root = tree.getroot()

for refset in root: 
	for doc in refset : 
		for seg in doc : 
			print '%s\t%s\t%s\t%s'%(refset.get('setid'), doc.get('docid'), seg.get('id'), seg.text.encode('ascii', 'ignore'))
