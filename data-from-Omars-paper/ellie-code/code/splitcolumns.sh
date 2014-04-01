#!/bin/bash

#seg-id traslator_id editor-id translation edit gold1 gold2 gold3 gold4  
echo "Splitting columns"
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 1 > data/parallel-files-omar/ids
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 2 > data/parallel-files-omar/translator
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 3 > data/parallel-files-omar/editor
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 4 > data/parallel-files-omar/translation
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 5 > data/parallel-files-omar/edit
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 6 > data/parallel-files-omar/gold1
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 7 > data/parallel-files-omar/gold2
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 8 > data/parallel-files-omar/gold3
cat data/parallel-files-omar/all.txt | grep -v "N/A" | cut -f 9 > data/parallel-files-omar/gold4

echo "TER edit to golds"
for i in 1 2 3 4; do echo $i; sh code/calculate_ter.sh data/parallel-files-omar/edit  data/parallel-files-omar/gold$i > data/parallel-files-omar/edit-gold$i.ter; done
echo "TER translation to golds"
for i in 1 2 3 4; do echo $i; sh code/calculate_ter.sh data/parallel-files-omar/translation data/parallel-files-omar/gold$i > data/parallel-files-omar/translation-gold$i.ter; done

echo "average TER translation to edit"
paste data/parallel-files-omar/edit-gold*.ter | python code/average_ter.py > data/parallel-files-omar/edit-gold-average.ter
echo "average TER translation to gold"
paste data/parallel-files-omar/translation-gold*.ter | python code/average_ter.py > data/parallel-files-omar/translation-gold-average.ter

echo "TER translation to edit"
sh code/calculate_ter.sh data/parallel-files-omar/translation data/parallel-files-omar/edit > data/parallel-files-omar/translation-edit.ter; 

