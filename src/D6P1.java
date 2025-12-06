import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class D6P1 {
    static Lector sc;
    static StringBuilder output;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(6);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // Leemos la primera línea para ver con cuántos números tenemos que operar:
        String[] linea = sc.nextLine().trim().split("\\s+");
        int numeros = linea.length;

        /*
         * Creamos dos arrays de BigIntegers, uno para guardarnos las sumas parciales y
         * otro para las multiplicaciones parciales:
         */
        BigInteger[] sumas = new BigInteger[numeros], productos = new BigInteger[numeros];

        for (int i = 0; i < numeros; i++) {
            sumas[i] = new BigInteger(linea[i]);
            productos[i] = new BigInteger(linea[i]);
        }

        // Vamos leyendo cada línea y operando de la manera correspondiente:
        linea = sc.nextLine().trim().split("\\s+");
        while (linea[0].charAt(0) >= 48 /* Vemos si el ASCII del primer carácter es >= que el de 0 */) {
            // Actualizamos 'sumas' y 'productos':
            for (int i = 0; i < numeros; i++) {
                BigInteger num = new BigInteger(linea[i]);
                sumas[i] = sumas[i].add(num);
                productos[i] = productos[i].multiply(num);
            }

            // Actualizamos 'linea':
            linea = sc.nextLine().trim().split("\\s+");
        }

        /*
         * Una vez lleguemos a la línea con los operandos, hacemos la suma de los
         * resultados parciales correspondientes:
         */
        BigInteger suma = BigInteger.ZERO;
        for (int i = 0; i < numeros; i++) {
            if (linea[i].charAt(0) == '+')
                suma = suma.add(sumas[i]);
            else
                suma = suma.add(productos[i]);
        }

        output.append(suma);
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
    private static class Nodo implements Comparable<Nodo> {
        long inf, sup, max;
        Nodo izq, der;

        public Nodo(long inf, long sup) {
            this.inf = inf;
            this.sup = sup;
            // Al momento de insertarlo, el máximo de un nodo será su extremo superior:
            this.max = sup;
        }

        @Override
        public int compareTo(Nodo n) {
            return this.inf < n.inf ? -1 : 1;
        }
    }
}