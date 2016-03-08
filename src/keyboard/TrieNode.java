package keyboard;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class TrieNode implements ICandidate {
	char c;
	Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
	boolean isWord;
	int wordCount = 1;
	String word;

	public TrieNode() {
	}

	public TrieNode(char c) {
		this.c = c;
	}

	// increment word count
	public void increment() {
		this.wordCount++;
	}

	// check to see if node has any children
	public boolean hasChildren() {
		return !this.children.isEmpty();

	}

	@Override
	public String getWord() {
		// TODO Auto-generated method stub
		return this.word;
	}

	@Override
	public Integer getConfidence() {
		// TODO Auto-generated method stub
		return this.wordCount;
	}

}