package search;

import search.Action;
import search.SearchUtils.Step;

import java.util.ArrayList;
import java.util.Set;


public interface State
{
//    Player getPlayer();
    ArrayList<Step> getApplicableActions();

    State getActionResult(Action action);
}