from urllib import quote_plus, unquote_plus
from wt_articles.splitting import determine_splitter
from pyango_view import str2img
import wikipydia
import re
import wpTextExtractor
import goopytrans
import time
import os
import string
import codecs

target_lang = 'en'

# URDU
lang = 'ur'
fontName='Nafees'
set_label = 'nist-mt-eval-2009-urdu-test-set'
date_time_str = time.strftime("%Y-%m-%dT%H%M")
path = '/Users/ccb/Documents/Projects/NIST-MT-Eval-2009/MTurk-Translation-take2/%s/%s/' % (lang, date_time_str)

img_output_dir = path + 'txt_img'
img_url_dir = 'http://cs.jhu.edu/~ccb/wikitrans/%s/%s/txt_img' % (lang, date_time_str)

csv_filename = path + set_label + "-" + date_time_str + ".csv"
sentence_filename = '/Users/ccb/Documents/Projects/NIST-MT-Eval-2009/MTurk-Translation-take2/nist09.ur'
translation_filename = '/Users/ccb/Documents/Projects/NIST-MT-Eval-2009/MTurk-Translation-take2/nist09.en.0'
num_sentences_per_hit = 10


if not os.path.exists(path):
    os.makedirs(path)

if not os.path.exists(img_output_dir):
    os.makedirs(img_output_dir)





def write_lines_to_file(output_filename, lines):
    """
    Appends a list of lines to file.   
    """
    output_file = open(output_filename, 'a')
    for line in lines:
        output_file.write(line.encode('UTF-8'))
        output_file.write('\n'.encode('UTF-8'))            
    output_file.close()
    return lines


def read_lines_from_file(filename):
   """
   Reads a file in utf8 encoding into an array
   """
   lines = []
   input_file = codecs.open(filename, encoding='utf-8')
   for line in input_file:
      lines.append(line.rstrip('\n'))
   input_file.close()
   return lines


def generate_images(sentence_filename, output_dir, img_url_dir, fontName='Nafees'):
   """
   Saves image representations of the sentences to the output dir.
   The file name contains the article ID and the segment number.
   """
   sentences = read_lines_from_file(sentence_filename)
   seg_ids = read_lines_from_file(sentence_filename + '.seg_ids')
   img_urls = []
   width = 350
   extension = 'png'
   # create a placeholder file in case we have an odd number of segments
   dnt_filename = output_dir + '/do-not-translate.png'
   retval = str2img("Do not translate this sentence.", font=fontName, output=dnt_filename, width=width)
   # create images for all sentences
   for i, sentence in enumerate(sentences):
       seg_id = seg_ids[i]
       sentence = format_for_image(sentence)
       img_outputfile = '%s/%s.%s' % (output_dir, seg_id, extension)
       #retval = str2img(sentence, font=fontName, output=img_outputfile, width=width)
       img_url = '%s/%s.%s' % (img_url_dir, seg_id, "png")
       img_urls.append(img_url)
   return img_urls





def format_for_image(string):
    """
    Cleans up the string so that it can be better rendered as an image.
    """
    string = string.replace("&nbsp;", " ")
    string = string.replace("&#160;", " ")
    # double quotes have to be converted to to two single quotes, 
    # because the image to string converter wraps the string in double quotes
    string = string.replace('"', "''")
    return string


def format_for_csv(string):
    """
    Replaces special characters used by comma separated value (CSV) files
    with their HTML equivalents.
    """
    string = string.strip()
    string = string.replace('\n', ' ')
    string = string.replace('&', "&amp;")
    string = string.replace(',', "&#44;")
    string = string.replace('>', "&gt;")
    string = string.replace('<', "&lt;")
    string = string.replace('"', "&quot;")
    string = string.replace("'", "&#39;")
    return string





def write_csv_file(csv_filename, sentence_filename, translation_filename, lang, num_sentences_per_hit, img_output_dir, img_url_dir, fontName='Nafees', target_lang='en'):
   """
   Generates a comma seperated value file and associated image files 
   so that a Mechanical Turk translation HIT can be created.
   """

   # generate all images
   img_urls = generate_images(sentence_filename, img_output_dir, img_url_dir, fontName)
   #
   csv_output_file = open(csv_filename, 'w')
   header = 'lang_pair'
   for i in range(1, num_sentences_per_hit+1):
      header += ',seg_id%s' % str(i)
      header += ',tag%s' % str(i)
      header += ',seg%s' % str(i)
      header += ',img_url%s' % str(i)
      header += ',machine_translation%s' % str(i)
   #
   # load the sentences
   seg_ids = read_lines_from_file(sentence_filename + '.seg_ids')
   tags =  read_lines_from_file(sentence_filename + '.tags')
   sentences = read_lines_from_file(sentence_filename)
   translations = read_lines_from_file(translation_filename)
   #
   line = header
   counter = 0
   for i, sentence in enumerate(sentences):
      if counter % num_sentences_per_hit == 0 :
         csv_output_file.write(line.encode('UTF-8'))
         csv_output_file.write('\n'.encode('UTF-8'))
         line = lang + "-" + target_lang               
      counter += 1
      seg_id = seg_ids[i]
      tag = tags[i]
      sentence = format_for_csv(sentence)
      img_url = img_urls[i]
      translation = format_for_csv(translations[i])
      line += ',%s,%s,%s,%s,%s' % (seg_id, tag, sentence, img_url, translation)
   # if there are an odd number of sentences, then fill out the rest of the fields with a do-not-translate message
   if not counter % num_sentences_per_hit == 0:
      dnt_url = ",,,," + img_url_dir + "/do-not-translate.png,"
      line = dnt_url * (num_sentences_per_hit - counter)
      csv_output_file.write(line.encode('UTF-8'))
   csv_output_file.write('\n'.encode('UTF-8'))
   csv_output_file.close()



write_csv_file(csv_filename, sentence_filename, translation_filename, lang, num_sentences_per_hit, img_output_dir, img_url_dir, fontName, target_lang)
