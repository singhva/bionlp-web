package com.bionlp;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by singhv on 7/5/17.
 */
public class CDRData {

    public static void main(String[] args) throws Exception {
        String baseDir = "/Users/singhv/Desktop/kpmg";
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(baseDir,"CDR-stanford-test-data.tsv"));
        String currentLine, abstract_ = "", title = "", abstractAndText = "";
        List<String[]> entityLines = new ArrayList<>();
        PTBTokenizer<Word> tokenizer = null;
        BufferedReader fileReader = Files.newBufferedReader(Paths.get(baseDir, "CDR_Data", "CDR.Corpus.v010516", "CDR_TestSet.PubTator.txt"));
        int i = 0;
        int currentLineNum = 0;
        while ((currentLine = fileReader.readLine()) != null) {
            System.out.println("Current line number is: " + currentLineNum++);
            if ("".equals(currentLine.trim())) {
                i = -1;
                if (entityLines.size() > 0) {
                    int temp = 0;
                    String[] entityLine = entityLines.get(temp);
                    int entityStart = Integer.parseInt(entityLine[1]);
                    int entityEnd = Integer.parseInt(entityLine[2]);
                    while (tokenizer.hasNext()) {
                        Word word = tokenizer.next();

                        if ( (entityStart <= word.beginPosition()) && (entityEnd >= word.endPosition()) ) {
                            writer.write(word.word() + "\t" + entityLine[4]);
                            writer.newLine();
                            if (word.endPosition() == entityEnd) {
                                temp++;
                                if (temp >= entityLines.size()) {
                                    entityStart = entityEnd = -1;
                                }
                                else {
                                    entityLine = entityLines.get(temp);
                                    entityStart = Integer.parseInt(entityLine[1]);
                                    entityEnd = Integer.parseInt(entityLine[2]);
                                }

                            }
                        }
                        //if (entityStart >= word.endPosition()) {
                        else {
                            writer.write(word.word() + "\t" + "O");
                            writer.newLine();
                        }
                    }
                }
                entityLines = new ArrayList<>();
            }

            if (i == 0) {
                title = currentLine.split("\\|")[2];
            }

            else if (i == 1) {
                abstract_ = currentLine.split("\\|")[2];
            }

            else if (i == 2) {
                abstractAndText = title + " " + abstract_;
                tokenizer = new PTBTokenizer<>(new StringReader(abstractAndText), new WordTokenFactory(), "splitHyphenated=true");
                String[] line = currentLine.split("\\t");
                if (!"CID".equals(line[1])) {
                    entityLines.add(line);
                }
            }

            else if (i > 2) {
                String[] line = currentLine.split("\\t");
                if (!"CID".equals(line[1])) {
                    entityLines.add(line);
                }
            }
            i++;
        }
        writer.close();
        fileReader.close();
    }

}