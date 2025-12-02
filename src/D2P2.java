// SOLUCIÓN: 25663320831

import java.io.*;
import java.util.*;

public class D2P2 {
    static Lector sc;
    static StringBuilder output;

    public static void main(String[] args) throws IOException {
        // Inicializamos entrada y salida:
        inicializar(2);

        // Resolvemos el problema:
        solve();

        // Imprimimos la salida y cerramos el flujo de entrada:
        System.out.print(output);
        sc.cerrar();
    }

    public static void solve() throws IOException {
        // Precalculo los divisores de cada número entre 1 y 10 (las longitudes de mis
        // datos de entrada):
        int[][] longitudes = { {}, {}, { 1 }, { 1 }, { 1, 2 }, { 1 }, { 1, 2, 3 }, { 1 }, { 1, 2, 4 }, { 1, 3 },
                { 1, 2, 5 } };

        // Leo la lista de rangos:
        String lista = sc.nextLine();

        // Elimino los guiones y las comas para convertirlo en un array donde cada par
        // de números son los extremos de un rango:
        String[] rangos = lista.split("-|,");

        // Inicializo mi variable solución:
        long res = 0;

        // Voy leyendo todos los rangos:
        for (int i = 0; i < rangos.length; i += 2) {

            // Me quedo con la longitud del extremo inferior del rango:
            int longitud = rangos[i].length();

            // Me guardo los valores numéricos de los extremos del rango:
            long primerVal = Long.parseLong(rangos[i]), segundoVal = Long.parseLong(rangos[i + 1]);

            while (primerVal <= segundoVal) {
                /*
                 * Llamo a la función 'sumaInv' (que funciona como la usada en la parte 1) para
                 * todas las posibles longitudes de segmentos repetidos dentro de mi rango (es
                 * decir, los divisores del número de digitos de 'primerVal'):
                 */

                /*
                 * > Como es posible que entre segmentos de distinta longitud se repitan valores
                 * (por ejemplo, 222222 se puede dividir tanto en 2_2_2_2_2_2 como 22_22_22 como
                 * 222_222, tenemos que RESTAR la suma de todos esos valores a la suma total que
                 * llevemos. Ten en cuenta que todos los valores que aparezcan con segmentos de,
                 * por ejemplo, longitud 2 (véase 12_12_12_12) también aparecerán con segmentos
                 * de longitud igual a todos los múltiplos de dos (1212_1212), por lo que,
                 * podemos guardarnos la suma total de valores para cada longitud de segmento
                 * (en el array 'sumaAnt') y restar aquellas que correspondan a segmentos de una
                 * longitud que divida a nuestra longitud actual (para lo que haremos uso de la
                 * función 'sumaAnteriores'):
                 */
                long[] sumasAnt = new long[10];
                for (int divisor : longitudes[longitud]) {
                    long suma = sumaInv(primerVal, segundoVal, longitud, divisor);
                    res += suma - sumaAnteriores(sumasAnt, divisor);
                    sumasAnt[divisor] = suma;
                }

                /*
                 * Actualizo 'primerVal' para que sea igual al primer valor con 1 dígito más que
                 * los que tenía antes:
                 */
                primerVal = (long) Math.pow(10, longitud);
                longitud++;
            }
        }

        output.append(res);
    }

    public static long sumaAnteriores(long[] sumasAnt, int divisor) {
        /*
         * Miramos cada posible divisor del valor 'divisor' y, de los que sean
         * divisores, sumamos a 'res' la suma de sus números inválidos:
         */
        long res = 0;

        /*
         * > Recorreremos solo hasta 'divisor' / 2 porque como mucho tendremos como
         * divisor la mitad del valor (ya que el valor en sí no se cuenta, pues
         * necesitamos que un segmento se repita al menos 2 veces. Si necesitáramos que
         * al menos se repitiera 3 veces, solo recorreríamos hasta 'divisor' / 3):
         */
        for (int i = 1; i <= divisor / 2; i++) {
            if (divisor % i == 0)
                res += sumasAnt[i];
        }

        return res;
    }

    public static long sumaInv(long primerVal, long segundoVal, int longitud, int digitos) {
        /*
         * Calculamos el número de segmentos de longitud 'digitos' que tendré en cada
         * número inválido (longitud / digitos):
         */
        int segmentos = longitud / digitos;

        /*
         * Calculo el primer valor inválido que podría estar dentro del rango,
         * repitiendo los primeros 'digitos' dígitos de 'primerVal' tantas veces como
         * quepan en 'longitud' (variable 'segmentos'):
         */
        long primerInv = Long.parseLong(Long.toString(primerVal).substring(0, digitos).repeat(segmentos));

        /*
         * Ahora calculamos la diferencia que habrá entre cada número inválido dentro de
         * nuestro rango. Por ejemplo, para un número como 2345_2345_2345, tendremos que
         * poner un 1 en las unidades de cada segmento y un 0 en el resto de posiciones:
         * 0001_0001_0001:
         */
        long dif = Long.parseLong(String.format("%0" + digitos + "d", 1).repeat(segmentos));

        /*
         * Calculamos el "techo" de valores inválidos que puedo obtener sumando 'dif'
         * (será el primer número con un dígito más que 'primerVal'):
         */
        long techo = (long) Math.pow(10, longitud);

        // Si el techo es mayor que el límite del rango, lo igualo a este:
        if (segundoVal < techo)
            techo = segundoVal;

        /*
         * Si 'primerInv' está por debajo del extremo inferior del intervalo, le
         * sumamos 1 'dif' para que esté por encima:
         */
        if (primerInv < primerVal)
            primerInv += dif;

        // Ahora, calculamos los inversos que caben entre 'primerInv' y 'techo':
        long numInv = (techo - primerInv) / dif;

        // Si 'primerInv' <= 'techo', incluimos a 'primerInv' en el conteo. Si no,
        // igualamos 'numInv' a 0:
        if (primerInv <= techo)
            numInv++;
        else
            numInv = 0;

        // Devolvemos la suma de todos esos números inválidos:
        long res = (long) (primerInv * numInv + dif * (numInv / 2.0 * (numInv - 1)));

        return res;
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