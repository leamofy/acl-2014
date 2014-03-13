#!/usr/bin/perl

# This script uses the EasyTER (which outputs num edits / ref length on a per sentence basis)
# to calculate the pairwise TER scores between the LDC annotators, and between each Turker
# and the LDC annotators.


for($i = 1; $i <=9; $i++) {
    for($j = 1; $j <= 4; $j++) {
	`java -jar bin/EasyTER.jar minimizing_ter/editor.$i minimizing_ter/mt09_urdu_evalset_current_v3-ref.$j.txt | perl bin/divide.perl > minimizing_ter/pairwise_ter.editor$i-ldc$j`;
    }
}

