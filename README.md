# Overview

An implementation for backend-flitetrak

## How to compile
Must have java version 8 or openjdk version 1.8.0

Navigate to main folder and check all files are in place.

```
.
├── autoexecjar.sh
├── Makefile
├── objdir
├── src
│   ├── AbstractKShortestPathFinder.java
│   ├── DefaultKShortestPathFinder.java
│   ├── Demo.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── ParseInput.java
│   ├── Path.java
│   └── Test.java
├── test2.txt
├── test3.txt
├── test_diagram.svg
└── test.txt

2 directories, 14 files
```

### Using make

If make package is present on the system just execute 

```
make
```

to compile it. 

make will generate files Demo.jar and Demo.run

To clean .class, .jar and .run files just execute 

```
make clean
```

```
.
├── autoexecjar.sh
├── Demo.jar
├── Demo.run
├── Makefile
├── objdir
│   ├── AbstractKShortestPathFinder.class
│   ├── DefaultKShortestPathFinder.class
│   ├── Demo.class
│   ├── Edge.class
│   ├── Graph.class
│   ├── MANIFEST.MF
│   ├── ParseInput.class
│   ├── Path$1.class
│   ├── Path.class
│   ├── Path$NonEmptyPath.class
│   └── Test.class
├── src
│   ├── AbstractKShortestPathFinder.java
│   ├── DefaultKShortestPathFinder.java
│   ├── Demo.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── ParseInput.java
│   ├── Path.java
│   └── Test.java
├── test2.txt
├── test3.txt
├── test_diagram.svg
└── test.txt

2 directories, 27 files
```

### Compile manually

On main folder execute:

```
cd src
javac *.java -d ../objdir/.
cd ../objdir
echo "Main-Class: Demo" > MANIFEST.MF && jar -cvmf MANIFEST.MF ../Demo.jar *.class
cd ..
cat autoexecjar.sh Demo.jar > Demo.run && chmod +x Demo.run 

```

## How to run

To run the solution, just execute in shell

```sh
./Demo.run < test.txt
```

Or 

```
java -jar Demo.jar < test.txt
```

Also corrected version of commands present in test.txt are available on test3.txt

