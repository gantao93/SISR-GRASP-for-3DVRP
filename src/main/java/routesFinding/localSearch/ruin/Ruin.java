package routesFinding.localSearch.ruin;


/*
ruin method- adjancent string removal
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class Ruin {

    // m最大可移除字符串的数量
    private static int m=1;

    public static List<String> getAbsents(String depot,
                                          List<String> customerList,
                                          List<List<String>> lastRoute,
                                          int[][] neighbours,
                                          List<String> absents,
                                          double c_bar,
                                          double L_max,
                                          double m_alpha) {
//        m = 1;
        double meanRouteLength = 0.0;
        for (List<String> r : lastRoute) {
            meanRouteLength += r.size();
        }
        meanRouteLength /= lastRoute.size();
        double l_s_max = Math.min(L_max, meanRouteLength);
        double k_s_max = 4.0*c_bar/(1.0+l_s_max)-1.0;
        int k_s = (int)(Math.random()*k_s_max+1.0);
        int c_s_seed = (int)(Math.random()*neighbours.length);

        // 初始化absents为空
        if (absents==null) {
            absents = new ArrayList<>();
        }

        ArrayList<Integer> ruined_r = new ArrayList<Integer>();

        for (int c : neighbours[c_s_seed]) {
            String cNode = customerList.get(c);
            if (ruined_r.size() >= k_s) {
                break;
            }
            if (cNode != depot && !absents.contains(cNode)) {
                int r_index = findR(lastRoute, cNode);
                if (!ruined_r.contains(r_index)) {
                    double l_t_max = Math.min(l_s_max, lastRoute.get(r_index).size());
                    int l_t = (int)(Math.random()*l_t_max+1.0);
                    List<String> newlyRemoved = removeNodes(lastRoute.get(r_index), l_t, cNode, m_alpha);
                    for (String node : newlyRemoved) {
                        absents.add(node);
                    }
                    ruined_r.add(r_index);
                }
            }
        }
        return absents;
    }

    /*
    根据概率来选择使用 stringRemove 或 splitRemove 方法来删除节点
     */
    private static List<String> removeNodes(List<String> r, int l_t, String c, double m_alpha) {
        List<String> removed;
        if (Math.random()<0.5) {
            removed = stringRemove(r, l_t, c);
        } else {
            removed = splitStringRemove(r, l_t, c);
            if (m<(r.size()-l_t) || Math.random()>m_alpha) m++;
        }
        return removed;
    }

    /*
    从r中删除指定数量的元素。
    c：目标字符串
    l_t： 在目标字符串c两侧要移除的数量
     */
    private static List<String> stringRemove(List<String> r, int l_t, String c) {
        int i_c = IntStream.range(0, r.size())
                .filter(i -> r.get(i).equals(c))
                .findFirst()
                .orElse(-1);
        int range1 = Math.max(0, i_c+1-l_t);
        int range2 = Math.min(i_c, r.size()-l_t)+1;
        int start = ThreadLocalRandom.current().nextInt(range1, range2);
        List<String> subList = r.subList(start, start + l_t);
        return subList;
    }

    private static List<String> splitStringRemove(List<String> r, int l_t, String c) {
        int additional_l = Math.min(m, r.size()-l_t);
        int l_t_m = l_t+additional_l;
        int i_c = IntStream.range(0, r.size())
                .filter(i -> r.get(i).equals(c))
                .findFirst()
                .orElse(-1);
        int range1 = Math.max(0, i_c+1-l_t_m);
        int range2 = Math.min(i_c, r.size()-l_t_m)+1;
        int start = ThreadLocalRandom.current().nextInt(range1, range2);
        List<String> potentialRemoval = new ArrayList<String>(l_t_m);
        List<String> subList = r.subList(start, start+l_t_m);
        for (String i : subList) {
            potentialRemoval.add(i);
        }
        Collections.shuffle(potentialRemoval);
        return potentialRemoval.subList(0, l_t);
    }

    /*
    遍历当前路线，找到路线中是否含有节点c，找到就返回节点c在路线中的索引
     */
    private static int findR(List<List<String>> lastRoute, String c) {
        int index = -1;
        for (int i = 0; i < lastRoute.size(); i++) {
            if (lastRoute.get(i).contains(c)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
