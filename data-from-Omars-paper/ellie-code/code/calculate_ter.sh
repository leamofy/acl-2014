#!/bin/bash

java -jar ../analysis/bin/EasyTER.jar $1 $2 | python code/ter.py #-c "exec(\"import sys\\nfor line in sys.stdin : print (float(line.split('/')[0]) / float(line.strip().split('/')[1]))\")" 
