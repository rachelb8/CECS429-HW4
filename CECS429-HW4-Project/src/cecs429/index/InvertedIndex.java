package cecs429.index;
import java.util.*;

/**
 * Implements an Inverted Index
 */
public class InvertedIndex implements Index {
    Map<String, List<Posting>> invIndex = new HashMap<String, List<Posting>>();
    private final List<String> mVocabulary;
    private int mCorpusSize;

    public InvertedIndex(Collection<String> vocabulary, int corpuseSize) {
    	mVocabulary = new ArrayList<String>();
    	mVocabulary.addAll(vocabulary);
    	mCorpusSize = corpuseSize;
    	Collections.sort(mVocabulary);
    }
    
    /**
     * TO-DO
     */
    public void addTerm(String term, int documentId) {
    	// termPostings = the list of postings for that term
    	List<Posting> termPostings = invIndex.get(term);
    	// if the term exists
        if (termPostings != null){
        	// add documentId to postings
        	List<Posting> postingsOfTerm = invIndex.get(term);
        	Posting lastPosting = postingsOfTerm.get(postingsOfTerm.size());
        	int lastDocID = lastPosting.getDocumentId();
        	if (!(lastDocID == documentId)){
        		invIndex.get(term).add(new Posting(documentId));
        	}
        }	
        else{
        	List<Posting> lPostings = new ArrayList<>();
        	lPostings.add(new Posting(documentId));
			invIndex.put(term, lPostings); 
        }
    }

    /**
     * Retrieves a list of Postings of documents that contain the given term.
     */
    public List<Posting> getPostings(String term){
       //TO-DO
    	return null;
    }

    public List<String> getVocabulary() {
    	return Collections.unmodifiableList(mVocabulary);
    }
}
