package qualification;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 1/9/17.
 */
public class ProgressPie {

    public static void main(String[] args) {
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            br = new BufferedReader(new FileReader("progress_pie.txt"));
            bw = new BufferedWriter(new FileWriter("output.txt"));
            int T = Integer.parseInt(br.readLine());

            for(int i = 1; i<=T; i++){
                String[] inputArray = br.readLine().split(" ");
                int P = Integer.parseInt(inputArray[0]);
                int X = Integer.parseInt(inputArray[1]);
                int Y = Integer.parseInt(inputArray[2]);

                bw.write("Case #" + i + ": " + solve(P,X,Y));
                bw.newLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String solve(int P, int X, int Y){

        if(((X-50)*(X-50) + (Y-50)*(Y-50)) > 50 * 50)
            return "white";

        if(P == 0)
            return "white";

        if(X == 50 && Y >= 50)
            return "black";


        double alpha = 90 - Math.toDegrees(Math.atan2(Y-50,X-50));
        if(alpha < 0)
            alpha += 360;

        if(alpha * 100 > P * 360)
            return "white";

        return "black";
    }

}