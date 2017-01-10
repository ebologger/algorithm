package November2016.KiritoInMemeland;

/**
 * Created by enkhbold on 12/3/16.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class KiritoInMemeland {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int L = scanner.nextInt();
            int R = scanner.nextInt();
            int[] A = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                A[i] = scanner.nextInt();
            }

            Graph g = new Graph();
            int V, U;
            for (int i = 0; i < N - 1; i++) {
                U = scanner.nextInt();
                V = scanner.nextInt();

                Node vNode = g.addVertex(V);
                Node uNode = g.addVertex(U);
                g.addEdge(vNode, uNode);
            }

            KiritoInMemeland m = new KiritoInMemeland(g, A, L, R);
            m.solve();
        }
    }

    private Graph g;
    private int[] A;
    private int L;
    private int R;

    public KiritoInMemeland(Graph g, int[] A, int L, int R){
        this.g = g;
        this.A = A;
        this.L = L;
        this.R = R;
    }

    public void solve(){
        long sol = 0;

        // 1, 1-eer ni leaf-ee tasdaad baihaar hamgiin svvliin 2 leaf maani shuluun bolchihno. Tiim bolohoor hamgiin svvliin leaf deer bodolt hiihgvi
        for(int i=0; i<g.leaves.size() - 1; i++){
            long aa = countInterestingRoutes(-1, -1, null, g.leaves.get(i), 0, 0, 0, new ArrayList<>(), false, null, 0);
            //System.out.println("sol : " + aa);
            sol += aa;
        }
        System.out.println(sol);
    }

    // 1. route-iin hamgiin svvliin city ni endCursor deer baih bvh interesting route-uudiig toolno.
    // 2. endCursor-oos tsaash yavah zam baival yavah bolomjit bvh zamuud ru ni endCursor-oo 1-eer move hiigeed route-uudee toolood count-aa nemegdvvlne

    // startCursor, midCursor 2 ni peaks-iin index-ee zaana. endCursor ni bvh hotoor yavaad peak mun esehee shalgaad yavchihsan bolohoor
    // start,mid cursoruud ni zuvhun peak-ees peak-iin horoond move hiine.

    // endCursorFrom gedeg ni endCursor ru ali edge-iig damjij irsen be gedgiig zaana. Butsaad yavchihgvin tuld haha

    // distanceBetweenStartMid gedeg ni start bolon mid cursoruudiin hoorond heden city baigaa ve gedeg too. Ene too ni interesting route-iin tootoi tentsvv

    // distanceFromPreviousPeak gedeg ni svvld taaraldsan peak-ees hoish heden hot yavchaad baigaag temdeglene.

    // peakCountStartEnd - start, end cursoruudiin hoorondoh niit peakvvdiin too.

    // peaks -eer endCursor-iin dairch ungursun peak-vvdiin list. Nuguu 2 cursor maani ene listeer yavna.

    // afterSplit ni true bol intersection dor hayj 1 udaa taaraldsan.

    // nonPeakStart, nonPeakCount - ene improment hiihed ashiglah gj baigaa ed. ignore it.

    public long countInterestingRoutes(int startCursor, int midCursor,
                                       Edge endCursorFrom, Node endCursor,
                                       int distanceBetweenStartMid, int distanceFromPreviousPeak,
                                       int peakCountStartEnd, List<Peak> peaks, boolean afterSplit, Node nonPeakStart, int nonPeakCount){

        // start ni mid cursoriigoo gvitsej ochson ved funktsee duusgavar bolgono. Tuhain muchiruus dor hayj 1 hot aguulsan bolomjuudiig toolj baigaa tul
        // bidend vvnees tsaash tootsoh hereg bhgvi
        if(startCursor > 0 && startCursor == midCursor)
            return 0;

        // start mid cursoruudiin hoorondoh hotuudiin too ni manai interesting route-iin tootoi tentsvv.
        // shuluun deer start = i, mid = j, end = k gesen indextei hotuudiig zaaj baisan gevel
        // i+1, i+2 ,,,, k
        // i+2, i+3,,,,, k
        // j , j+1 ,,,,,,k gesen bvh routvvd interesting route bolno.
        long counts = distanceBetweenStartMid;

        /*
        // Ene improvement hiihtei holbootoi code. Improvement-iin maani sanaa ni
        // a, b 2 node-iin hoorondoh edge ni >0 weight-tei baival
        // a.vertex-ees ehleed b.vertex hot hvrtel niit edge.weight shirheg non-peak, non-intersection node-vvd baigaa gesen vg.
        // uuruur helbel edgeer >0 weight gedeg ni bvh node-oor damjihgvi shortcut bolno.

        Edge nextEdge;
        if(endCursor.edges.size() == 2 && (nextEdge = endCursor.otherOne(endCursorFrom)).weight > 0){
            counts *= nextEdge.weight;
            endCursorFrom = nextEdge;
            endCursor = nextEdge.otherOne(endCursor);
            distanceFromPreviousPeak += (nextEdge.weight - 1);
        }
        */

        boolean justSplitted = false;
        if(!afterSplit && endCursor.edges.size() > 2){ // endCursor maani intersection deer baina.
            afterSplit = true;
            justSplitted = true;
            peaks.add(new Peak(distanceFromPreviousPeak)); // endCursoriig peak rv nemj baigaa ni start bolon mid cursor ni hezee ch ene tsegees hoish yavahgvi.

            Iterator<Edge> it = endCursor.edges.iterator(); // muchiruu modnoos ni taslah vildel.
            while (it.hasNext()) {
                Edge e = it.next();
                if(e == endCursorFrom)
                    it.remove();
            }
        }

        /* Ene bas improvementiin 1 heseg. End cursor maani davtaj yavj baihdaa daraalsan olon NonPeak, NonIntersection node-uud
        taaraldah yum bol bvgdiig ni bagtslaad ehnii node, svvliin node 2iig ni weighttei edgeer holboj ugnu.

        if(afterSplit && !justSplitted){
            if(endCursor.edges.size() == 2 && !isPeak(endCursor)){ // end cursor is not peak or intersection
                if(nonPeakCount == 0)
                    nonPeakStart = endCursor;
                else if(nonPeakCount == 1){
                    nonPeakStart.edges.remove(endCursorFrom);
                }

                nonPeakCount++;
            }else{
                if(nonPeakCount >= 2){
                    Node endCursorPrev = endCursorFrom.otherOne(endCursor);
                    endCursorPrev.edges.remove(endCursorPrev.otherOne(endCursorFrom));
                    Edge newEdge = g.addEdge(nonPeakStart, endCursorPrev);
                    newEdge.weight = nonPeakCount;
                }
                nonPeakStart = null;
                nonPeakCount = 0;
            }
        }
        */


        // endCursor-oo tsaash ni move hiih bvh bolomj ru ni daraagiin funktsee duudna
        for(int i=0; i<endCursor.edges.size(); i++) {
            if(!endCursor.edges.get(i).equals(endCursorFrom)){
                counts += moveCursors(startCursor, midCursor, endCursorFrom, endCursor, endCursor.edges.get(i),
                        distanceBetweenStartMid, distanceFromPreviousPeak, peakCountStartEnd, peaks, afterSplit, nonPeakStart, nonPeakCount);

            }


        }

        return counts;
    }


    // Daraagiin node ni yamar node baihaas hamaaraad endCursor maani peak ch bolj bolno, peak bish ch bolj bolno.
    // endCursor peak/nonpeak baihaas hamaaraad start bolon mid cursor maani move hiih v vgvi yu gedgee shiidne.
    public long moveCursors(int startCursor, int midCursor,
                            Edge endCursorFrom, Node endCursor, Edge endCursorNext,
                            int distanceBetweenStartMid, int distanceFromPreviousPeak,
                            int peakCountStartEnd, List<Peak> peaks, boolean afterSplit, Node nonPeakStart, int nonPeakCount){
        if (isPeak(endCursorFrom, endCursor, endCursorNext)) { // endCursor is peak

            if(!afterSplit){ // start, mid cursoruud maani intersection-oos tsaash yavahgvi tul afterSplit=true bol peaks rv add hiih shaardlagagvi.
                peaks.add(new Peak(distanceFromPreviousPeak));
                distanceFromPreviousPeak = 0;
            }

            if (peakCountStartEnd >= L - 1) { // peak-iin too L hvreh ved mid cursoroo move hiij ehelne.
                if( midCursor < peaks.size()  - 1){ // mid cursor maani peaks-iin svvliin element deer ochson ve buyu intersection deer irsen bol tsaash yavahgvi
                    midCursor++;
                    distanceBetweenStartMid += peaks.get(midCursor).distanceFromPreviousPeak; // uragshilsan hotiin toogoor distanceBetweenStartMid-iig nemegdvvlne
                }
            }

            if (peakCountStartEnd == R) { // R shirheg peak garaad irsen bol start cursoroo move hiij ehelne
                if( startCursor < peaks.size()  - 1){ // mun adil intersection deer irsen bol start cursor uragshlahgvi. gehdee ene code duudagdahgvi baih. startCursor maani midCursortoigoo zeregtseed ochchihson baih tul return hiigdchihsen baina
                    startCursor++;
                    distanceBetweenStartMid -= peaks.get(startCursor).distanceFromPreviousPeak;
                }
                peakCountStartEnd--;
            }

            peakCountStartEnd++;
        }

        distanceFromPreviousPeak++;

        // cursoruudiigaa baih baih gazar ni avaachsan bol umnuh funktsee duudaj interesting route-vvdee toolno
        return countInterestingRoutes(startCursor, midCursor, endCursorNext, endCursorNext.otherOne(endCursor),
                distanceBetweenStartMid, distanceFromPreviousPeak, peakCountStartEnd, peaks, afterSplit, nonPeakStart, nonPeakCount);

    }

    public boolean isPeak(Edge prev, Node current, Edge next){
        if(prev == null || current == null || next == null)
            return false;

        if(A[prev.otherOne(current).vertex] < A[current.vertex] && A[current.vertex] > A[next.otherOne(current).vertex])
            return true;

        return false;
    }

    public boolean isPeak(Node current){ // 2oos olon edge-tei ved ene funktsiig ashiglaj bolohgvi
        if(current.edges.size() != 2)
            return false;

        return isPeak(current.edges.get(0), current, current.edges.get(1));
    }

}

class Peak {
    int distanceFromPreviousPeak;

    public Peak(int dist){
        this.distanceFromPreviousPeak = dist;
    }
}

class Graph {
    Map<Integer, Node> nodes;
    List<Node> leaves;

    public Graph() {
        nodes = new HashMap<>();
        leaves = new ArrayList<>();
    }

    public Node addVertex(int vertex) {
        if (nodes.containsKey(vertex)) {
            leaves.remove(nodes.get(vertex));
            return nodes.get(vertex);
        }
        Node n = new Node(vertex);
        nodes.put(vertex, n);
        leaves.add(n);
        return n;
    }

    public Edge addEdge(Node node1, Node node2) {
        Edge e = new Edge(node1, node2);

        node1.addEdge(e);
        node2.addEdge(e);

        return e;
    }

}

class Node {
    int vertex;
    List<Edge> edges;

    public Node(int vertex) {
        this.vertex = vertex;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public Edge otherOne(Edge edge){ // only used if there is 2 edge
        return edge == edges.get(0) ? edges.get(1) : edges.get(0);
    }
}

class Edge {
    Node node1, node2;
    int weight;

    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = 0;
    }

    public Node otherOne(Node node){
        return node == node1 ? node2 : node1;
    }

    public boolean equals(Edge other) {
        if(other == null)
            return false;
        return this == other || this.node1 == other.node1 && this.node2 == other.node2 || this.node1 == other.node2 && this.node2 == other.node1;
    }
}

class FasterScanner {
    private InputStream mIs;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;

    public FasterScanner() {
        this(System.in);
    }

    public FasterScanner(InputStream is) {
        mIs = is;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = mIs.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public String nextLine() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isEndOfLine(c));
        return res.toString();
    }

    public String nextString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public long nextLong() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public int nextInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public boolean isSpaceChar(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public boolean isEndOfLine(int c) {
        return c == '\n' || c == '\r' || c == -1;
    }
}