import java.io.*;
import java.util.*;

public class D5P2 {
    static Lector sc;
    static StringBuilder output;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(5);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // Nos guardamos todos los intervalos en un ArrayList:
        ArrayList<Pair> intervalos = new ArrayList<>();
        String intervalo = sc.nextLine();
        while (!intervalo.isEmpty()) {
            String[] extremos = intervalo.split("-");
            intervalos.add(new Pair(Long.parseLong(extremos[0]), Long.parseLong(extremos[1])));
            intervalo = sc.nextLine();
        }

        // Ordenamos la ArrayList de menor a mayor (según el extremo inferior de cada
        // intervalo):
        Collections.sort(intervalos);

        // Vamos recorriéndolos en orden y los fusionamos:
        ArrayList<Pair> fusionados = new ArrayList<>();
        fusionados.add(intervalos.get(0));

        for (int i = 1; i < intervalos.size(); i++) {
            Pair ultimo = fusionados.get(fusionados.size() - 1), actual = intervalos.get(i);

            /*
             * Si el extremo inferior del intervalo actual es menor que el superior del
             * último intervalo de 'fusionados', significa que los podemos fusionar. Si no,
             * crearemos una nueva entrada en 'fusionados' con el intervalo actual:
             */
            if (ultimo.sup >= actual.inf) {
                if (ultimo.sup < actual.sup)
                    ultimo.sup = actual.sup;
            } else
                fusionados.add(actual);
        }

        // Calculamos la cantidad de números que comprende 'fusionados':
        long total = 0;
        for (Pair itv : fusionados) {
            total += itv.sup - itv.inf + 1;
        }
        output.append(total);
    }

    // -------- CLASES Y MÉTODOS AUXILIARES --------
    public static void inicializar(int prob) throws IOException {
        File f = new File(String.format("inputs/%02d.txt", prob));
        sc = new Lector();
        sc.leerArchivo(f);
        output = new StringBuilder();
    }

    public static void inicializar() {
        sc = new Lector();
        sc.leerStd();
        output = new StringBuilder();
    }

    @SuppressWarnings("unused")
    private static class Lector {
        BufferedReader br;
        StringTokenizer st;

        Lector() {
            br = null;
            st = new StringTokenizer("");
        }

        void leerArchivo(File f) throws IOException {
            br = new BufferedReader(new FileReader(f));
        }

        void leerStd() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        void cerrar() throws IOException {
            br.close();
        }

        boolean hasNext() throws IOException {
            if (st.hasMoreTokens())
                return true;
            String aux = br.readLine();
            if (aux == null)
                return false;
            st = new StringTokenizer(aux);
            return true;
        }

        String next() throws IOException {
            if (!st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        String nextLine() throws IOException {
            if (!st.hasMoreTokens()) {
                return br.readLine();
            } else {
                StringBuilder resto = new StringBuilder();
                while (st.hasMoreTokens()) {
                    resto.append(st.nextToken()).append(" ");
                }
                return resto.toString().trim();
            }
        }

        byte nextByte() throws IOException {
            return Byte.parseByte(next());
        }

        short nextShort() throws IOException {
            return Short.parseShort(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        float nextFloat() throws IOException {
            return Float.parseFloat(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        boolean nextBoolean() throws IOException {
            return Boolean.parseBoolean(next());
        }
    }

    @SuppressWarnings("unused")
    private static class Pair implements Comparable<Pair> {
        long inf, sup;

        public Pair(long inf, long sup) {
            this.inf = inf;
            this.sup = sup;
        }

        @Override
        public int compareTo(Pair p) {
            if (this.inf == p.inf)
                return this.sup < p.sup ? -1 : 1;

            return this.inf < p.inf ? -1 : 1;
        }
    }
}