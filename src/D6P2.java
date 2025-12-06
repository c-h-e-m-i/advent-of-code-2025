import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class D6P2 {
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
        String linea = sc.nextLine();
        int numeros = linea.length();

        // Creamos un array de StringBuilders para ir formando los números:
        StringBuilder[] nums = new StringBuilder[numeros];
        for (int i = 0; i < numeros; i++) {
            char c = linea.charAt(i);
            nums[i] = new StringBuilder(c != ' ' ? String.valueOf(c) : "");
        }

        /*
         * Voy leyendo las líneas y construyendo mis Strings de 'nums' hasta llegar a la
         * de los operandos:
         */
        linea = sc.nextLine();
        while (linea.charAt(0) != '+' && linea.charAt(0) != '*') {
            for (int i = 0; i < numeros; i++) {
                char c = linea.charAt(i);
                if (c != ' ')
                    nums[i].append(c);
            }
            linea = sc.nextLine();
        }

        /*
         * Una vez llegue a la línea de los operandos, leo cada uno y voy realizando las
         * operaciones correspondientes sobre los Strings que he construido en 'nums':
         */
        boolean sumar = false; // Si 'sumar' = false, multiplico. Si no, sumo.
        BigInteger suma = BigInteger.ZERO, // Variable para guardar la suma final.
                resParcial = BigInteger.ZERO; // Variable para guardar resultados parciales.

        for (int i = 0; i < numeros; i++) {
            /*
             * Cada vez que lea un operando, actualizaré 'suma' con el resultado parcial
             * anterior:
             */
            if (linea.charAt(i) == '+') {
                suma = suma.add(resParcial);
                sumar = true;
                resParcial = BigInteger.ZERO;
            } else if (linea.charAt(i) == '*') {
                suma = suma.add(resParcial);
                sumar = false;
                resParcial = BigInteger.ONE;
            }

            // Solo actualizaré los resultados parciales cuando el número no sea vacío:
            if (!nums[i].isEmpty()) {
                BigInteger num = new BigInteger(nums[i].toString());
                resParcial = sumar ? resParcial.add(num) : resParcial.multiply(num);
            }
        }
        /*
         * Al final del todo me habrá faltado actualizar 'suma' una última vez, así que
         * lo hago:
         */
        suma = suma.add(resParcial);

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