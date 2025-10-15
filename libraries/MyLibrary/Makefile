########################(beg of [Makefile])########################

clean:: ; rm -f */*~

cleanall:: clean
cleanall:: ; rm -f */*.class

###################################################################

compall:: FnGseq
compall:: FnInt1
compall:: FnStrn
compall:: FnList
compall:: FnA1sz
compall:: FnTuple
compall:: MyRefer
compall:: MyStack
compall:: MyQueue
compall:: FnGtree
compall:: BinSearch

###################################################################

FnGseq:: ; make -C FnGseq comp
FnStrn:: ; make -C FnStrn comp
FnStrn:: ; make -C FnInt1 comp
FnList:: ; make -C FnList comp
FnA1sz:: ; make -C FnA1sz comp
FnTuple:: ; make -C FnTuple comp

###################################################################

FnGseq:: ; make -C LnGseq comp
FnList:: ; make -C LnList comp

###################################################################

MyStack:: ; make -C MyRefer comp
MyStack:: ; make -C MyStack comp
MyQueue:: ; make -C MyQueue comp

###################################################################

FnGtree:: ; make -C FnGtree comp

###################################################################

BinSearch:: ; make -C BinSearch comp

###################################################################L

ROOT=./..

Library_jar: ; \
jar -cvf Library.jar \
$(ROOT)/Library/FnGseq/*.class \
$(ROOT)/Library/LnGseq/*.class \
$(ROOT)/Library/FnInt1/*.class \
$(ROOT)/Library/FnStrn/*.class \
$(ROOT)/Library/FnList/*.class \
$(ROOT)/Library/LnList/*.class \
$(ROOT)/Library/FnA1sz/*.class \
$(ROOT)/Library/FnTuple/*.class \
$(ROOT)/Library/MyRefer/*.class \
$(ROOT)/Library/MyStack/*.class $(ROOT)/Library/MyQueue/*.class \
$(ROOT)/Library/FnGtree/*.class $(ROOT)/Library/BinSearch/*.class

###################################################################

########################(end of [Makefile])########################
