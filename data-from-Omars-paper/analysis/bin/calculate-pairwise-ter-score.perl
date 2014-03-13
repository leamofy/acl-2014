#!/usr/bin/perl

# This script uses the EasyTER (which outputs num edits / ref length on a per sentence basis)
# to calculate the pairwise TER scores between the LDC annotators, and between each Turker
# and the LDC annotators.

for($i = 1; $i <=4; $i++) {
    for($j = $i+1; $j <= 4; $j++) {
#	`java -jar bin/EasyTER.jar minimizing_ter/mt09_urdu_evalset_current_v3-ref.$i.txt minimizing_ter/mt09_urdu_evalset_current_v3-ref.$j.txt | perl bin/divide.perl > minimizing_ter/pairwise_ter.ldc$i-ldc$j`;
    }
}


for($i = 4; $i <=5; $i++) {
    for($j = 1; $j <= 4; $j++) {
#	`java -jar bin/EasyTER.jar minimizing_ter/turker.$i minimizing_ter/mt09_urdu_evalset_current_v3-ref.$j.txt | perl bin/divide.perl > minimizing_ter/pairwise_ter.turker$i-ldc$j`;
    }
}

for($i = 1; $i <=5; $i++) {
    for($j = 1; $j <= 5; $j++) {
	if($j != $i) {
	    `java -jar bin/EasyTER.jar minimizing_ter/turker.$i minimizing_ter/turker.$j | perl bin/divide.perl > minimizing_ter/pairwise_ter.turker$i-turker$j`;
	}
    }
}

