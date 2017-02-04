package round1;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by enkhbold on 1/15/17.
 */
public class PieProgress {
    public static void main(String[] args) {

        BufferedWriter bw = null;
        Scanner sc = null;


        try {
            File file = new File("pie_progress.txt");
            sc = new Scanner(file);

            bw = new BufferedWriter(new FileWriter("output.txt"));
            int T = sc.nextInt();

            for(int c = 1; c<=T; c++){
                int N = sc.nextInt();
                int M = sc.nextInt();

                int[][] C = new int[N][M];

                for(int i = 0; i<N; i++){
                    for(int j = 0; j<M; j++){
                        C[i][j] = sc.nextInt();
                    }
                }

                bw.write("Case #" + c + ": " + solve(N,M,C));
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

    public static int solve(int N, int M, int[][] C){
        int cost = 0;

        int[] saleCnt = new int[N]; // how many pies sold each day

        for(int i=0;i<N;i++){
            Arrays.sort(C[i]);
            int minCost = C[i][0] + 1;
            int chosenDay = i;

            for(int k=0; k<i; k++){
                int sold = saleCnt[k];
                if(sold < M) { // there's more pies to sale
                    int trueCost = C[k][sold];
                    trueCost += 2 * sold + 1; // additional tax

                    if (trueCost < minCost) {
                        minCost = trueCost;
                        chosenDay = k;
                    }
                }
            }

            cost += minCost;
            saleCnt[chosenDay]++;
        }

        return cost;
    }
}
