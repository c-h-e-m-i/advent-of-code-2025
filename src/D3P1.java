import java.io.*;
import java.util.*;

public class D3P1 {
    static Lector sc;
    static StringBuilder output;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(3);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // Creamos una variable resultado:
        long res = 0;

        // Vamos leyendo línea por línea:
        while (sc.hasNext()) {
            char[] linea = sc.nextLine().toCharArray();

            /*
             * Recorremos cada línea hasta su penúltimo elemento. Nos quedamos con el mayor
             * de ese rango y su índice, y luego nos quedamos con el mayor de los que vengan
             * después:
             */
            int max1 = -1, ind = 0;
            for (int i = 0; i < linea.length - 1; i++) {
                // Restamos el ASCII de 0 para pasar de carácter a número:
                int num = linea[i] - 48;

                if (num > max1) {
                    max1 = num;
                    ind = i;
                }
            }

            int max2 = -1;
            for (int i = ind + 1; i < linea.length; i++) {
                int num = linea[i] - 48;

                if (num > max2) {
                    max2 = num;
                }
            }

            // Juntamos los dos máximos y los sumamos al resultado:
            res += max1 * 10 + max2;
        }

        output.append(res);
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
