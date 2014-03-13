REF_FILE=$1
OUTPUT_FILE=$2

echo $REF_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site04_single_constrained.xml | perl ../bin/get-bleu-score.perl > ../bleu_scores/$OUTPUT_FILE 
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site05_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site07_single_unconstrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site11_FSC_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site11_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site13_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site16_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site18_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site19_FSC_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site19_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site22_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
perl ../bin/mteval-v13a.pl -s ../ref-sets/mt09_urdu_evalset_current_src.xml -r $REF_FILE -t ../systems/site24_single_constrained.xml | perl ../bin/get-bleu-score.perl >> ../bleu_scores/$OUTPUT_FILE
