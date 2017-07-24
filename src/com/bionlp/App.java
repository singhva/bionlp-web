package com.bionlp;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.LexedTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;

import java.io.StringReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String text = "Ketanserin pretreatment reverses alfentanil-induced muscle rigidity.";
        LexedTokenFactory<Word> factory = new WordTokenFactory();
        PTBTokenizer<Word> tokenizer = new PTBTokenizer(new StringReader(text), new CoreLabelTokenFactory(), "splitHyphenated=true");
        while (tokenizer.hasNext()) {
            System.out.println(tokenizer.next());
        }

    }
}