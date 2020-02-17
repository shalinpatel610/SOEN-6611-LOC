package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileCount {

    int javaFiles = 0, uniqueJavaFiles = 0, blankLines = 0, commentLines = 0, codeLines = 0;
    ArrayList<File> fileContent = new ArrayList<>();

    public void init(String folder) throws IOException {
        listFiles(folder);
        System.out.println(javaFiles+"-"+uniqueJavaFiles+"-"+blankLines+"-"+commentLines+"-"+codeLines);
    }

    public void listFiles(String folder) throws IOException {
        File directory = new File(folder);
        /*
            Check if file is a directory or file
         */
        if (directory.isDirectory()) {

            File[] contents = directory.listFiles();
            assert contents != null;
            /*
                Content of folder
             */
            for (File f : contents) {
                if (f.isFile()) {
                    /*
                        Check for extension of file
                     */
                    String extension;
                    int i = f.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = f.getName().substring(i+1);
                        if (extension.equals("java")){
                            javaFiles++;
                            /*
                                Check for duplicate files
                             */
                            boolean isDuplicate = false;
                            try {
                                isDuplicate = checkForDuplicateFile(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (isDuplicate){

                            } else{
                                uniqueJavaFiles++;
                                /*
                                    Check for blank lines
                                 */
                                BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    if (line.trim().isEmpty()) {
                                        blankLines++;
                                    } else {
                                        /*
                                            Check for commented lines
                                         */
                                        if (line.trim().startsWith("//")){
                                            commentLines++;
                                        } else if (line.trim().startsWith("/*") && line.trim().endsWith("*/")){
                                            commentLines++;
                                        } else if (line.trim().startsWith("/*") && line.trim().contains("*/") && !line.trim().endsWith("*/")){
                                            codeLines++;
                                        } else if(line.trim().startsWith("/*") && !line.trim().contains("*/")){
                                            commentLines++;
                                            while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
                                                commentLines++;
                                            }
                                            if (line.trim().contains("*/") && !line.trim().endsWith("*/")){
                                                codeLines++;
                                            } else {
                                                commentLines++;
                                            }
                                        } else if (line.contains("/*") && !line.trim().startsWith("/*")){
                                            if (line.contains("*/")){
                                                codeLines++;
                                            } else {
                                                codeLines++;
                                                while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
                                                    commentLines++;
                                                }
                                                commentLines++;
                                            }
                                        } else {
                                            codeLines++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    listFiles(f.getPath());
                }
            }
        } else if (directory.isFile()){
            javaFiles++;
            uniqueJavaFiles++;
            /*
                Check for extension of file
             */
            String extension;
            int i = directory.getName().lastIndexOf('.');
            if (i > 0) {
                extension = directory.getName().substring(i+1);
                if (extension.equals("java")){
                    /*
                        Check for blank lines
                     */
                    BufferedReader br = new BufferedReader(new FileReader(directory.getPath()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                            blankLines++;
                        } else {
                            /*
                                Check for commented lines
                             */
                            if (line.trim().startsWith("//")){
                                commentLines++;
                            } else if (line.trim().startsWith("/*") && line.trim().endsWith("*/")){
                                commentLines++;
                            } else if (line.trim().startsWith("/*") && line.trim().contains("*/") && !line.trim().endsWith("*/")){
                                codeLines++;
                            } else if(line.trim().startsWith("/*") && !line.trim().contains("*/")){
                                commentLines++;
                                while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
                                    commentLines++;
                                }
                                if (line.trim().contains("*/") && !line.trim().endsWith("*/")){
                                    codeLines++;
                                } else {
                                    commentLines++;
                                }
                            } else if (line.contains("/*") && !line.trim().startsWith("/*")){
                                if (line.contains("*/")){
                                    codeLines++;
                                } else {
                                    codeLines++;
                                    while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
                                        commentLines++;
                                    }
                                    commentLines++;
                                }
                            } else {
                                codeLines++;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkForDuplicateFile(File f) throws IOException {
        boolean isDuplicate = false;
        if (fileContent.size() != 0) {
            for (File file : fileContent) {
                if (Files.mismatch(Paths.get(file.getPath()), Paths.get(f.getPath())) != -1){
                    isDuplicate = false;
                } else {
                    isDuplicate = true;
                    break;
                }
            }
        }
        if (!isDuplicate){
            fileContent.add(f);
        }
        return isDuplicate;
    }

    public static void main(String[] args) throws IOException {
        new FileCount().init(args[0]);
    }
}