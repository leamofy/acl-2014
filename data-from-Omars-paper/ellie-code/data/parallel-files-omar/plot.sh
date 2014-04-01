#!/bin/bash

paste gold1 translation edit editor ids translation-edit.ter diff.ter | python ../../code/figures/editor_scatter.py
#paste editor ids translation-edit.ter diff.ter | python ../../code/figures/editor_bin_histo.py
