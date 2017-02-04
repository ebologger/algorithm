package round1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by enkhbold on 1/15/17.
 */
public class ManicMoving {

    final static int INF = 1_000_000_000;


    public static void main(String[] args) {

        BufferedWriter bw = null;
        Scanner sc = null;

        try {
            File file = new File("manic_moving.txt");
            sc = new Scanner(file);

            bw = new BufferedWriter(new FileWriter("output.txt"));
            int T = sc.nextInt();

            for(int c = 1; c<=T; c++){
                int N = sc.nextInt();
                int M = sc.nextInt();
                int K = sc.nextInt();

                int[][] graph = new int[N][N];
                for(int i = 0; i<N; i++){
                    Arrays.fill(graph[i], INF);
                    graph[i][i] = 0;
                }

                for(int i = 0; i<M; i++){
                    int A = sc.nextInt() - 1; //0-based idx
                    int B = sc.nextInt() - 1; //0-based idx
                    int G = sc.nextInt();

                    graph[A][B] = Math.min(G,graph[A][B]);
                    graph[B][A] = Math.min(G,graph[B][A]);
                }

                int[][] task = new int[K][2];
                for(int i = 0; i<K; i++){
                    int S = sc.nextInt() - 1; //0-based idx
                    int D = sc.nextInt() - 1; //0-based idx
                    task[i][0] = S;
                    task[i][1] = D;
                }

                bw.write("Case #" + c + ": " + solve(N,M,K,graph,task));
                bw.newLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                sc.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int solve(int N, int M, int K, int[][] graph, int[][] task){
        // calculate all distance between towns
        int[][] dist = AllPairShortestPath.floydWarshall(graph);

        for(int i=0; i<K; i++)
            if(dist[0][task[i][0]] == INF || dist[0][task[i][1]] == INF)
                return -1; // unreachable town


        int[][] dp = new int[K][2];

        // dp[i][0] means minimum cost after unloading i-th family && truck is empty
        // dp[i][1] means minimum cost after unloading i-th family && truck contains (i+1)-th family's belongings

        if(task.length == 1){
            return dist[0][task[0][0]] + dist[task[0][0]][task[0][1]];
        }

            dp[0][0] = dist[0][task[0][0]] + dist[task[0][0]][task[0][1]];
            dp[0][1] = dist[0][task[0][0]] + dist[task[0][0]][task[1][0]] + dist[task[1][0]][task[0][1]];


        for(int i=1; i<K-1; i++){
            dp[i][0] = Math.min(
                    dp[i-1][0] + dist[task[i-1][1]][task[i][0]] + dist[task[i][0]][task[i][1]],
                    dp[i-1][1] + dist[task[i-1][1]][task[i][1]]
            );

            dp[i][1] = Math.min(
                    dp[i-1][0] + dist[task[i-1][1]][task[i][0]] + dist[task[i][0]][task[i+1][0]] + dist[task[i+1][0]][task[i][1]],
                    dp[i-1][1] + dist[task[i-1][1]][task[i+1][0]] + dist[task[i+1][0]][task[i][1]]
            );
        }

        dp[K-1][0] = Math.min(
                dp[K-2][0] + dist[task[K-2][1]][task[K-1][0]] + dist[task[K-1][0]][task[K-1][1]],
                dp[K-2][1] + dist[task[K-2][1]][task[K-1][1]]
        );

        return dp[K-1][0];
    }
}



// Floyd Warshall algorithm
// Modified from http://www.geeksforgeeks.org/dynamic-programming-set-16-floyd-warshall-algorithm/
class AllPairShortestPath
{
    public static int[][] floydWarshall(int graph[][])
    {
        int N = graph.length;

        int dist[][] = new int[N][N];
        int i, j, k;

        /* Initialize the solution matrix same as input graph matrix.
           Or we can say the initial values of shortest distances
           are based on shortest paths considering no intermediate
           vertex. */
        for (i = 0; i < N; i++)
            for (j = 0; j < N; j++)
                dist[i][j] = graph[i][j];

        /* Add all vertices one by one to the set of intermediate
           vertices.
          ---> Before start of a iteration, we have shortest
               distances between all pairs of vertices such that
               the shortest distances consider only the vertices in
               set {0, 1, 2, .. k-1} as intermediate vertices.
          ----> After the end of a iteration, vertex no. k is added
                to the set of intermediate vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < N; k++)
        {
            // Pick all vertices as source one by one
            for (i = 0; i < N; i++)
            {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < N; j++)
                {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        return dist;
    }
}