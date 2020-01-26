//package search;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
//
//public class AlphaBetaSearch implements Search
//{
//
//    public AlphaBetaSearch()
//    {
//        states_generated = 0;
//        reached_cells = new LinkedHashMap<State, Integer>();
//    }
//
//    public Map<State, Action> findStrategy(State initialState, TerminalTest test)
//    {
//        states_generated = 0;
//        reached_cells.clear();
//		Map<State, Action> strategy = new LinkedHashMap<State, Action>();
//        maxValue(initialState, test, Integer.MIN_VALUE, Integer.MAX_VALUE, strategy);
//        reached_cells.clear();
//		return strategy;
//    }
//
//    public int minValue(State initialState, TerminalTest terminalState, int alpha, int beta, Map<State, Action> strategy )
//    {
//        if (terminalState.isTerminal(initialState))
//            return terminalState.utility(initialState);
//
//        int v = Integer.MAX_VALUE;
//        Action action_taken = null;
//        State newState = null;
//        for (Action action : initialState.getApplicableActions())
//		{
//            newState = initialState.getActionResult(action);
//            if(reached_cells.containsKey(newState))
//            {
//                v = reached_cells.get(newState);
//            }
//            else
//            {
//                states_generated++;
//                v = Math.min(v, maxValue(newState, terminalState, alpha, beta, strategy));
//                reached_cells.put(newState, v);
//            }
//            action_taken = action;
//            if(v <= alpha)
//            {
//                strategy.put(newState, action_taken);
//                return v;
//            }
//            beta = Math.min(beta, v);
//        }
//        strategy.put(newState, action_taken);
//        return v;
//    }
//
//    public int maxValue(State initialState, TerminalTest terminalState, int alpha, int beta, Map<State, Action> strategy )
//    {
//        if (terminalState.isTerminal(initialState))
//        {
//            return terminalState.utility(initialState);
//        }
//
//        int v = Integer.MIN_VALUE;
//        Action action_taken = null;
//        State newState = null;
//        for (Action action : initialState.getApplicableActions())
//        {
//            newState = initialState.getActionResult(action);
//            if(reached_cells.containsKey(newState))
//            {
//                v = reached_cells.get(newState);
//            }
//            else
//            {
//                states_generated++;
//                v = Math.max(v, minValue(newState, terminalState, alpha, beta, strategy));
//                reached_cells.put(newState, v);
//            }
//
//            action_taken = action;
//            if(v >= beta)
//            {
//                strategy.put(initialState, action_taken);
//                return v;
//            }
//            alpha = Math.max(alpha, v);
//        }
//
//        strategy.put(initialState, action_taken);
//        return v;
//    }
//
//    public int get_states_generated()
//    {
//        return states_generated;
//    }
//
//    int states_generated;
//    Map<State, Integer> reached_cells;
//};