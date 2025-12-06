import java.io.*;
import java.util.*;

public class D5P1 {
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
        // Creamos un interval tree tomando como nodo raíz el primer intervalo:
        String[] extremosRaiz = sc.nextLine().split("-");
        Nodo raiz = new Nodo(Long.parseLong(extremosRaiz[0]), Long.parseLong(extremosRaiz[1]));
        intervalTree(raiz);

        /*
         * Leemos los IDs de los ingredientes y vemos si caen en algún intervalo dentro
         * del árbol:
         */
        int numIds = 0;
        while (sc.hasNext()) {
            long id = sc.nextLong();
            numIds += consultarId(raiz, id);
        }
        output.append(numIds);
    }

    private static void intervalTree(Nodo raiz) throws IOException {
        // Vamos leyendo cada intervalo hasta encontrar una línea vacía:
        String[] intervalo = sc.nextLine().split("-");
        while (!intervalo[0].isEmpty()) {
            Nodo nuevo = new Nodo(Long.parseLong(intervalo[0]), Long.parseLong(intervalo[1]));
            insertarNodo(raiz, nuevo);
            intervalo = sc.nextLine().split("-");
        }
    }

    private static void insertarNodo(Nodo raiz, Nodo nuevo) {
        /*
         * Como 'nuevo' será hijo de 'raiz', si su extremo superior es mayor que el
         * 'max' de raíz, lo actualizamos:
         */
        if (nuevo.sup > raiz.max)
            raiz.max = nuevo.sup;

        /*
         * Si el extremo inferior de 'nuevo' es menor que el de 'raiz', lo insertamos a
         * su izquierda. Si no, a su derecha:
         */
        if (nuevo.compareTo(raiz) < 0) {
            if (raiz.izq != null)
                insertarNodo(raiz.izq, nuevo);
            else
                raiz.izq = nuevo;
        } else {
            if (raiz.der != null)
                insertarNodo(raiz.der, nuevo);
            else
                raiz.der = nuevo;
        }
    }

    private static int consultarId(Nodo raiz, long id) {
        // Si llego a un callejón sin salida, devuelvo 0:
        if (raiz == null || id > raiz.max)
            return 0;

        // Si encuentro un intervalo que contenga a 'id', devuelvo 1:
        if (raiz.inf <= id && id <= raiz.sup)
            return 1;

        // Exploro el subárbol izquierdo mientras su 'max' sea mayor o igual que mi ID:
        if (raiz.izq != null && raiz.izq.max >= id)
            return consultarId(raiz.izq, id);

        /*
         * Cuando termine de explorar cada subárbol izquierdo, exploro el derecho.
         * Ten en cuenta que, si no he entrado en la condición de "id > raiz.max", y sé
         * que el máximo del subárbol izquierdo es menor que 'id', significa que el
         * máximo del subárbol derecho sí que es mayor o igual que 'id':
         */
        return consultarId(raiz.der, id);
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