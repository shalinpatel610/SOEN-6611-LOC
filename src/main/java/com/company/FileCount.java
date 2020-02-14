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
    ArrayList<File> fileContent = new ArrayList<File>();

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
                Content of file
             */
            for (File f : contents) {
                if (f.isFile()) {
                    //System.out.println(f.getAbsolutePath());
                    /*
                        Check for extension of file
                     */
                    String extension;
                    int i = f.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = f.getName().substring(i+1);
                        if (extension.endsWith("java")){
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
                                //System.out.println("File Duplicate");
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
                                        } else if (line.trim().startsWith("/*")){
                                            commentLines++;
                                            while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
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
            /*
                Check for extension of file
             */
            String extension;
            int i = directory.getName().lastIndexOf('.');
            if (i > 0) {
                extension = directory.getName().substring(i+1);
                if (extension.endsWith("java")){
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
                                //System.out.println(line);
                                commentLines++;
                            } else if (line.trim().startsWith("/*")){
                                //System.out.println(line);
                                commentLines++;
                                while (!line.trim().contains("*/") && !(line = br.readLine()).trim().contains("*/")){
                                    //System.out.println(line);
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
                    //System.out.println("File Content Not Same");
                    isDuplicate = false;
                } else {
                    //System.out.println("File Content Same");
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
        //System.out.println("Directory Path: "+args[0]);
        new FileCount().init(args[0]);
    }

}

