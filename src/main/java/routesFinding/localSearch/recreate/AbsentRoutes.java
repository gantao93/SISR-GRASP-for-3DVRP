package routesFinding.localSearch.recreate;

import java.util.ArrayList;
import java.util.List;


/*
根据给定的 absents 列表，从 lastRoute 中排除了包含在 absents 列表中的节点，生成新的路线列表 absentRoute
 */

public class AbsentRoutes {

    public static List<List<String>> getRuinRoutes(List<List<String>> lastRoute,
                                                   List<String> absents) {
        List<List<String>> absentRoute = new ArrayList<>();
        for (int i = 0 ; i<lastRoute.size() ; i++) {
            List<String> r = new ArrayList<>();
            for (String c : lastRoute.get(i)) {
                if (!absents.contains(c)) {
                    r.add(c);
                }
            }
            if (r.size()>0) {
                absentRoute.add(r);
            }
        }
        return absentRoute;
    }
}
