package keyboard;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Trie implements ICandidate.IAutocompleteProvider {
	private TrieNode root;

	// constructor for the data structure, initializes the root node
	public Trie() {
		root = new TrieNode();
	}

	// Inserts a word into the trie.
	public boolean insert(String word) {
		
		Map<Character, TrieNode> children = root.children;
		TrieNode tn = root;
		// iterate through the characters of a word
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			TrieNode t;
			// if the tree already contains the character as a node, don't
			// insert it.
			if (children.containsKey(c)) {
				// keeps track of duplicate words, returns false as nothing gets
				// inserted
				if (children.get(c).isWord && i == word.length() - 1) {
					children.get(c).increment();
					return false;
				}
				// gets child if the character is already a node
				t = children.get(c);
			} else {
				// if character isn't in a node, create the node and place it
				t = new TrieNode(c);
				children.put(c, t);
			}
			// set children to the new node's children for next loop interation
			children = t.children;
			// set word node and set the word of the leaf
			if (i == word.length() - 1) {
				t.isWord = true;
				t.word = word;

			}
		}
		// return true if inserted.
		return true;
	}

	public TrieNode searchNode(String str) {
		Map<Character, TrieNode> children = root.children;
		TrieNode t = null;
		// iterate through the structure using the characters of a word
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// if the child contains the key set children to t.children for
			// next iteration
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.children;
			} else {
				// no string found
				return null;
			}
		}
		// return the node if the string was found
		return t;
	}

	// Returns true if the word is in the structure.
	public boolean search(String word) {
		TrieNode node = searchNode(word);
		if (node != null && node.isWord)
			return true;
		else
			return false;
	}

	// Returns if there is any word in the trie
	// that starts with the given word.
	public boolean startsWith(String prefix) {
		if (searchNode(prefix) == null)
			return false;
		else
			return true;
	}

	// Traverse through trie finding all words that start with a certain prefix.
	public void traverseWords(TrieNode node, List<ICandidate> nodeList) {
		// if the node is a leaf add it to the node list
		if (node.isWord) {
			nodeList.add(node);
		}
		// If the node has children continue to traverse the trie
		if (node.hasChildren()) {

			// loop over all children
			for (Character c : node.children.keySet()) {
				// for each child traverse further
				traverseWords(node.children.get(c), nodeList);
			}
		}
	}

	@Override
	public List<ICandidate> getWords(String fragment) {
		// set up the list of nodes we will find
		List<ICandidate> nodeList = new ArrayList<ICandidate>();
		// search the node and check for the prefix
		TrieNode node = searchNode(fragment.toLowerCase());
		// get list of nodes that are words
		traverseWords(node, nodeList);
		// sort the list of nodes by confidence desc, the rest will be alphabetical.
		Collections.sort(nodeList, new Comparator<ICandidate>() {
			@Override
			public int compare(ICandidate n1, ICandidate n2) {
				return n2.getConfidence() - n1.getConfidence();
			}
		});
		return nodeList;

	}

	@Override
	public void train(String passage) {
		passage = passage.toLowerCase();
		// set up empty list of words
		List<String> words = new ArrayList<String>();
		// set up iteration
		BreakIterator breakIterator = BreakIterator.getWordInstance();
		breakIterator.setText(passage);
		int lastIndex = breakIterator.first();
		// iterate through BreakIterator breaking up string into individual
		// words, remove any punctuation
		while (BreakIterator.DONE != lastIndex) {
			int firstIndex = lastIndex;
			lastIndex = breakIterator.next();
			if (lastIndex != BreakIterator.DONE
					&& Character.isLetterOrDigit(passage.charAt(firstIndex))) {
				words.add(passage.substring(firstIndex, lastIndex));
			}
		}
		for (String word : words) {
			// insert the word into the structure
			insert(word);
		}
	}

	public ICandidate getCandidate(List<ICandidate> list) {
		// return the first element of the list, this is the Candidate since the
		// list is sorted
		return list.get(0);

	}

}