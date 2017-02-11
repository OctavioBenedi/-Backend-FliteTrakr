
MAIN_CLASS := Demo
SRCS := *.java
SRCDIR := src/
OBJDIR := objdir/

PWD := $(shell pwd)

##########################
all: clean compile jar

compile: 
	@echo "=== Compiling $(SRCS) on $(OBJDIR) ==="
	@cd $(PWD)/$(SRCDIR) && javac $(SRCS) -d $(PWD)/$(OBJDIR)/.
	
jar:
	@echo "=== Generating jar file ==="
	@cd $(OBJDIR) && echo "Main-Class: $(MAIN_CLASS)" > MANIFEST.MF && jar -cvmf MANIFEST.MF $(PWD)/$(MAIN_CLASS).jar *.class	
	@cat autoexecjar.sh $(MAIN_CLASS).jar > $(MAIN_CLASS).run && chmod +x $(MAIN_CLASS).run 

clean:
	@echo "Cleaning"
	@rm -fv $(OBJDIR)/*
	@rm -fv $(OBJDIR)/MANIFEST.MF
	@rm -f $(MAIN_CLASS).jar
	@rm -f $(MAIN_CLASS).run



