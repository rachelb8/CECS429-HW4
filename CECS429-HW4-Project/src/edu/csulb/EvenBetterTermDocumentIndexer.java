package edu.csulb;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Index;
import cecs429.index.InvertedIndex;
import cecs429.index.Posting;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;
import java.nio.file.Paths;
import java.util.Scanner;


public class EvenBetterTermDocumentIndexer {
	public static void main(String[] args) {
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
		Index invertedIndex = indexCorpus(corpus);

		Scanner inScanner = new Scanner(System.in);
		boolean continueSearch = true;
		while (continueSearch){
			System.out.print("Please enter term to search, or enter \"v\" for vocab: ");
			String userTerm = inScanner.next();
			String query = userTerm;
			query = query.toLowerCase();
			if (!query.equals("quit") && !query.equals("v")) {
				if (invertedIndex.getPostings(query) != null){
					if (invertedIndex.getPostings(query).size() > 0) {
						for (Posting p : invertedIndex.getPostings(query)) {
							System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
						}								
					}
				}	 
				else {
					System.out.println("Not Found.");
				}
			} 
			else if (query.equals("v")) {
				for (String lString : invertedIndex.getVocabulary()) {
					System.out.println(lString);
				}			
			} 
			else {
				continueSearch = false;
			}
		}
		inScanner.close();
	}
	
	private static Index indexCorpus(DocumentCorpus corpus) {
		Iterable<Document> allDocs = corpus.getDocuments();
		BasicTokenProcessor processor = new BasicTokenProcessor();

		InvertedIndex invertedIndex = new InvertedIndex();
		for (Document lDoc : allDocs) {
			EnglishTokenStream eStream = new EnglishTokenStream(lDoc.getContent());
			Iterable<String> eTokens = eStream.getTokens();
			for (String lToken : eTokens) {
				invertedIndex.addTerm(processor.processToken(lToken),lDoc.getId());
			}
		}
		
		return invertedIndex;
	}
}