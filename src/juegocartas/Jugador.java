package juegocartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Jugador {
    
    private int TOTAL_CARTAS = 10;
    private int MARGEN_SUPERIOR = 10;
    private int MARGEN_IZQUIERDA = 10;
    private int DISTANCIA = 60;
    public int[] Indices_generados = new int[TOTAL_CARTAS];
    private Set<Integer> numeros_escalera = new HashSet<>();
    private Set<Integer> numeros_grupos = new HashSet<>();
    
    private List<Integer> listaOrdenada = new ArrayList<>(numeros_escalera);
    
    public Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r;
    
    public Jugador() {
        r = new Random();
    }

    //vector de cartas con las cartas repartidas
    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
            Indices_generados[i] = cartas[i].getIndice();
            
        }
        
    }
    
    public void mostrar_vector() {
        String mensaje = "";
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            mensaje += Indices_generados[i] + " ";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    //Mostrar cartas pero sin que sea una encima de otra
    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        //for (int i = 0; i < cartas.length; i++) {

        int posicion_x = MARGEN_IZQUIERDA;
        for (Carta c : cartas) {

            //cartas[i].mostrar(pnl, 10, 5);
            c.mostrar(pnl, posicion_x, MARGEN_SUPERIOR);
            posicion_x += DISTANCIA;
        }
        pnl.repaint();
    }
    
    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        //verificar que haya cartas
        if (cartas[0] != null) {
            //iniciar los contadores
            int[] contadores = new int[NombreCarta.values().length];

            //recorrer las cartas para contarlas de acuerdo a su nombre
            for (Carta c : cartas) {
                contadores[c.ObtenerNombre().ordinal()]++;
                
            }

            //contar cuantos grupos se hallaron
            int totalGrupos = 0;
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] > 1) {
                    totalGrupos++;
                }
            }
            
            if (totalGrupos > 0) {
                mensaje = "Los grupos encontrador fueron:\n";
                for (int i = 0; i < contadores.length; i++) {
                    if (contadores[i] > 1) {
                        mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                    }
                }
            }
        } else {
            mensaje = "No se han repartido cartas";
        }
        return mensaje;
    }
    
    public int[] ordenamiento_burbuja(int v[]) {
        int temp;
        for (int i = 1; i < v.length; i++) {
            for (int y = 0; y < v.length - 1; y++) {
                if (v[y] > v[y + 1]) {
                    temp = v[y];
                    v[y] = v[y + 1];
                    v[y + 1] = temp;
                }
            }
        }
        return v;
    }
    
    public void Comparador(int v[]) {

        // Con esto reinicio los acumuladores en cada llamada al método para mostrar las escaleras
        numeros_escalera.clear();
        
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (i < TOTAL_CARTAS - 1 && v[i] >= 1 && v[i] <= 13 && v[i] + 1 == v[i + 1]) {
                
                if (v[i] + 1 == v[i + 1] && v[i] != 13) {
                    numeros_escalera.add(v[i]);
                    numeros_escalera.add(v[i] + 1);
                    
                } else if (v[i] == 13 && v[i + 1] == 14) {
                    i++;
                }
                
            } else if (i < TOTAL_CARTAS - 1 && v[i] >= 14 && v[i] <= 26 && v[i] + 1 == v[i + 1]) {
                
                if (v[i] + 1 == v[i + 1] && v[i] != 26) {
                    numeros_escalera.add(v[i]);
                    numeros_escalera.add(v[i] + 1);
                    
                } else if (v[i] == 26 && v[i + 1] == 27) {
                    i++;
                }
                
            } else if (i < TOTAL_CARTAS - 1 && v[i] >= 27 && v[i] <= 39 && v[i] + 1 == v[i + 1]) {
                if (v[i] + 1 == v[i + 1] && v[i] != 39) {
                    numeros_escalera.add(v[i]);
                    numeros_escalera.add(v[i] + 1);
                    
                } else if (v[i] == 39 && v[i + 1] == 40) {
                    i++;
                }
                
            } else if (i < TOTAL_CARTAS - 1 && v[i] + 1 == v[i + 1]) {
                numeros_escalera.add(v[i]);
                numeros_escalera.add(v[i] + 1);
                
            } else if (i == TOTAL_CARTAS - 1 && v[i] == v[i - 1] + 1) {
                numeros_escalera.add(v[i]);
                
            }
            
        }
        List<Integer> listaOrdenada = new ArrayList<>(numeros_escalera);
        Collections.sort(listaOrdenada);
//        System.out.println("Lista de numeros: ");
//        for (Integer numeros : listaOrdenada) {
//            System.out.println(numeros);
//        }

    }
    
    public String CantidadPinta() {
        String mensaje = "Las escaleras encontradas fueron:\n";
        int totalgrupos = 0;
        List<NombreCarta> nombresCartasP = new ArrayList<>();
        Map<Pinta, List<Carta>> sublistaPinta = new HashMap<>();
        for (Pinta pinta : Pinta.values()) {
            sublistaPinta.put(pinta, new ArrayList<>());
        }
        
        for (Integer numeros : numeros_escalera) {
            for (Carta c : cartas) {
                if (c.getIndice() == numeros) {
                    NombreCarta nombreCarta = c.ObtenerNombre();
                    nombresCartasP.add(nombreCarta);
                    sublistaPinta.get(c.obtenerPinta()).add(c);
                    break;
                }
                
            }
        }
        
        System.out.println("Nombres de las cartas en la escalera:");
        for (NombreCarta nombreCarta : nombresCartasP) {
            System.out.println(nombreCarta);
        }
        
        for (Pinta pinta : Pinta.values()) {
            List<Carta> sublista = sublistaPinta.get(pinta);
            if (!sublista.isEmpty()) {
                int cantidadCartas = sublista.size();
                Grupo grupo = Grupo.values()[cantidadCartas];
                mensaje += grupo + " de " + pinta + ":  ";
                mensaje += "\n ";
                for (Carta carta : sublista) {
                    mensaje += carta.ObtenerNombre() + " ";
                    
                    totalgrupos++;
                }
                mensaje += "\n";
                
            }
        }
        if (totalgrupos == 0) {
            mensaje = "No se encontraron grupos de pintas";
        }
        
        JOptionPane.showMessageDialog(null, mensaje);
        return mensaje;
    }
    
    public void numeros_listas() {
        List<Integer> listaGrupos = new ArrayList<>(numeros_grupos);
        List<NombreCarta> nombresCartas = new ArrayList<>();
        Set<NombreCarta> nombresCartasG = new HashSet<>();
        List<NombreCarta> nombresCartasP = new ArrayList<>();
        Map<Pinta, List<Carta>> sublistaPinta = new HashMap<>();
        
        for (Pinta pinta : Pinta.values()) {
            sublistaPinta.put(pinta, new ArrayList<>());
        }
        
        for (Integer numeros : numeros_escalera) {
            for (Carta c : cartas) {
                if (c.getIndice() == numeros) {
                    NombreCarta nombreCarta = c.ObtenerNombre();
                    if (!nombresCartasP.contains(nombreCarta)) {
                        nombresCartasP.add(nombreCarta);
                    }
                    sublistaPinta.get(c.obtenerPinta()).add(c);
                    break;
                }
            }
        }
        
        System.out.println("Nombres de las cartas en la escalera:");
        for (NombreCarta nombreCarta : nombresCartasP) {
            System.out.println(nombreCarta);
        }
        
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            
            Carta carta = cartas[i];
            NombreCarta nombreCarta = carta.ObtenerNombre();
            nombresCartas.add(nombreCarta);
            
        }
        System.out.println("Nombre de cada carta");
        for (NombreCarta c : nombresCartas) {
            System.out.println(c);
            
        }
        
        System.out.println("Grupos de Cartas repetidos");
        Map<NombreCarta, Integer> contadorCartas = new HashMap<>();
        for (NombreCarta c : nombresCartas) {
            contadorCartas.put(c, contadorCartas.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<NombreCarta, Integer> entry : contadorCartas.entrySet()) {
            NombreCarta carta = entry.getKey();
            int cantidad = entry.getValue();
            if (cantidad > 1) {
                for (int i = 0; i < cantidad; i++) {
                    nombresCartasG.add(carta);
                    System.out.println(carta);
                }
                
            }
        }

        // Creo la lista con las cartas sobrantes
        Set<NombreCarta> nombresSobrantes = new HashSet<>(nombresCartas);
        nombresSobrantes.removeAll(nombresCartasP);
        nombresSobrantes.removeAll(nombresCartasG);
        
        System.out.println("Cartas sobrantes:");
        for (NombreCarta cartaSobrante : nombresSobrantes) {
            System.out.println(cartaSobrante);
        }
        int puntajeTotal = 0;
        int valor = 0;
        for (NombreCarta nombre : nombresSobrantes) {
            valor = NombreCartaValor.valueOf(nombre.name()).getValor();
            puntajeTotal += valor;
        }
        
        if (valor > 0) {
            JOptionPane.showMessageDialog(null, "Su puntaje total es " + puntajeTotal);
        } else if (valor == 0) {
            JOptionPane.showMessageDialog(null, "No tienes cartas sobrantes");
        }
        
    }
    
}
