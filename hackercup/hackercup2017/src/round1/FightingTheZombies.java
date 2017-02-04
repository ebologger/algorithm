package round1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by enkhbold on 1/15/17.
 */
public class FightingTheZombies {
    public static void main(String[] args) {

        BufferedWriter bw = null;
        Scanner sc = null;

        try {
            File file = new File("fighting_the_zombies.txt");
            sc = new Scanner(file);

            bw = new BufferedWriter(new FileWriter("output.txt"));
            int T = sc.nextInt();

            for(int c = 1; c<=T; c++){
                int N = sc.nextInt();
                int R = sc.nextInt();

                Zombie[] zombies = new Zombie[N];

                for(int i = 0; i<N; i++){
                    zombies[i] = new Zombie(sc.nextInt(),sc.nextInt());
                }

                //if(c == 9)
                //    System.out.println("here we go");

                bw.write("Case #" + c + ": " + solve(N,R,zombies));
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

    public static int solve(int N, int R, Zombie[] zombies){
        List<Square> squares = new LinkedList<>();
        for(Zombie zombie1 : zombies){
            for(Zombie zombie2 : zombies){
                Square square = new Square(zombie1.X, zombie2.Y);
                squares.add(square);

                for(Zombie zombie : zombies){
                    if(zombie.isContained(square, R)){
                        square.zombiesContained.add(zombie);
                    }
                }
            }
        }

        int max = 0;

        if(squares.size() == 1){
            return squares.get(0).zombiesContained.size();
        }
        for(Square square1 : squares){
            for(Square square2 : squares){
                if(!square1.equals(square2)){
                    int cnt = square1.zombiesContained.size();
                    for(Zombie z : square2.zombiesContained)
                        if(!square1.zombiesContained.contains(z))
                            cnt++;
                    if(cnt > max)
                        max = cnt;
                }
            }
        }


        return max;
    }
}


class Zombie{
    int X, Y;
    public Zombie(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public boolean isContained(Square square, int R){
        return square.X <= X && square.X + R >= X && square.Y <= Y && square.Y + R >= Y;
    }
}

class Square{
    int X, Y;
    List<Zombie> zombiesContained;

    public Square(int X, int Y){
        this.X = X;
        this.Y = Y;
        zombiesContained = new LinkedList<>();
    }
}