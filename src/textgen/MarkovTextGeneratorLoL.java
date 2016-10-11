package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[a-zA-Z]+");
		Matcher m = tokSplitter.matcher(sourceText);
		while (m.find()) {
			tokens.add(m.group());
		}
		String prevWord = "";
		if(tokens.size() >0 ){
			starter = tokens.get(0);
			prevWord = starter;
			wordList.add(new ListNode(starter));
			
			int index = 0;
			for (String w : tokens) {
				// ignoring the first word
				if(index == 0){
					index++;
					continue;
				}else{
					boolean found = false;
					for (ListNode word : wordList) {
						if(word.getWord().equalsIgnoreCase(w)){
							found = true;
							break;
						}
					}
				
					ListNode prevNode= null;
					for (ListNode word : wordList) {
						if(word.getWord().equalsIgnoreCase(prevWord)){
							prevNode = word;
						}
					}
					if(found == true){
						prevNode.addNextWord(w);
					}else{
						prevNode.addNextWord(w);
						wordList.add(new ListNode(w));
					}
					prevWord = w;
				}
			}
		}else{
			return;
		}
		// adding next to last one
		wordList.get(wordList.size()-1).addNextWord(starter);
		//wordList.toString();
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		
		/*
		 * set "currWord" to be the starter word
			set "output" to be ""
			add "currWord" to output
			while you need more words
   			find the "node" corresponding to "currWord" in the list
   			select a random word "w" from the "wordList" for "node"
   			add "w" to the "output"
   			set "currWord" to be "w" 
   			increment number of words added to the list
		 * 
		 * */
		
		if(wordList.size() <= 0)
			return "";
		
		
		
		
		
		String currWord = wordList.get(0).getWord();
		String output = "";
		int index = 0;
		
		while(index < numWords){
			output = output + currWord + " ";
			for (ListNode listNode : wordList) {
				if(listNode.getWord().equalsIgnoreCase(currWord)){
					currWord = listNode.getRandomNextWord(new Random());
					break;
				}
			}
			index++;
		}
			
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int i = generator.nextInt(nextWords.size());
		return nextWords.get(i);
	    
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


