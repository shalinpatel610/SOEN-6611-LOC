SOEN 6611 - Software Measurement Winter 2020

Assignment 1 - Calculating LOC

Included Files
1) pom.xml
2) readme.txt
3) src -> main -> java -> com -> company -> FileCount.java

FileCount.java contains
- 'main' class
- 'checkForDuplicateFile' which accepts file path, returns true if file content is duplicate to previous files.
- 'init' function which accepts path of the folder or file. Calls 'listFiles' function and prints the final result.
- 'listFiles' function accepts path of folder or file, and performs
        - Check for file or folder
        - Check for extension which will tell if a file is java file or not (Total number of files)
        - Check for duplicate files (Unique java files)
        - Check for blank lines (Number of blank lines)
        - Check for comments and code lines (Comment and code lines)

Uses maven for generating executable jar files.

Steps for generating executable jar file
- Go to folder which contains pom.xml
- mvn clean
- mvn install

after that inside target folder executable jar file is generated.

Usage of jar file
$ java -jar assignment-1.0.jar 'path'
