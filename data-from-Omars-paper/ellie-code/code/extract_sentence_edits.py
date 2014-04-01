#!/bin/python

import os
import sys
import csv
import gzip

#"HITId","HITTypeId","Title","Description","Keywords","Reward","CreationTime","MaxAssignments","RequesterAnnotation","AssignmentDurationInSeconds","AutoApprovalDelayInSeconds","Expiration","NumberOfSimilarHITs","LifetimeInSeconds","AssignmentId","WorkerId","AssignmentStatus","AcceptTime","SubmitTime","AutoApprovalTime","ApprovalTime","RejectionTime","RequesterFeedback","WorkTimeInSeconds","Input.lineNum1","Input.sentence1","Input.lineNum2","Input.sentence2","Input.lineNum3","Input.sentence3","Input.lineNum4","Input.sentence4","Input.lineNum5","Input.sentence5","Input.lineNum6","Input.sentence6","Input.lineNum7","Input.sentence7","Input.lineNum8","Input.sentence8","Input.lineNum9","Input.sentence9","Input.lineNum10","Input.sentence10","Answer.comment","Answer.edited2","Answer.edited3","Answer.edited4","Answer.edited5","Answer.edited6","Answer.edited7","Answer.edited8","Answer.edited9","Answer.edited10","Answer.edited1","Answer.incomprehensible","Approve","Reject"

root_dir = sys.argv[1]
for f in os.listdir(root_dir) :
	for line in csv.DictReader(gzip.open('%s/%s'%(root_dir,f))) : 
		worker = line['WorkerId']
		for i in range(1,10) : 
			translation = line['Input.sentence%d'%i]
			edit = line['Answer.edited%d'%i]
			print '%s\t%s\t%s'%(worker, translation, edit)
