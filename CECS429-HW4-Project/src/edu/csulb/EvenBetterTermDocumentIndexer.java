package edu.csulb;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Index;
import cecs429.index.InvertedIndex;
import cecs429.index.Posting;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;
import cecs429.index.TermDocumentIndex;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;


public class EvenBetterTermDocumentIndexer {
	public static void main(String[] args) {
		InvertedIndex invertedInd = new InvertedIndex();
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
		Iterable<Document> allDocs = corpus.getDocuments();
		BasicTokenProcessor processor = new BasicTokenProcessor();

		
		for (Document lDoc : allDocs) {
			EnglishTokenStream eStream = new EnglishTokenStream(lDoc.getContent());
			Iterable<String> eTokens = eStream.getTokens();
			for (String lToken : eTokens) {
				invertedInd.addTerm(processor.processToken(lToken),lDoc.getId());
			}
		}

		Scanner inScanner = new Scanner(System.in);
		boolean continueSearch = true;
		while (continueSearch){
			System.out.print("Please enter term to search, or enter \"v\" for vocab: ");
			String userTerm = inScanner.next();
			String query = userTerm;
			query = query.toLowerCase();
			if (!query.equals("quit") && !query.equals("v")) {
				if (invertedInd.getPostings(query) != null){
					if (invertedInd.getPostings(query).size() > 0) {
						for (Posting p : invertedInd.getPostings(query)) {
							System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
						}								
					}
				}	 
				else {
					System.out.println("Not Found.");
				}
			} 
			else if (query.equals("v")) {
				for (String lString : invertedInd.getVocabulary()) {
					System.out.println(lString);
				}			
			} 
			else {
				continueSearch = false;
			}
		}
		/*
		Iterable<String> testList = invertedInd.getVocabulary();
		for (String lString : testList) {
			System.out.print(lString + "; ");
			for (Posting lPosting: invertedInd.getPostings(lString))  {
				System.out.print(lPosting.getDocumentId());
			}			
			System.out.println();
		}
		*/

		/*
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
		Index index = indexCorpus(corpus) ;
		// We aren't ready to use a full query parser; for now, we'll only support single-term queries.
	
		Scanner inScanner = new Scanner(System.in);
		boolean continueSearch = true;
		while (continueSearch){
			System.out.print("Please enter term to search : ");
			String userTerm = inScanner.next();
			String query = userTerm;
			
			if (!query.equals("quit")) {
				if (index.getPostings(query).size() > 0) {
					for (Posting p : index.getPostings(query)) {
						System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
					}
				} else {
					System.out.println("Not Found.");
				}
			} else {
				continueSearch = false;
			}
		}*/
	}
	
	private static Index indexCorpus(DocumentCorpus corpus) {
		HashSet<String> vocabulary = new HashSet<>();
		BasicTokenProcessor processor = new BasicTokenProcessor();
		
		// First, build the vocabulary hash set.
		
		// TODO:
		// Get all the documents in the corpus by calling GetDocuments().
		// Iterate through the documents, and:
		// Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
		// Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
		//		and adding them to the HashSet vocabulary.
		
		Iterable<Document> documents = corpus.getDocuments();
		for (Document lDoc : documents) {
			EnglishTokenStream eStream = new EnglishTokenStream(lDoc.getContent());
			Iterable<String> eTokens = eStream.getTokens();
			for (String lToken : eTokens) {
				vocabulary.add(processor.processToken(lToken));
			}
		}
		
		// TODO:
		// Constuct a TermDocumentMatrix once you know the size of the vocabulary.
		// THEN, do the loop again! But instead of inserting into the HashSet, add terms to the index with addPosting.
		TermDocumentIndex lTermDocIndex = new TermDocumentIndex(vocabulary, corpus.getCorpusSize());
		for (Document lDoc : documents) {
			EnglishTokenStream eStream = new EnglishTokenStream(lDoc.getContent());
			Iterable<String> eTokens = eStream.getTokens();
			for (String lToken : eTokens) {
				lTermDocIndex.addTerm(processor.processToken(lToken), lDoc.getId());
			}
		}
		
		return lTermDocIndex;
	}
}