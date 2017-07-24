package com.bionlp;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by singhv on 7/9/17.
 */
public class BioNer {
    public static void main(String[] args) throws Exception {
        String baseDir = "/Users/singhv/Desktop/kpmg";
        File model = Paths.get(baseDir, "CDR-ner-model.ser.gz").toFile();
        CRFClassifier classifier = CRFClassifier.getClassifier(model);
        String text = "A newborn with massive tricuspid regurgitation, atrial flutter, congestive heart failure, and a high serum lithium level is described";
        List<List<CoreLabel>> lst = classifier.classify(text);
        for (CoreLabel label : lst.get(0)) {
            System.out.print(label.word() + '/' + label.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
        }
    }

    public static CRFClassifier<?> getClassifier() throws Exception {
        String baseDir = "/Users/singhv/Desktop/kpmg";
        File model = Paths.get(baseDir, "CDR-ner-model.ser.gz").toFile();
        return CRFClassifier.getClassifier(model);
    }
}