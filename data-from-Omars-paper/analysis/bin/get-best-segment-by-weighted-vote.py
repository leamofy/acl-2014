import csv
import operator
import codecs

filename = 'selection-by-turkers/choose-best-translation-Batch_250771_result.csv'
csv_reader = csv.reader(open(filename))

header_index = {}
for i, header in enumerate(csv_reader.next()):
   header_index[header] = i

hits = []
for hit in csv_reader:
   hits.append(hit)

# Check how often the judges pass the control
# to see how much we should weight their vote
# for the best translator
hit = hits[0]
control_header_id = 'Answer.best_translation_' + str(hit[header_index['Input.control_id']])
amount_worked = {}
controls_passed = {}
for hit in hits:
   judge_id = hit[header_index['WorkerId']]
   num_hits = 0
   if judge_id in amount_worked:
      num_hits = amount_worked[judge_id]
   control = hit[header_index[control_header_id]]
   amount_worked[judge_id] = num_hits + 1
   if control == 'positive':
      num_controls_passed = 0
      if judge_id in controls_passed:
         num_controls_passed = controls_passed[judge_id]
      controls_passed[judge_id] = num_controls_passed + 1
judge_weight = {}
for judge_id in amount_worked.keys():
   num_correct = 0
   if judge_id in controls_passed:
      num_correct = controls_passed[judge_id]
   num_assignments = amount_worked[judge_id]
   weight = float(num_correct) / float(num_assignments)
   judge_weight[judge_id] = weight


# Iterate through all non-control segments
# and collect statistics about the weighted
# vote for each translator

# We use two maps:
# seg_id --> translator --> weighted vote
# seg_id --> cumulative vote weight of judges
per_segment_weighted_votes = {}
per_segment_weight_totals = {}
all_translators = {}
for hit in hits:
   num_segs = int(hit[header_index['Input.num_segs']])
   judge_id = hit[header_index['WorkerId']]
   weight = judge_weight[judge_id]
   for i in range(1, num_segs):
      seg_id = hit[header_index['Input.seg_id_' + str(i)]]
      if not seg_id in per_segment_weighted_votes:
         per_segment_weighted_votes[seg_id] = {}
         per_segment_weight_totals[seg_id] = 0
      selected_translator = hit[header_index['Answer.best_translation_' + str(i)]]
      for j in range(1, 5):
         translator = hit[header_index['Input.translator' + str(i) + '_' + str(j)]]
         # filter out the misalignments when keeping track of annotators
         if len(translator) <= 14 and not translator == "" and not translator == 'negative' and not translator == 'positive':
            all_translators[translator] = 1
         if not translator in per_segment_weighted_votes[seg_id]:
            per_segment_weighted_votes[seg_id][translator] = 0            
         if translator == selected_translator:
            current_vote_total = per_segment_weighted_votes[seg_id][selected_translator]
            per_segment_weighted_votes[seg_id][selected_translator] = current_vote_total + weight
            total = per_segment_weight_totals[seg_id]
            per_segment_weight_totals[seg_id] = total + weight


seg_ids = {}
for hit in hits:
   for i in range(1, num_segs):
      seg_id = hit[header_index['Input.seg_id_' + str(i)]]
      if not seg_id in seg_ids:
         seg_ids[seg_id] = 1
         best_score = 0
         best_translator = ''
         best_translation = ''
         for j in range(1, 5):
            translator = hit[header_index['Input.translator' + str(i) + '_' + str(j)]]
            translation = hit[header_index['Input.translation' + str(i) + '_' + str(j)]]
            score = per_segment_weighted_votes[seg_id][translator] 
            if score > best_score and translator in all_translators:
               best_translator = translator
               best_score = score
               best_translation = translation
         line = seg_id + '\t' + best_translator + '\t' + str(best_score)
         print line.encode('utf8')

