package cecs429.index;

import java.util.*;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

/**
 * Implements an Index using a term-document matrix. Requires knowing the full corpus vocabulary and number of documents
 * prior to construction.
 */
public class TermDocumentIndex implements Index {
	private final boolean[][] mMatrix;
	private final List<String> mVocabulary;
	private int mCorpusSize;
	
	/**
	 * Constructs an empty index with with given vocabulary set and corpus size.
	 * @param vocabulary a collection of all terms in the corpus vocabulary.
	 * @param corpuseSize the number of documents in the corpus.
	 */
	public TermDocumentIndex(Collection<String> vocabulary, int corpuseSize) {
		mMatrix = new boolean[vocabulary.size()][corpuseSize];
		mVocabulary = new ArrayList<String>();
		mVocabulary.addAll(vocabulary);
		mCorpusSize = corpuseSize;
		
		Collections.sort(mVocabulary);
	}
	
	/**
	 * Associates the given documentId with the given term in the index.
	 */
	public void addTerm(String term, int documentId) {
		int vIndex = Collections.binarySearch(mVocabulary, term);
		if (vIndex >= 0) {
			mMatrix[vIndex][documentId] = true;
		}
	}

	@Override
	public List<Posting> getPostings(String term) {
		List<Posting> results = new ArrayList<>();
		
		// Terell - TODO: implement this method.
		// Terell - Binary search the mVocabulary array for the given term.
		// Jonathan - Gets the int position of the vocab, which should match the row value of the array (right?)
		/* cuz its like mMatrix [x][y]

					0	  |1	 |2		|3 	 <--these are second, so they're [y]
					Doc 1 |Doc 2 |Doc 3 |Doc 4
		0 A vocab	1	   0	  0  	 1
		1 B vocab	0	   1 	  1	 	 0
		2 C vocab	......
		^	...
		|
		these are the first, so they're [x]
		If this doesn't work, this could be where I'm wrong
		*/
		int VocabPos = Collections.binarySearch(mVocabulary, term);
		// Terell - Walk down the mMatrix row for the term and collect the document IDs (column indices)
		// of the "true" entries.
		// Jonathan - mMatrix [VocabPos] should be just be the bollean row for all 10 docs on whether or not they have the vocab of that row
		int columnWalker = 0;
		// Rachel - Checks if the value was located or not to avoid an ArrayIndexOutOfBoundsException
		if (VocabPos > 0) {
			for (boolean termBool : mMatrix[VocabPos]) {
				//J - Starting from the doc at column 0
				
				//J -  if the term is in the doc
				if (termBool){
					//J - create a new posting? idk if one exists yet, and add it to results
					results.add(new Posting(columnWalker));
				}
				//J - Note: as this is a for each loop, column walker does not iterate the function, just tracks the docId of the next column
				columnWalker++;
			}
			
		}		
		return results;
	}
	
	public List<String> getVocabulary() {
		return Collections.unmodifiableList(mVocabulary);
	}
}
