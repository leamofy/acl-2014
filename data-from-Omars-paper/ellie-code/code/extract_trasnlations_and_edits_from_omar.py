#!/bin/python

import os
import sys
import csv
import gzip

#segIndex        segID   src     ref1    ref2    ref3    ref4    turkTranslatorID_1      turkTranslation_1       turkTranslatorID_2      turkTranslation_2       turkTranslatorID_3 turkTranslation_3       turkTranslatorID_4      turkTranslation_4       turkEditorID_1_1        turkEdit_1_1    turkEditorID_1_2        turkEdit_1_2    turkEditorID_1_3 turkEdit_1_3    turkEditorID_2_1        turkEdit_2_1    turkEditorID_2_2        turkEdit_2_2    turkEditorID_2_3        turkEdit_2_3    turkEditorID_3_1        turkEdit_3_1 turkEditorID_3_2        turkEdit_3_2    turkEditorID_3_3        turkEdit_3_3    turkEditorID_4_1        turkEdit_4_1 

#doc_ids		edit		edit_ter	editor_ids	gold		seg_ids		translation	translation_ter	translator_ids

raw_file = sys.argv[1]
for line in csv.DictReader(open(raw_file), delimiter='\t') : 
	if line['segID'] == 'urd-NG-102-174502-11516125-post26__4' : continue
	segId = line['segID']
	ref1 = line['ref1']; ref2 = line['ref2']; ref3 = line['ref3']; ref4 = line['ref4'];
	for translator in [1,2,3,4] : 
		for editor in [1,2,3,4] : 
			try : 
				tid = line['turkTranslatorID_%d'%translator]
				translation = line['turkTranslation_%d'%translator]
				eid = line['turkEditorID_%d_%d'%(translator, editor)]
				edit= line['turkEdit_%d_%d'%(translator,editor)]
				if tid is None or translation is None or eid is None or edit is None : continue
				if len(eid.split()) > 1 : continue
				translation = translation.replace('\t',' ')
				edit= edit.replace('\t',' ')
				#each line contains seg-id traslator_id editor-id translation edit gold1 gold2 gold3 gold4  
				print '\t'.join([segId, tid, eid, translation,edit,ref1,ref2,ref3,ref4])
				#except TypeError : continue
			except KeyError : continue
