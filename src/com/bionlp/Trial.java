package com.bionlp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.bionlp.ctgov.ClinicalStudy;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * Servlet implementation class Annotate
 */
@WebServlet("/trial")
public class Trial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Trial.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trial() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/annotate/index.jsp");
		String baseDir = "/Users/singhv/Desktop/kpmg";
		String nctId = request.getParameter("nctId");
		if (fetchTrial(nctId) != null) {
			try {
				ClinicalStudy study = ClinicalTrialUtils.createClinicalStudy(Paths.get(baseDir, nctId + ".xml").toFile());
				String description = study.getDetailedDescription().getTextblock();
				CRFClassifier classifier = BioNer.getClassifier();
				List<List<CoreLabel>> sentences = classifier.classify(description);
				List<Map<String, String>> spans = new ArrayList<>();
		        for (List<CoreLabel> sentence : sentences) {
		        	Map<String, String> span = new HashMap<String, String>();
		            for (CoreLabel label : sentence) {
		            	String entity = label.get(CoreAnnotations.AnswerAnnotation.class);
		            	if (span.containsKey("entity") && span.get("entity").equals(entity)) {
		            		span.put("text", span.get("text") + " " + label.word());
		            	}
		            	else if (span.containsKey("text")) {
		            		spans.add(span);
		            		span = new HashMap<String, String>();
		            		span.put("entity", entity);
		            		span.put("text", label.word());
		            	}
		            	else {
		            		span.put("entity", entity);
		            		span.put("text", label.word());
		            	}
		            }
		            spans.add(span);
		        }
				request.setAttribute("spans", spans);
				request.setAttribute("study", study);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected File fetchTrial(String nctId) {
		String baseDir = "/Users/singhv/Desktop/kpmg";
		Path path = Paths.get(baseDir, nctId + ".xml");
		String baseUrl = "https://clinicaltrials.gov/ct2/show/";
		if (!Files.exists(path)) {
			try {
	            URL website = new URL(baseUrl + nctId + "?displayxml=true");
	            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	            FileOutputStream fos = new FileOutputStream(path.toString());
	            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	            fos.close();
	            rbc.close();
	            return path.toFile();

	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
		}
		else {
			return path.toFile();
		}
	}

}
