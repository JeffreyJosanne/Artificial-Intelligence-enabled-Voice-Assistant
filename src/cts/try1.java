package cts;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;


public class try1 {


	public static void main(String[] args) {
		try {
			//Sentence();
			//Tokenize();
			POSTag();
			//findName();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
			
			
			public static void Sentence()throws InvalidFormatException,
	IOException  {

	String paragraph = "This. isn't the greatest example sentence. in the world because I've seen better.  Neither is this one.  This one's not bad, though.";
	InputStream is = new FileInputStream("en-sent.bin");
	SentenceModel model = new SentenceModel(is);
	SentenceDetectorME sdetector = new SentenceDetectorME(model);
 
	String sentences[] = sdetector.sentDetect(paragraph);
 
	System.out.println(sentences[0]);
	System.out.println(sentences[1]);
	is.close();

		
	}
	public static void Tokenize(String str1) throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream("en-token.bin");
	 
		TokenizerModel model = new TokenizerModel(is);
	 
		Tokenizer tokenizer = new TokenizerME(model);
	 
		String tokens[] = tokenizer.tokenize(str1);
	 
		for (String a : tokens)
			System.out.println(a);
	 
		is.close();
	}
	public static void findName() throws IOException {
		InputStream is = new FileInputStream("en-ner-person.bin");
	 
		TokenNameFinderModel model = new TokenNameFinderModel(is);
		is.close();
	 
		NameFinderME nameFinder = new NameFinderME(model);
	 
		String []sentence = new String[]{
			    "John",
			    "Bella",
			    "is",
			    "a",
			    "good",
			    "person"
			    };
	 
			Span nameSpans[] = nameFinder.find(sentence);
	 
			//for(Span s: nameSpans)
				System.out.println(nameSpans[0].toString());			
	}
	
	public static void POSTag() throws IOException {
		POSModel model = new POSModelLoader()	
			.load(new File("en-pos-maxent.bin"));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "");
		POSTaggerME tagger = new POSTaggerME(model);
	 
		String input = "He a is great guy";
		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));
	 
		perfMon.start();
		String line;
		while ((line = lineStream.read()) != null) {
	 
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);
	 
			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			Tokenize(sample.toString());
	 
			perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
	}

}
