package keyboard;

import java.util.List;

public interface ICandidate {
	String getWord(); // returns the autocomplete candidate

	Integer getConfidence();// returns the confidence* for the candidate

	interface IAutocompleteProvider {
		List<ICandidate> getWords(String fragment);// : returns list of
													// autocomplete candidates
													// ordered by confidence*

		void train(String passage);// : trains the algorithm with the provided
									// passage

	}

}
