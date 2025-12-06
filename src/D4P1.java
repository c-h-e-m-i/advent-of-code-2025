import java.io.*;
import java.util.*;

public class D4P1 {
    static Lector sc;
    static StringBuilder output;
    public static boolean[] grafo;
    public static int n = 135;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(4);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // Creamos el grafo:
        grafo = new boolean[n * n];
        crearGrafo();

        // Recorremos el grafo:
        int res = 0;
        for (int i = 0; i < n * n; i++) {
            if (grafo[i]) {
                res += vecinos(i);
            }
        }

        output.append(res);
    }

    public static void crearGrafo() throws IOException {
        /*
         * Creamos el grafo poniendo a 'true' las entradas asociadas a símbolos '@' y a
         * 'false' las asociadas a símbolos '.':
         */
        for (int i = 0; i < n; i++) {
            String linea = sc.nextLine();
            for (int j = 0; j < n; j++) {
                char c = linea.charAt(j);
                int entrada = i * n + j;
                grafo[entrada] = c != '.';
            }
        }
    }

    public static int vecinos(int nodo) {
        // Recorremos los 8 vecinos de cada celda:
        int alrededores[] = { -1, 0, 1 }, vecinos = 0;

        for (int i : alrededores) {
            for (int j : alrededores) {
                // La celda actual no se recorre:
                if (i == 0 && j == 0) {
                    continue;
                }

                /*
                 * Si nos salimos de una fila por la izquierda o por la derecha, cambiamos de
                 * vecino:
                 */
                if ((nodo % n == 0 && i == -1) || (nodo % n == n - 1 && i == 1)) {
                    continue;
                }

                /*
                 * Si nos salimos del inicio del grafo o de su final, cambiamos de vecino:
                 */
                int celda = nodo + n * j + i;
                if (celda < 0 || celda >= grafo.length) {
                    continue;
                }

                /*
                 * Si el vecino está en una celda válida, comprobamos su valor y sumamos 1 si
                 * está a 'true':
                 */
                boolean vecino = grafo[celda];
                if (vecino) {
                    vecinos++;

                    // Si el número de vecinos llega a los 4, devolvemos 0:
                    if (vecinos == 4)
                        return 0;
                }
            }
        }

        /*
         * Si hemos llegado hasta aquí significa que el número de vecinos del nodo es
         * menor que 4, así que devolvemos 1:
         */
        return 1;
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
        int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Pair p) {
            return this.a - p.a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Pair))
                return false;
            Pair p = (Pair) o;
            return p.a == this.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }
}