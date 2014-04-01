#!/bin/python

import os
import sys
import csv
import gzip

#"HITId","HITTypeId","Title","Description","Keywords","Reward","CreationTime","MaxAssignments","RequesterAnnotation","AssignmentDurationInSeconds","AutoApprovalDelayInSeconds","Expiration","NumberOfSimilarHITs","LifetimeInSeconds","AssignmentId","WorkerId","AssignmentStatus","AcceptTime","SubmitTime","AutoApprovalTime","ApprovalTime","RejectionTime","RequesterFeedback","WorkTimeInSeconds","Input.document","Input.lineNum","Input.numSegs","Input.seg1","Input.seg2","Input.seg3","Input.seg4","Input.seg5","Input.seg6","Input.seg7","Input.seg8","Input.seg9","Input.seg10","Input.seg11","Input.seg12","Input.seg13","Input.seg14","Input.seg15","Input.seg16","Input.seg17","Input.seg18","Input.seg19","Input.seg20","Answer.seg5","Answer.seg12","Answer.comment","Answer.translation14","Answer.seg6","Answer.seg13","Answer.translation15","Answer.seg7","Answer.where_learned","Answer.seg14","Answer.translation1","Answer.how_long","Answer.translation16","Answer.seg8","Answer.seg15","Answer.translation2","Answer.translation17","Answer.what_aid","Answer.seg9","Answer.seg16","Answer.translation3","Answer.native_language","Answer.translation18","Answer.usedMT","Answer.seg17","Answer.translation4","Answer.translation19","Answer.seg1","Answer.seg18","Answer.translation5","Answer.where_live","Answer.translation20","Answer.seg2","Answer.seg19","Answer.translation6","Answer.translation10","Answer.seg3","Answer.translation7","Answer.seg20","Answer.translation11","Answer.seg4","Answer.seg10","Answer.translation8","Answer.translation12","Answer.how_long_English","Answer.seg11","Answer.translation9","Answer.translation13","Approve","Reject"

root_dir = sys.argv[1]
for f in os.listdir(root_dir) :
	for line in csv.DictReader(gzip.open('%s/%s'%(root_dir,f))) : 
		worker = line['WorkerId']
		doc = line['Input.document']
		lineNum = line['Input.lineNum']
		for i in range(1,int(line['Input.numSegs'])) : 
			translation = line['Answer.translation%d'%i]
			print '%s\t%s\t%s\t%s'%(doc, i, worker, translation)
