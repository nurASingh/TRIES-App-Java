package spelling;

import java.util.List;
import java.util.Set;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should ignore the word's case. That is, you should convert the
	 * string to all lower case as you insert it.
	 */
	public boolean addWord(String word) {
		// TODO: Implement this method.
		char[] ar = word.toLowerCase().toCharArray();
		TrieNode next = root;
		for (int i = 0; i < ar.length; i++) {
			next = next.insertM(ar[i]);
		}
		next.setEndsWord(true);
		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: Implement this method

		size = 0;

		getCount(root);
		return size;
	}

	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
		// TODO: Implement this method

		if (s.length() == 0)
			return false;
		TrieNode next = null;
		for (Character c : root.getValidNextCharacters()) {
			next = root.getChild(c);

			if (next.getText().equalsIgnoreCase(s.substring(0, 1))) {

				if (getWord(next, s.toLowerCase(), 1) == true)
					return true;
			}

		}

		// return getWord(root,s.toLowerCase());

		return false;
	}

	/**
	 * * Returns up to the n "best" predictions, including the word itself, in
	 * terms of length If this string is not in the trie, it returns null.
	 * 
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the
		// trie, return an
		// empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem
		// to the back
		// of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions

		List<String> wordList = new ArrayList<String>();

		if(numCompletions == 0)
			return wordList;
		
		TrieNode next = null;
		for (Character c : root.getValidNextCharacters()) {
			next = root.getChild(c);
			if(prefix == ""){
				next = getNode(root, prefix, 1);
			}
			else if(next.getText().equalsIgnoreCase(prefix.substring(0, 1))) {
				next = getNode(next, prefix, 1);
				if (next != null)
					break;
			}else{
				next = null;
			}
		}

		if (next == null)
			return wordList;

		// apply BFS

		TrieNode[] Q = new TrieNode[100];
		int front = 0;
		int rear = 0;
		Q[rear++] = next;

		if(next.endsWord() == true)
			wordList.add(next.getText());
		
		while (front != rear) {
			TrieNode currNode= Q[front++];
			for (Character c : currNode.getValidNextCharacters()) {
				Q[rear++] = currNode.getChild(c);
				
				
				if(currNode.getChild(c).endsWord() == true){
					wordList.add(currNode.getChild(c).getText());
				}
				if(wordList.size() == numCompletions)
					break;
			}
			
			if(wordList.size() == numCompletions)
				break;
		}
		return wordList;
	}

	public TrieNode getNode(TrieNode currNode, String str, int len) {

		if (currNode.getText().equalsIgnoreCase(str) == true)
			return currNode;

		TrieNode next = null;
		for (Character c : currNode.getValidNextCharacters()) {
			next = currNode.getChild(c);
			if (next.getText().equalsIgnoreCase(str.substring(0, len + 1))) {
				return getNode(next, str, len + 1);
			}
		}
		return null;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

	public boolean getWord(TrieNode curr, String s, int len) {
		if (curr.getText().equalsIgnoreCase(s) == true)
			return true;

		if (curr == null)
			return false;

		if (curr.getText().equalsIgnoreCase(s) == true)
			return true;
		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			if (next.getText().equalsIgnoreCase(s.substring(0, len + 1))) {
				return getWord(next, s, len + 1);
			}
		}

		return false;
	}

	public void getCount(TrieNode curr) {
		if (curr.endsWord() == true) {
			size++;
		}
		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			getCount(next);
		}
	}

}