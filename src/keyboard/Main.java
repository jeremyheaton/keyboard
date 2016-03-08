package keyboard;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Trie tri = new Trie();
		while (true) {
			System.out
					.println("Enter in a sentence to simulate training the program");
			Scanner scan = new Scanner(System.in);
			String option = scan.nextLine();
			tri.train(option);
			System.out
					.println("Enter in a full or partial word. The Program will return a list of all words ordered by confidence as well as the chosen word and it's confidence");
			scan = new Scanner(System.in);
			option = scan.nextLine();
			List<ICandidate> list = tri.getWords(option);
			StringBuilder sb = new StringBuilder();
			for (ICandidate ican : list) {
				sb.append(ican.getWord() + "("
						+ ican.getConfidence().toString() + ") ");
			}
			System.out.println(sb);
			System.out.println("the chosen word is '" + tri.getCandidate(list).getWord() + "' with confidence of " + tri.getCandidate(list).getConfidence().toString());
			System.out.println("Would you like to keep training the program and have it guess more words? y/n");
			scan = new Scanner(System.in);
			option = scan.nextLine();
			if (option.equalsIgnoreCase("n")) {
				break;
			}
		}
	}
}
