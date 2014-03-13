The individual reference files in this folder have doc and seg id's that are mismatched with the rest of the files.  The reason is that they seem to have been created based on the doc and seg order found in the source file, whereas (for some reason) the order of the docs and segs in the original reference file is different from the source.

Just to be clear, the original reference file (mt09_urdu_evalset_current_v3-ref.xml) contains all the correct translations, and they all have the correct doc and seg id's, matching up fine with the source file (mt09_urdu_evalset_current_src.xml).  The issue is that the *order* of the docs is different.  And so, when the individual ref files were created, they processed the references sentence by sentence in the order found in the original ref file, but they were assigned doc and seg id's in the order found in the source file, hence causing a mismatch.

Corrected versions of the individual ref files are in the matched\ folder.  These follow the exact order found in the source file (and have correct doc and seg id's).  The individual reference files in this folder should not be used for anything.  (Except perhaps scoring one reference set against the others, since they are all mismatched synchronously.)

Note: the *.minus-?.xml files are fine as far as doc and seg id's.  Like the original ref file though, the order of the documents is different.

O.Z.
