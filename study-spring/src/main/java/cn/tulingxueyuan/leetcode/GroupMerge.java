package cn.tulingxueyuan.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname GroupMerge
 * @Description TODO
 * @Date 2025/2/24 11:11
 * @Created by liukaihua
 */
public class GroupMerge {

    public static void main(String[] args) {
        System.out.println(bfs(new int[][] {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        System.out.println(bfs(new int[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
    }

    /**
     * 广度优先
     * @param cities
     * @return
     */
    private static int bfs(int[][] cities) {
        int length = cities.length;
        boolean[] visited = new boolean[length];
        int provinces = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            if (!visited[i]) {
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int k = queue.poll();
                    visited[k] = true;
                    for (int j = 0; j < length; j++) {
                        if (cities[i][j] == 1 && !visited[j]) {
                            queue.offer(j);
                        }
                    }
                }
                provinces++;
            }
        }
        return provinces;
    }

    private static int getProvince(int[][] cities) {
        int length = cities.length;
        boolean[] visited = new boolean[length];
        int provinces = 0;
        for (int i = 0; i < length; i++) {
            if (!visited[i]) {
                dfs(i, length, visited, cities);
                provinces++;
            }
        }
        return provinces;
    }

    /**
     * 深度优先
     * @param i
     * @param length
     * @param visited
     * @param cities
     */
    private static void dfs(int i, int length, boolean[] visited, int[][] cities) {
        for (int j = 0; j < length; j++) {
            if (cities[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(j, length, visited, cities);
            }
        }
    }

}
