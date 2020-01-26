//package search;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
//
//public class MinimaxSearch implements Search {
//
//	public MinimaxSearch()
//	{
//		states_generated = 0;
//	 	reached_states = new LinkedHashMap<State, Integer>();
//	}
//
//
//
//	public Map<State, Action> findStrategy(State initialState, TerminalTest terminalTest) {
//		states_generated = 0;
//		reached_states.clear();
//		Map<State, Action> strategy = new LinkedHashMap<State, Action>();
//		maxValue(initialState, terminalTest, strategy);
//		return strategy;
//	}
//
//	public int maxValue(State initialState, TerminalTest terminalState, Map<State, Action> strategy)
//	{
//		if (terminalState.isTerminal(initialState))
//			return terminalState.utility(initialState);
//
//		int maxVal = Integer.MIN_VALUE;
//		int min_value = 0;
//		Action action_taken = null;
//		for (Action action : initialState.getApplicableActions())
//		{
//			State newState = initialState.getActionResult(action);
//			if(reached_states.containsKey(newState))
//			{
//				min_value = reached_states.get(newState);
//			}
//			else
//			{
//				states_generated++;
//				min_value = minValue(newState, terminalState, strategy);
//				reached_states.put(newState, min_value);
//			}
//
//
//			if (maxVal < min_value)
//			{
//				action_taken = action;
//				maxVal = min_value;
//			}
//		}
//
//		strategy.put(initialState, action_taken);
//		return maxVal;
//	}
//
//	public int minValue(State initialState, TerminalTest terminalState, Map<State, Action> strategy)
//	{
//		if(terminalState.isTerminal(initialState))
//			return terminalState.utility(initialState);
//
//
//		int minVal = Integer.MAX_VALUE;
//		int max_value = 0;
//		Action action_taken = null;
//
//		for( Action action : initialState.getApplicableActions() )
//		{
//			State newState = initialState.getActionResult(action);
//			if(reached_states.containsKey(newState))
//			{
//				max_value = reached_states.get(newState);
//			}
//			else
//			{
//				max_value = maxValue(newState, terminalState, strategy);
//				states_generated++;
//				reached_states.put(newState, max_value);
//			}
//			if(minVal > max_value)
//			{
//				minVal = max_value;
//				action_taken = action;
//			}
//		}
//
//		strategy.put(initialState, action_taken);
//		return minVal;
//	}
//
//	public int get_states_generated()
//	{
//		return states_generated;
//	}
//
//	int states_generated;
//	Map<State, Integer> reached_states;
//}
