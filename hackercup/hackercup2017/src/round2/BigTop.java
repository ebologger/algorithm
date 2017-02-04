package round2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by enkhbold on 1/22/17.
 *
 * incomplete....
 */
public class BigTop {

    static final int INF = 1_000_000_000;
    public static void main(String[] args) {

        BufferedWriter bw = null;
        Scanner sc = null;

        try {
            File file = new File("big_top.txt");
            sc = new Scanner(file);

            bw = new BufferedWriter(new FileWriter("output.txt"));
            int T = sc.nextInt();

            for(int c = 1; c<=T; c++){
                int N = sc.nextInt();

                int[] X = new int[N];
                int[] H = new int[N];

                X[0] = sc.nextInt();
                int Ax = sc.nextInt();
                int Bx = sc.nextInt();
                int Cx = sc.nextInt();

                H[0] = sc.nextInt();
                int Ah = sc.nextInt();
                int Bh = sc.nextInt();
                int Ch = sc.nextInt();

                for(int i=1; i<N; i++){
                    X[i] =(int) ((1l * Ax * X[i-1] + Bx) % Cx) + 1;
                    H[i] =(int) ((1l * Ah * H[i-1] + Bh) % Ch) + 1;
                }

                bw.write("Case #" + c + ": " + String.format("%.10f",solve(N,X,H)));
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

    public static double solve(int N, int[] X, int[] H){
        Double area = 0.0;

        TreeSet<Peak> peaks = new TreeSet<>();
        peaks.add(new Peak(-INF, 0));
        peaks.add(new Peak(INF, 0));

        for(int i=0; i<N; i++){
            Peak peak = new Peak(X[i],H[i]);
            SortedSet<Peak> before = peaks.headSet(peak);
            SortedSet<Peak> after = peaks.tailSet(peak);

            Peak left = before.last();
            Peak right = after.first();

            if (left.h >= peak.h + peak.x - left.x) {
                // it's not peak
            } else if (right.h >= peak.h + right.x - peak.x) {
                // it's not peak
            }else {
                while (peak.h >= left.h + peak.x - left.x) {
                    peaks.remove(left);
                    left = before.last();
                }
                while (peak.h >= right.h + right.x - peak.x) {
                    peaks.remove(right);
                    right = after.first();
                }
                peaks.add(peak);
            }
        }

        Peak prev = null;
        for(Peak cur : peaks){
            if(prev == null){
                prev = cur;
                continue;
            }

            if( (prev.h + cur.h) <= (cur.x - prev.x) ){
                area += (prev.h * prev.h * 0.5) + (cur.h * cur.h * 0.5);
                int a = 1;
            }else{
                double h = ((prev.h + cur.h) - (cur.x - prev.x)) * 0.5;
                area += (prev.h + h) * (prev.h - h) * 0.5;
                area += (cur.h + h) * (cur.h - h) * 0.5;
                int a = 1;
            }
            prev = cur;
        }

        return area;
    }
}

class Peak implements Comparable<Peak> {
    int x;
    int h;

    public Peak(int x, int h) {
        this.x = x;
        this.h = h;
    }

    public int compareTo(Peak o) {
        return Integer.compare(x, o.x);
    }
}