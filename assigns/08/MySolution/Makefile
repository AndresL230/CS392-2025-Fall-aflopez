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
Assign08_01
Assign08_01: \
Assign08_01.class; \
$(JAVA) -cp $(MYCPATH) Assign08_01
Assign08_01.class: \
Assign08_01.java ; \
$(JAVAC) -cp $(MYCPATH) Assign08_01.java

############################################################

all:: \
Assign08_02
Assign08_02: \
Assign08_02.class; \
$(JAVA) -cp $(MYCPATH) Assign08_02
Assign08_02.class: \
Assign08_02.java ; \
$(JAVAC) -cp $(MYCPATH) Assign08_02.java

############################################################
