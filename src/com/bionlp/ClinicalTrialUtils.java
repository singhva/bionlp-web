package com.bionlp;

import com.bionlp.ctgov.ClinicalStudy;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by singhv on 7/10/17.
 */
public class ClinicalTrialUtils {

    public static void main(String[] args) throws Exception {
        String baseDir = "/Users/singhv/Desktop/kpmg";
        File xml = Paths.get(baseDir, "NCT00428922.xml").toFile();
        ClinicalStudy study = createClinicalStudy(xml);
        String description = study.getDetailedDescription().getTextblock();
        CRFClassifier classifier = BioNer.getClassifier();
        List<List<CoreLabel>> sentences = classifier.classify(description);
        for (List<CoreLabel> sentence : sentences) {
            for (CoreLabel label : sentence) {
                System.out.print(label.word() + '/' + label.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
            }
        }
    }

    public static ClinicalStudy createClinicalStudy(File xml) throws FileNotFoundException, JAXBException {
        if (!xml.exists()) throw new FileNotFoundException();
        try {
            String instancePath = "com.bionlp.ctgov";
            JAXBContext jc = JAXBContext.newInstance(instancePath);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(xml);
            ClinicalStudy study = (ClinicalStudy) obj;
            return study;
        } catch (Exception e) {
            System.err.println("Caught exception: " + e);
            throw e;
        }
    }
}