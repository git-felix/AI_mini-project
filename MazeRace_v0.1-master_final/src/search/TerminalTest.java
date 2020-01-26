package search;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class TerminalTest {
	protected final Map<State, Integer> utilities;

	public TerminalTest() {
		utilities = new LinkedHashMap<>();
	}
	public int utility(State state) {
		return utilities.get(state);
	}
	public abstract boolean isTerminal(State state);
}
