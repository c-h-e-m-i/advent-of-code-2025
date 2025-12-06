import java.io.*;
import java.util.*;

public class D2P1 {
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

            // Calculo el primer valor inválido que podría estar dentro del rango:
            long primerInv;

            // > Si la longitud del extremo inferior del rango es impar, 'primerVal' será
            // del tipo "10..010..0":
            if (longitud % 2 == 1) {
                primerInv = (long) (Math.pow(10, longitud) + Math.pow(10, longitud / 2));
            }

            // > Si no, será la primera mitad del extremo inferior repetida 2 veces:
            else {
                primerInv = Long.parseLong(rangos[i].substring(0, longitud / 2).repeat(2));
            }

            /*
             * Ahora viene la parte interesante. Si, por ejemplo, nuestro 'primerInv' es de
             * 6 dígitos (digamos 564564), el siguiente valor inválido será ese más 1001:
             * 565565. Es decir, elevamos 10 a la mitad de la longitud del primer inválido,
             * le sumamos 1, y dicho resultado se lo sumamos al inválido. Por tanto, la suma
             * de todos los inválidos desde 'primerInv' será del tipo 564564*k + (1001*0 +
             * 1001*1 + ... + 1001*(k-1)). La serie aritmética entre paréntesis se puede
             * reducir, por números triangulares, en 1001 * [k / 2 * (k-1)] (pues tendremos
             * k / 2 parejas que suman k).
             * 
             * Esto se cumplirá hasta que lleguemos a un número de 7 dígitos. Como ningún
             * número de longitud impar puede ser inválido, saltaremos directamente a los
             * números de 8 dígitos, y repetiremos el proceso:
             */

            // > Empezamos calculando el número de dígitos de 'primerInv' (en función de la
            // longitud del extremo inferior del rango):
            int digitos = longitud % 2 == 0 ? longitud : longitud + 1;

            // > Ahora calculamos el "techo" hasta el cual podemos llegar sumando
            // 10^(digitos / 2) + 1:
            long techo = (long) Math.pow(10, digitos);

            // Repetiremos este proceso hasta que 'primerInv' se pase de 'segundoVal':
            while (primerInv <= segundoVal) {
                // > Si el "techo" es mayor que el extremo superior del rango, lo acotamos a
                // este:
                if (segundoVal < techo)
                    techo = segundoVal;

                // > Calculamos el valor que hay entre cada número inválido en función de los
                // digitos de primerInv:
                long dif = (long) Math.pow(10, digitos / 2) + 1;

                /*
                 * > Si 'primerInv' está por debajo del extremo inferior del intervalo (lo cual
                 * puede pasar si lo calculamos duplicando la primera mitad de este último), le
                 * sumamos 1 'dif' para que esté por encima:
                 */
                if (primerInv < primerVal)
                    primerInv += dif;

                // > Ahora, calculamos los inversos que caben entre 'primerInv' y 'techo':
                long numInv = (techo - primerInv) / dif;

                // > Si 'primerInv' <= 'techo' ('numInv' es positivo o 0, aunque ¡OJO!, podría
                // ser 0 también si 'primerInv' es mayor que 'techo' pero la diferencia es menor
                // que 'dif', por eso en el 'if' tenemos que comparar explícitamente 'primerInv'
                // con 'techo' en vez de consultar el valor de 'numInv'), incluimos a
                // 'primerInv' en el conteo. Si no, igualamos 'numInv' a 0:
                if (primerInv <= techo)
                    numInv++;
                else
                    numInv = 0;

                // > Sumamos a 'res' el valor de todos esos números inválidos:
                res += primerInv * numInv + dif * (numInv / 2.0 * (numInv - 1));

                /*
                 * > Actualizamos el techo y ponemos 'primerInv' al primer valor inválido de 2
                 * dígitos más:
                 */
                techo *= 100;
                digitos += 2;
                primerInv = (long) (Math.pow(10, digitos - 1) + Math.pow(10, (digitos - 1) / 2));
            }
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
