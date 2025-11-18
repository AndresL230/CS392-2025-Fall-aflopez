############################################################
JAVA=java
JAVAC=javac
MYCPATH=.:Library.jar
############################################################

all::

############################################################

clean: ; rm -f *~
cleanall: clean
cleanall: ; rm -f *.class

############################################################

all:: \
Assign07_01
Assign07_01: \
Assign07_01.class
Assign07_01.class: \
Assign07_01.java ; \
$(JAVAC) -cp $(MYCPATH) Assign07_01.java

############################################################

all:: \
Assign07_02
Assign07_02: \
Assign07_02.class; \
$(JAVA) -cp $(MYCPATH) Assign07_02
Assign07_02.class: \
Assign07_02.java Assign07_01.class ; \
$(JAVAC) -cp $(MYCPATH) Assign07_02.java

############################################################
