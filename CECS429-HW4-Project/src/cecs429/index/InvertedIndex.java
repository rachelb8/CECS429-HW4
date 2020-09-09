package cecs429.index;
import java.util.*;

/**
 * Implements an Inverted Index
 */
public class InvertedIndex implements Index {
    Map<String, List<Posting>> invIndex = new HashMap<String, List<Posting>>();
	
	public InvertedIndex() {	
		
    }
    
    /**
     * Add documentID to the term's postings list if it does not already exist
     */
    public void addTerm(String term, int documentId) {
    	// termPostings = the list of postings for that term
    	List<Posting> termPostings = invIndex.get(term);
    	// if the term exists
        if (termPostings != null){
        	// add documentId to postings
        	List<Posting> postingsOfTerm = invIndex.get(term);
        	Posting lastPosting = postingsOfTerm.get(postingsOfTerm.size()-1);
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
     * Retrieves a list of Postings of documents that contain the given term
     */
    public List<Posting> getPostings(String term){
		return invIndex.get(term);
    }

    public List<String> getVocabulary() {
		List<String> lVocab = new ArrayList<>();
		for (String lstring : invIndex.keySet()) {
			lVocab.add(lstring);
		}
		Collections.sort(lVocab);
    	return Collections.unmodifiableList(lVocab);
    }
}
