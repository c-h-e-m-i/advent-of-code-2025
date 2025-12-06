import java.io.*;
import java.util.*;

public class D1P2 {
    static Lector sc;
    static StringBuilder output;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(1);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // El dial empieza apuntando a 50:
        int dial = 50, contrasenya = 0;

        // Leemos cada orden y movemos el dial acorde a lo que nos digan:
        while (sc.hasNext()) {
            String orden = sc.nextLine();

            // Miramos si debemos ir a la izquierda o a la derecha:
            int lado = orden.charAt(0) == 'L' ? -1 : 1;

            // Consultamos cuántas posiciones debemos movernos:
            int pos = Integer.parseInt(orden.substring(1));

            // Nos movemos en el dial:
            dial += lado * pos;

            /*
             * El número de veces que el dial pase por el 0 será el número de veces que cabe
             * el número 100 en él.
             * Es decir, el cociente del valor del dial entre 100 en valor absoluto.
             * Si el dial es menor o igual que 0, le restamos 100 para que los valores entre
             * -99 y 0 contabilicen
             * como 1 pasada:
             */
            if (dial <= 0 && dial != lado * pos /*
                                                 * Si el dial anterior valía 0, contabilizamos una pasada menos (pues,
                                                 * por ejemplo, entre 0 y -20 solo pasamos por el 0 una vez, no dos)
                                                 */)
                dial -= 100;

            contrasenya += Math.abs(dial / 100);

            // Una vez actualizada la contraseña, aplicamos el módulo al dial:
            dial = ((dial % 100) + 100) % 100;
        }

        output.append(contrasenya);
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


