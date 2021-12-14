package Estructura.TpFinal.EscapeHouse;

import Estructura.TpFinal.Dominio.*;
import Estructura.TpFinal.Estructuras.Diccionario.AVL.*;
import Estructura.TpFinal.Estructuras.Diccionario.HashAbierto.*;
import Estructura.TpFinal.Estructuras.GrafoEtiquetado.*;
import Estructura.TpFinal.Estructuras.Lineales.*;
import Estructura.TpFinal.Estructuras.Mapeo.HashMapAbierto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;

public class Menu {
    
    static String sOk = "\u001B[32m SI \u001B[0m";
    static String sErr = " \u001B[31m NO \u001B[0m";

    public static void main(String[] args) {
        menu();
    }

    private static void menu(){

        Scanner sc = new Scanner(System.in);
        DiccionarioAVL habitaciones = new DiccionarioAVL();
        DiccionarioAVL desafios = new DiccionarioAVL();
        DiccionarioHash equipos = new DiccionarioHash();
        GrafoEtiq planoCasa = new GrafoEtiq();
        HashMapAbierto desafiosResueltos = new HashMapAbierto();
        HashMapAbierto habitacionesVisitadas = new HashMapAbierto();
        boolean terminar = false;
        while(!terminar){
            System.out.println(
            "--------------------------- MENU ----------------------------\n"+
            "1 - Carga del sistema \n"+
            "2 - ABM \n"+
            "3 - Consultas sobre habitaciones\n"+
            "4 - Consultas sobre Equipos\n"+
            "5 - Consultas sobre Desafios\n"+
            "6 - Mostrar graficos del sistema\n"+
            "7 - Terminar"
        );
        int resp = sc.nextInt();
        switch (resp) {
            case 1:
                cargarInicial(habitaciones, desafios, equipos, planoCasa);
                planoCasa.existeCamino(habitaciones.obtenerDato(7), habitaciones.obtenerDato(18), 230);
            break;
            case 2:
                manejoABM(habitaciones, planoCasa, equipos, desafios);
            break;
            case 3:
                consultasHabitaciones(habitaciones, planoCasa);
            break;
            case 4:
                consultasDesafios(desafios, desafiosResueltos);
            break;
            case 5:
                consultasEquipos(habitaciones, equipos, desafios, planoCasa, habitacionesVisitadas, desafiosResueltos);
            break;
            case 6:
                mostrarSistema(habitaciones, desafios, equipos, planoCasa, desafiosResueltos, habitacionesVisitadas);
            break;
            case 7:
                terminar = true;
            break;
        
            default:
            break;
        }
        }
            


    }

    private static void escribirLog(String elem) {
        try {
            FileWriter escribir = new FileWriter("C:\\Users\\Gian Franco Recalde\\Desktop\\UNCO- Licenciatura en Sistemas\\2 AÑO\\Estructura de Datos\\Historial.txt");

            //Escribimos en el archivo con el metodo write 
            escribir.write(elem + "\n");
            //Cerramos la conexion
            escribir.close();
        } //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            System.out.println("Error al escribir");
        }

    }

    /*---------------------------------------------------------- ----------------- ------------------------------------------------------------*/
    /*---------------------------------------------------------- Carga del sistema ------------------------------------------------------------*/
    /*---------------------------------------------------------- ----------------- ------------------------------------------------------------*/

    private static void cargarInicial(DiccionarioAVL habitaciones, DiccionarioAVL desafios, DiccionarioHash equipos, GrafoEtiq planoCasa){
        String cadena = "", temp;
        StringTokenizer token;
        escribirLog("Carga inicial, iniciada");
        try(
            FileReader fr = new FileReader("C:\\Users\\Gian Franco Recalde\\Desktop\\UNCO- Licenciatura en Sistemas\\2 AÑO\\Estructura de Datos\\OtrosDatos.txt");
            ){
                int valor = fr.read();
                while(valor!=-1){
                    if(valor == 10){
                        valor = fr.read();
                        token = new StringTokenizer(cadena,";");
                        temp = token.nextToken();
                        if(temp.equalsIgnoreCase("H")){
                            // cargar habitacion
                            cargarhabitacion(cadena.substring(2), habitaciones,planoCasa);
                        }else{
                            if(temp.equalsIgnoreCase("E")){
                                // cargar equipo
                                cargarEquipos(cadena.substring(2), equipos);
                            }else{
                                if(temp.equalsIgnoreCase("D")){
                                    // cargar desafio
                                    cargarDesafio(cadena.substring(2),desafios);
                                }else{
                                    if(temp.equalsIgnoreCase("P")){
                                        // cargar arcos
                                        cargarConexiones(cadena.substring(2),planoCasa,habitaciones);
                                    }
                                }
                            }
                        }
                        cadena = "";
                    }
                    cadena += (char)valor;
                    valor = fr.read();
                }
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }
    }

    private static void cargarhabitacion(String cadena, DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        StringTokenizer tokens = new StringTokenizer(cadena, ";");
        String temp = tokens.nextToken();
        int contador = 1;
        if(!habitaciones.existeClave(Integer.parseInt(temp))){
            Habitacion hab = new Habitacion(Integer.parseInt(temp));
            while(tokens.hasMoreTokens()){
                temp = tokens.nextToken();
                    switch (contador) {
                        case 1:
                        hab.setNombre(temp);
                        break;
                        case 2:
                        hab.setPlanta(Integer.parseInt(temp));
                        break;
                        case 3:
                        hab.setM2(Integer.parseInt(temp));
                        break;
                        case 4:
                        hab.setSalidaExt(Boolean.parseBoolean(temp));
                        break;
                        default:
                        break;
                    }
                contador++;
            }
            habitaciones.insertarRecursivo(hab.getCod(),hab);
            planoCasa.insertarVertice(hab);
        }
    }
    
    private static void cargarEquipos(String cadena, DiccionarioHash equipos){
        StringTokenizer tokens = new StringTokenizer(cadena, ";\n");
        String temp = tokens.nextToken();
        int contador = 1;
        if(!equipos.pertenece(temp)){
            Equipo eq = new Equipo(temp);
            while(tokens.hasMoreTokens()){
                temp = tokens.nextToken();
                switch (contador) {
                    case 1:
                        eq.setPtjeExigido(Integer.parseInt(temp));
                    break;
                    case 2:
                        eq.setPtjeTotal(Integer.parseInt(temp));
                    break;
                    case 3:
                        eq.setHab(Integer.parseInt(temp));

                    break;
                    default:
                    
                    break;
                }
                contador++;
            }
            equipos.insertar(eq.getNom(), eq);
        }
    }

    private static void cargarDesafio(String cadena, DiccionarioAVL desafios){
        StringTokenizer tokens = new StringTokenizer(cadena, ";");
        String temp = tokens.nextToken();
        int contador = 1;
        if(!desafios.existeClave(Integer.parseInt(temp))){
            Desafio des = new Desafio(Integer.parseInt(temp));
            while(tokens.hasMoreTokens()){
                temp = tokens.nextToken();
                switch (contador) {
                    case 1:
                        des.setNombre(temp);
                    break;
                    case 2:
                        des.setTipo(temp);
                    break;
                    default:
                    System.out.println("paso por aca: "+temp);
                    break;
                }
                contador ++;
            }
            desafios.insertarRecursivo(des.getPtje(), des);
        }
    }

    private static void cargarConexiones(String cadena, GrafoEtiq planoCasa, DiccionarioAVL habitaciones){
        StringTokenizer tokens = new StringTokenizer(cadena, ";");
        String temp = "";
        int contador = 1;
        Habitacion claveOrigen = null, claveDestino = null;
        while(tokens.hasMoreTokens()){
            temp = tokens.nextToken();
                switch (contador) {
                    case 1:
                        claveOrigen = (Habitacion)habitaciones.obtenerDato(Integer.parseInt(temp));
                    break;
                    case 2:
                        claveDestino = (Habitacion)habitaciones.obtenerDato(Integer.parseInt(temp));
                    break;
                    case 3:
                        planoCasa.insertarArco(claveOrigen, claveDestino, Integer.parseInt(temp));
                    break;
                    default:
                    break;
                }
            contador++;
        }
    }
    
    /*  ---------------------------------------------------- --- ---------------------------------------------------------------------- */
    /*  ---------------------------------------------------- ABM ---------------------------------------------------------------------- */
    /*  ---------------------------------------------------- --- ---------------------------------------------------------------------- */

    private static void manejoABM(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, DiccionarioHash equipos, DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "1 - ABM Habitacion\n"+  
            "2 - ABM Equipos\n"+
            "3 - ABM Desafios\n"
        );
        int resp = sc.nextInt();
        switch (resp){
            case 1:
                habitacion(habitaciones, planoCasa);
            break;
            case 2:
                equipo(equipos,habitaciones);
            break;
            case 3:
                desafio(desafios);
            break;
            default:
                break;
        }
    }

    private static void habitacion(DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "1 - Modificar nombre\n"+
            "2 - Modificar planta\n"+
            "3 - Modificar metros cuadrados\n"+
            "4 - Modificar Salida Exterior\n" +
            "5 - Agregar\n"+
            "6 - Eliminar\n"
        );
        int resp = sc.nextInt();
        System.out.println("Ingrese clave de la habitacion");
        int cod = sc.nextInt();
        switch (resp) {
            case 1:
                System.out.println("Ingrese el nombre");
                String nombre = sc.nextLine();
                
                if(modNombreHab(habitaciones, planoCasa, cod, nombre)){
                    System.out.println("Modificacion realizada");
                }else{
                    System.out.println("No existe la habitacion");
                }
            break;
            case 2:
                System.out.println("Ingrese la planta");
                int planta = sc.nextInt();
            
                if(modPlanta(habitaciones, planoCasa, cod, planta)){
                    System.out.println("Modificacion realizada");
                }else{
                    System.out.println("No existe la habitacion");
                }
            break;
            case 3:
                System.out.println("Ingrese los metros cuadrados");
                int m2 = sc.nextInt();
                
                if(modM2(habitaciones, planoCasa, cod, m2)){
                    System.out.println("Modificacion realizada");
                }else{
                    System.out.println("No existe la habitacion");
                }
            break;
            case 4:
                System.out.println("Ingrese:\n"+
                                    "1 - habitacion con salida exterior\n"+
                                    "0 - habitacion sin salida exterior"
                );
                int aux = sc.nextInt();
                boolean sldaExt = false;
                if(aux==1){
                    sldaExt = true;
                }
                
                if(modSalidaExterior(habitaciones, planoCasa, cod, sldaExt)){
                    System.out.println("Modificacion realizada");
                }else{
                    System.out.println("No existe la habitacion");
                }
            break;
            case 5:
                System.out.println("Ingrese el nombre");
                String resp1 = sc.nextLine();
                System.out.println("Ingrese la planta");
                int resp2 = sc.nextInt();
                System.out.println("Ingrese los metros cuadrados");
                int resp3 = sc.nextInt();
                System.out.println("Ingrese:\n"+
                                    "1 - habitacion con salida exterior\n"+
                                    "0 - habitacion sin salida exterior"
                );
                int aux2 = sc.nextInt();
                boolean resp4 = false;
                if(aux2==1){
                    resp4 = true;
                }
                System.out.println("Ingrese la clave de la habitacion contigua");
                int resp5 = sc.nextInt();
                System.out.println("Ingrese el puntaje para pasar entre habitaciones");
                int resp6 = sc.nextInt();
                if(altaHab(habitaciones, planoCasa, cod, resp1, resp2, resp3, resp4, resp5, resp6)){
                    System.out.println("Habitacion creada");
                }else{
                    System.out.println("Ya existe la habitacion");
                }
            break;
            case 6:
                if(bajaHab(habitaciones, planoCasa, cod)){
                    System.out.println("habitacion eliminada con exito");
                }else{
                    System.out.println("no se encontro la habitacion");
                }        
            break;
            default:
                break;
        }
    }

    private static void equipo(DiccionarioHash equipos, DiccionarioAVL habitaciones){
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "1 - Modificar puntaje exigido\n"+
            "2 - Modificar puntaje total\n"+
            "3 - Modificar puntaje actual\n"+
            "4 - Modificar habitacion\n" +
            "5 - Agregar\n"+
            "6 - Eliminar\n"
        );
        int resp = sc.nextInt();
        System.out.println("Ingrese nombre");
        String nombre = sc.nextLine();
        switch (resp) {
            case 1:
                System.out.println("Ingrese puntaje exigido");
                int ptjeExigido = sc.nextInt();
                if(modPtjeExigido(equipos, nombre, ptjeExigido)){
                    System.out.println("Modificacion con exito");
                }else{
                    System.out.println("El equipo no existe");
                }
            break;
            case 2:
                System.out.println("Ingrese puntaje total");
                int ptjeTotal= sc.nextInt();
                if(modPtjeTotal(equipos, nombre, ptjeTotal)){
                    System.out.println("Modificacion con exito");
                }else{
                    System.out.println("El equipo no existe");
                }
            break;
            case 3:
                System.out.println("Ingrese puntaje actual");
                int ptjeActual = sc.nextInt();
                if(modPtjeAcutal(equipos, nombre, ptjeActual)){
                    System.out.println("Modificacion con exito");
                }else{
                    System.out.println("El equipo no existe");
                }
            break;
            case 4:
                System.out.println("Ingrese clave de habitacion");
                int hab = sc.nextInt();
                if(habitaciones.existeClave(hab)){
                    if(modHabitacion(equipos, nombre, hab)){
                        System.out.println("Modificacion con exito");
                    }else{
                        System.out.println("El equipo no existe");
                    }
                }else{
                    System.out.println("No existe habitacion");
                }
            break;
            case 5:
                System.out.println("Ingrese puntaje exigido");
                int resp2 = sc.nextInt();
                if(altaEquipo(equipos, nombre, resp2)){
                    System.out.println("equipo creado con exito");
                }else{
                    System.out.println("ya existe equipo");
                }
            break;
            case 6:
                if(bajaEquipo(equipos, nombre)){
                    System.out.println("equipo eliminado con exito");
                }else{
                    System.out.println("No se encontro el equipo");
                }        
            break;
            default:
                break;
        }
    }

    private static void desafio(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "1 - Modificar nombre\n"+
            "2 - Modificar tipo\n"+
            "3 - Agregar\n"+
            "4 - Eliminar\n"
        );
        int resp = sc.nextInt();
        System.out.println("Ingrese puntaje");
        int ptje = sc.nextInt();
        switch (resp) {
            case 1:
                System.out.println("Ingrese nombre");
                String nombre = sc.nextLine();
                if(modNomDesafio(desafios, ptje, nombre)){
                    System.out.println("Modificacion con exito");
                }else{
                    System.out.println("El desafio no existe");
                }
            break;
            case 2:
                System.out.println("Ingrese el tipo");
                String tipo = sc.nextLine();
                if(modTipo(desafios, ptje, tipo)){
                    System.out.println("Modificacion con exito");
                }else{
                    System.out.println("El desafio no existe");
                }
            break;
            case 3:
                System.out.println("Ingrese el nombre");
                String resp7 = sc.nextLine();
                System.out.println("Ingrese el tipo");
                String resp8 = sc.nextLine();
                if(altaDesafio(desafios, ptje, resp7, resp8)){
                    System.out.println("Desafio creado con exito");
                }else{
                    System.out.println("El desafio ya existe");
                }
            break;
            case 4:
                if(bajaDesafio(desafios, ptje)){
                    System.out.println("Desafio eliminado");
                }else{
                    System.out.println("No existe desafio");
                }
            break;
            default:
                break;
        }
    }
    
    //  ABM Habitacion
    
    private static boolean modNombreHab(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod, String nombre){
        boolean exito = false;
        Habitacion aux = (Habitacion)habitaciones.obtenerDato(cod);
        Habitacion aux2 = (Habitacion)planoCasa.modificar(aux);
        if(aux != null && aux2 != null){
            aux.setNombre(nombre);
            aux2.setNombre(nombre); 
            exito = true;
            escribirLog("Se modifico el nombre de la habitacion: " + nombre);
        }
        return exito;
    }
    private static boolean modPlanta(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod, int planta){
        boolean exito = false;
        Habitacion aux = (Habitacion)habitaciones.obtenerDato(cod);
        Habitacion aux2 = (Habitacion)planoCasa.modificar(aux);
        if(aux != null && aux2 != null){
            aux.setPlanta(planta);
            aux2.setPlanta(planta);
            exito = true;
            escribirLog("Se modifico la planta de la habitacion: " + planta);
        }
        return exito;
    }
    private static boolean modM2(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod, int m2){
        boolean exito = false;
        Habitacion aux = (Habitacion)habitaciones.obtenerDato(cod);
        Habitacion aux2 = (Habitacion)planoCasa.modificar(aux);
        if(aux!= null && aux2 != null){
            aux.setM2(m2);
            aux2.setM2(m2);
            exito = true;
            escribirLog("Se modifico los metros cuadrados de la habitacion: " + m2);
        }
        return exito;
    }
    private static boolean modSalidaExterior(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod, boolean sldaExt){
        boolean exito = false;
        Habitacion aux = (Habitacion)habitaciones.obtenerDato(cod);
        Habitacion aux2 = (Habitacion)planoCasa.modificar(aux);
        if(aux!= null&& aux2 != null){
            aux.setSalidaExt(sldaExt);
            aux2.setSalidaExt(sldaExt);
            exito = true;
            escribirLog("Se modifico salidaExterior de la habitacion");
        }
        return exito;
    }
    private static boolean bajaHab(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod){
        boolean exito = false;
        if(habitaciones.eliminar(cod)){
            exito = planoCasa.eliminarVertice((Habitacion)habitaciones.obtenerDato(cod));
            escribirLog("Se elimino habitacion");
        }
        return exito;
    }
    private static boolean altaHab(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int cod, String nombre, int planta, int m2, boolean sldaExt, int habDestino,int etq){
        boolean exito = false;
        if(!habitaciones.existeClave(cod)){
            Habitacion newHab = new Habitacion(cod, nombre, planta, m2, sldaExt); 
            habitaciones.insertarRecursivo(cod, newHab);
            planoCasa.insertarVertice(newHab);
            Habitacion destino = (Habitacion)habitaciones.obtenerDato(habDestino);
            if(destino != null){
                planoCasa.insertarArco(newHab, destino, etq);
                exito = true;
                escribirLog("Se creo una habitcion");
            }
        }
        return exito;
    }
   
    // ABM Equipos

    private static boolean modPtjeExigido(DiccionarioHash equipos, String nombre, int ptjeExigido){
        boolean exito = false;
        Equipo aux = (Equipo)equipos.obtenerInformacion(nombre);
        if(aux!=null){
            aux.setPtjeExigido(ptjeExigido);
            exito = true;
            escribirLog("Se modifico el ptjeExigido del equipo");
        }
        return exito;
    }
    private static boolean modPtjeTotal(DiccionarioHash equipos,String nombre, int ptjeTotal){
        boolean exito = false;
        Equipo aux = (Equipo)equipos.obtenerInformacion(nombre);
        if(aux!= null){
            aux.setPtjeTotal(ptjeTotal);
            exito = true;
            escribirLog("Se modifico el ptjeTotal del equipo");
        }
        return exito;
    }
    private static boolean modPtjeAcutal(DiccionarioHash equipos, String nombre, int ptjeActual){
        boolean exito = false;
        Equipo aux = (Equipo)equipos.obtenerInformacion(nombre);
        if(aux!=null){
            aux.setPtjeActual(ptjeActual);
            exito = true;
            escribirLog("Se modifico el ptje acutal del equipo");
        }
        return exito;
    }
    private static boolean modHabitacion(DiccionarioHash equipos, String nombre, int habitacion){
        boolean exito = false;
        Equipo aux = (Equipo)equipos.obtenerInformacion(nombre);
        if(aux!=null){
            aux.setHab(habitacion);
            exito = true;
            escribirLog("Se modifico la habitacion del equipo");
        }
        return exito;
    }
    private static boolean altaEquipo(DiccionarioHash equipos, String nombre, int ptjeExigido){ 
        escribirLog("Se creo equipo");
        return equipos.insertar(nombre, new Equipo(nombre, ptjeExigido));
    }
    private static boolean bajaEquipo(DiccionarioHash equipos, String nombre){
        escribirLog("Se elimino equipo");
        return equipos.eliminar(nombre);
    }
    
    // ABM Desafios
    
    private static boolean modNomDesafio(DiccionarioAVL desafios, int ptje, String nombre){
        boolean exito = false;
        Desafio aux = (Desafio)desafios.obtenerDato(ptje);
        if(aux != null){
            aux.setNombre(nombre);
            exito = true;
            escribirLog("Se modifico el nombre del desafio");
        }
        return exito;
    }
    private static boolean modTipo(DiccionarioAVL desafios, int ptje, String tipo){
        boolean exito = false;
        Desafio aux = (Desafio)desafios.obtenerDato(ptje);
        if(aux!=null){
            aux.setTipo(tipo);
            exito = true;
            escribirLog("Se modifico el tipo del desafio");
        }
        return exito;
    }
    private static boolean altaDesafio(DiccionarioAVL desafios, int ptje, String nombre, String tipo){
        Desafio newDes = new Desafio(nombre, ptje, tipo);
        escribirLog("Se creo el desafio");
        return desafios.insertarRecursivo(ptje, newDes);
    }
    private static boolean bajaDesafio(DiccionarioAVL desafios, int ptje){
        escribirLog("Se elimino el desafio");
        return desafios.eliminar(ptje);
    }
    
    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */
    /* -------------------------------------------------- Consulta Habitaciones ----------------------------------------------------- */
    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */
 
    private static void consultasHabitaciones(DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while(!salir){
            System.out.println(
                "1 - Mostrar informacion\n"+
                "2 - Mostar las habitaciones contiguas\n"+
                "3 - Verificar la existencia de un camino entre dos habitaciones acumulando una cantidad de puntaje\n"+
                "4 - Mostrar todos los caminos entre dos habitaciones sin pasar por una en especifico y sin superar un puntaje\n"+
                "5 - Mostrar el maximo puntaje que se necesita para pasar de una habitacion a otra\n"+
                "6 - Terminar"
            );
            int resp = sc.nextInt();
            System.out.println("Ingrese clave de la habitacion origen");
            int cod = sc.nextInt();
            switch (resp) {
                case 1:
                    System.out.println(mostrarHabitacion(cod, habitaciones));
                break;
                case 2:
                    System.out.println(habitacionesContiguas(cod, habitaciones, planoCasa));
                break;
                case 3:
                    System.out.println("Ingrese clave de habitacion destino");
                    int codHab1 = sc.nextInt();
                    System.out.println("Ingrese un puntaje");
                    int ptje = sc.nextInt();
                    if(esPosibleLLegar(cod, codHab1, ptje, habitaciones, planoCasa)){
                        System.out.println("Existe camino");
                    }else{
                        System.out.println("No existe camino");
                    }
                break;
                case 4:
                System.out.println("Ingrese clave de habitacion destino");
                int codHab2 = sc.nextInt();
                System.out.println("Ingrese clave de habitacion por la que no quiere pasar");
                int codHab3 = sc.nextInt();
                System.out.println("Ingrese un puntaje limite");
                int ptjeLimit = sc.nextInt();
                System.out.println(sinPasarPor(habitaciones, planoCasa, cod, codHab2, codHab3, ptjeLimit));
                break;
                case 5:
                System.out.println("Ingrese clave de habitacion destino");
                int codHab4 = sc.nextInt();
                System.out.println(maximoPuntaje(cod, codHab4, habitaciones, planoCasa));
                break;
                case 6:
                    salir = true;
                break;
                default:
                    break;
            }
        }
    }

    private static String mostrarHabitacion(int cod, DiccionarioAVL habitaciones){
        String cadena = "";
        Habitacion aux = (Habitacion)habitaciones.obtenerDato(cod);
        if(aux != null){
            // habitacion existe
            cadena += "Nombre de la habitacion: "+aux.getNombre()+" Planta: "+aux.getPlanta()+ " Metros cuadrados: "+aux.getM2()+" Tiene salida exterior: "+((aux.getSalidaExt()) ? sOk : sErr);
        }
        return cadena;
    }
    
    private static String habitacionesContiguas(int cod, DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        String cadena = "";
        Lista lis;
        int auxPtje,i=1;
        Habitacion auxHab = (Habitacion)habitaciones.obtenerDato(cod);
        if(auxHab != null){
            lis = planoCasa.verticesContiguos(auxHab);
            if(!lis.esVacia()){
                while(i<=lis.longitud()){
                    auxHab = (Habitacion)lis.recuperar(i);
                    i++;
                    auxPtje = (int)lis.recuperar(i);
                    i++;
                    cadena += "Para pasar a la habitacion: "+auxHab.getNombre()+" es necesario la siguiente cantidad de puntaje: "+auxPtje;
                }
            }
        }
        return cadena;
    }
    
    private static boolean esPosibleLLegar(int codHab1, int codHab2, int ptje, DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        // Busca si existe camino de hab1 a hab2 con cierta cantidad de ptje, pasado por paramentro. Si existe devuelve verdadero y en caso contrario falso.
        boolean existeCamino = false;
        Habitacion hab1 = (Habitacion)habitaciones.obtenerDato(codHab1);
        Habitacion hab2 = (Habitacion)habitaciones.obtenerDato(codHab2);
        if(hab1 != null && hab2 != null){
            existeCamino = planoCasa.existeCamino(hab1, hab2, ptje);
        }
        return existeCamino;
    }
    
    private static String sinPasarPor(DiccionarioAVL habitaciones, GrafoEtiq planoCasa, int codHab1, int codHab2, int codHab3, int ptjeLimite){
        String cadena = "";
        Lista lis, aux;
        int i;
        Habitacion hab1 = (Habitacion)habitaciones.obtenerDato(codHab1);
        Habitacion hab2 = (Habitacion)habitaciones.obtenerDato(codHab2);
        Habitacion hab3 = (Habitacion)habitaciones.obtenerDato(codHab3);
        if(hab1 != null && hab3 != null && hab2 != null){
            lis = planoCasa.caminos(hab1, hab2, hab3, ptjeLimite);
            if(!lis.esVacia()){
                for(i=1; i<=lis.longitud(); i++){
                    aux = (Lista)lis.recuperar(i);
                    if(!aux.esVacia()){
                        cadena+=aux.toString()+"\n";
                    }
                }
            }
        }
        return cadena;
    } 
    
    private static int maximoPuntaje(int codHab1, int codHab2, DiccionarioAVL habitaciones, GrafoEtiq planoCasa){
        int ptjeMax = -1;
        Habitacion hab1 = (Habitacion)habitaciones.obtenerDato(codHab1);
        Habitacion hab2 = (Habitacion)habitaciones.obtenerDato(codHab2);
        if(hab1 != null && hab2 != null){
            ptjeMax = (int)planoCasa.valorDelArco(hab1, hab2);
        }
        return ptjeMax;
    }

    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */
    /* -------------------------------------------------- Consulta Desafios ----------------------------------------------------- */
    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */

    private static void consultasDesafios(DiccionarioAVL desafios, HashMapAbierto desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while(!salir){
            System.out.println(
                "1 - Mostrar informacion\n"+
                "2 - Mostar desafios resueltos\n"+
                "3 - Verificar desafios resueltos\n"+
                "4 - Mostrar desafios de un tipo y que se encuentren entre dos puntajes\n"+
                "5 - Terminar"
            );
            int resp = sc.nextInt();
            switch (resp) {
                case 1:
                    System.out.println("Ingrese el puntaje del desafio");
                    int ptje = sc.nextInt();
                    System.out.println(mostrarDesafios(ptje, desafios);
                break;
                case 2:
                    System.out.println("Ingrese nombre del equipo");
                    String nombre = sc.nextLine();
                    System.out.println(mostrarDesafiosResueltos(nombre, desafiosResueltos));     
                break;
                case 3:
                    System.out.println("Ingrese nombre del equipo");
                    String n = sc.nextLine();
                    System.out.println("Ingrese puntaje del desafio");
                    int p =  sc.nextInt();
                    if(verificarDesafioResuelto(n, p, desafiosResueltos)){
                        System.out.println("El desafio ya fue resuelto");
                    }else{
                        System.out.println("El desafio no fue resuelto");
                    }
                break;
                case 4:
                    System.out.println("Ingrese primer puntaje");
                    int p1 = sc.nextInt();
                    System.out.println("Ingrese segundo puntaje");
                    int p2 = sc.nextInt();
                    System.out.println("Ingrese tipo de desafio");
                    String tipo = sc.nextLine();
                    System.out.println(mostrarDesafiosTipo(p1, p2, tipo, desafios));
                    break;
                case 5:
                    salir = true;
                break;
            
                default:
                    break;
            }
        }
    }

    private static String mostrarDesafios(int ptje, DiccionarioAVL desafios){
        String cadena = "";
        Desafio auxDes = (Desafio)desafios.obtenerDato(ptje);
        if(auxDes != null){
            cadena += "Nombre del desafio: "+auxDes.getNom()+" Tipo de desafio: "+auxDes.getTipo()+ " Puntaje del desafio: " + ptje;
        }
        return cadena;
    }

    private static String mostrarDesafiosResueltos(String nombre, HashMapAbierto desafiosResueltos){
        Lista lis = desafiosResueltos.obtenerValores(nombre);
        String cadena = "";
        if(!lis.esVacia()){
            Desafio aux;
            int i;
            cadena += "Los desafios resueltos del equipo "+nombre+" son: ";
            for(i=1;i<=lis.longitud();i++){
                aux = (Desafio)lis.recuperar(i);
                cadena += aux.getNom()+ ", "; 
            }
        }
        return cadena;
    }

    private static boolean verificarDesafioResuelto(String nomEq, int ptjeDes, HashMapAbierto desafiosResueltos){
        // Verifica si el desafio pasado por parametro esta se encuentra en hashMap de desafios resueltos, devolvera verdadero si lo encunetra y falso en caso contario
        Lista lis = desafiosResueltos.obtenerValores(nomEq);
        boolean resuelto = false;
        if(!lis.esVacia()){
            Desafio aux;
            int i=1;
            while(i<=lis.longitud() && !resuelto){
                aux = (Desafio)lis.recuperar(i);
                resuelto = aux.getPtje() == ptjeDes;
                i++; 
            }
        }
        return resuelto;
    }

    private static String mostrarDesafiosTipo(int ptjeMin, int ptjeMax, String tipoDesafio, DiccionarioAVL desafios){
        String cadena = "";
        Desafio aux;
        Cola desafiosDelRango = desafios.listarRango(ptjeMin, ptjeMax);
        while(!desafiosDelRango.esVacia()){
            aux = (Desafio)desafiosDelRango.obtenerFrente();
            if(aux.getTipo().equals(tipoDesafio)){
                cadena += aux.getNom() + ", ";
            }
            desafiosDelRango.sacar();
        }
        return cadena;
    }

    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */
    /* -------------------------------------------------- Consulta Equipos ----------------------------------------------------- */
    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */

    private static void consultasEquipos(DiccionarioAVL habitaciones, DiccionarioHash equipos, DiccionarioAVL desafios, GrafoEtiq planoCasa, HashMapAbierto habitacionesVisitadas, HashMapAbierto desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while(!salir){
            System.out.println(
                "1 - Mostrar informacion\n"+
                "2 - Jugar desafio\n"+
                "3 - Verificar pasaje de una habitacion a otra\n"+
                "4 - Consultar si puede salir de la casa\n"+
                "5 - Mostrar todos los desafios que se deberian resolver para pasar de una habitacion a otra\n"+
                "6 - Terminar"
            );
            int resp = sc.nextInt();
            switch (resp) {
                case 1:
                    System.out.println("Ingrese nombre del equipo");
                    String n = sc.nextLine();
                    System.out.println(mostrarInfoEquipo(n, equipos, habitaciones));
                break;
                case 2:
                    System.out.println("Ingrese nombre del equipo");
                    String n1 = sc.nextLine();
                    System.out.println("Ingrese puntaje del desafio");
                    int ptje = sc.nextInt();
                    if(jugarDesafio(n1, ptje, desafios, equipos, desafiosResueltos)){
                        System.out.println("Desafio jugado");
                    }else{
                        System.out.println("Desafio no jugado");
                    } 
                break;
                case 3:
                    System.out.println("Ingrese nombre del equipo");
                    String n2 = sc.nextLine();
                    System.out.println("Ingrese clave de la habitacion");
                    int c1 = sc.nextInt();
                    if(pasarAHabitacion(n2, c1, habitaciones, equipos, planoCasa, habitacionesVisitadas)){
                        System.out.println("Pudo pasar de habitacion");
                    }else{
                        System.out.println("No pudo pasar de habitacion");
                    }
                break;
                case 4:
                    System.out.println("Ingrese nombre del equipo");
                    String n3 = sc.nextLine();
                    if(puedeSalir(n3, habitaciones, equipos)){    
                        System.out.println("Puede salir de la casa");
                    }else{
                        System.out.println("No puede salir de la casa");
                    }
                break;
                case 5:
                    
                break;
                case 6:
                    salir = true;
                break;
                default:
                    break;
            }
        }
    }

    private static String mostrarInfoEquipo(String nombre, DiccionarioHash equipos, DiccionarioAVL habitaciones){
        String cadena = "";
        Equipo aux = (Equipo)equipos.obtenerInformacion(nombre);
        Habitacion aux2 = (Habitacion)habitaciones.obtenerDato(aux.getHab());
        if(aux != null){
            cadena += "Nombre del equipo: "+ nombre+ " Puntaje exigido: "+ aux.getPtjeExigido()+ " Puntaje total: " + aux.getPtjeTotal()+ " Puntaje actual: " +aux.getPtjeActual()+ " Habitacion: "+ aux2.getNombre();  
        }
        return cadena;
    }

    private static boolean jugarDesafio(String nombre, int ptje, DiccionarioAVL desafios, DiccionarioHash equipos, HashMapAbierto desafiosResueltos){
        boolean exito = false;
        Equipo auxEq = (Equipo)equipos.obtenerInformacion(nombre);
        if(auxEq != null && desafios.existeClave(ptje)){
            auxEq.setPtjeActual(auxEq.getPtjeActual()+ptje);
            desafiosResueltos.asociar(nombre, (Desafio)desafios.obtenerDato(ptje));
            exito = true;
        }
        return exito;
    }

    private static boolean pasarAHabitacion(String nombre, int cod, DiccionarioAVL habitaciones, DiccionarioHash equipos, GrafoEtiq planoCasa, HashMapAbierto habitacionesVisitadas){
        boolean exito = false, contiguas = false;
        Lista lis;
        int i = 1;
        Equipo eq = (Equipo)equipos.obtenerInformacion(nombre);
        Habitacion hab = (Habitacion)habitaciones.obtenerDato(cod),aux;
        if(eq != null && hab != null){
            if(eq.getHab() != cod ){
                // verifico que la habitacion en la que esta el equipo no es la misma por la cual se esta preguntando
                lis = planoCasa.verticesContiguos(hab);
                while(i<=lis.longitud() && !contiguas){
                    // Verifico si existe la habitacion en las habitaciones contiguas a la pasada por paramentro
                    aux = (Habitacion)lis.recuperar(i);
                    contiguas = aux.getCod() == eq.getHab();
                    i++;
                }
                if(contiguas && (int)planoCasa.valorDelArco(hab, (Habitacion)habitaciones.obtenerDato(eq.getHab())) <= (eq.getPtjeTotal()+eq.getPtjeActual()) && !habitacionesVisitadas.pertenece(nombre, hab)){
                    // la habitacion contigua existe
                    // Compruebo que el equipo pueda pasar de habitacion con el ptje total mas el actual obtenido por los desafios resueltos en la habitacion actual
                    // Verifico que la haabitacion no haya sido visitada anteriormente
                    exito = true;
                    eq.setPtjeTotal(eq.getPtjeTotal()+eq.getPtjeActual()); // ptje total que tiene el equipo + ptje acumulado de los desafios resueltos en la habitacion actual
                    eq.setPtjeActual(0);
                    eq.setHab(hab.getCod());
                    habitacionesVisitadas.asociar(nombre, hab);
                }
            }
        }
        return exito;
    }

    private static boolean puedeSalir(String nombre, DiccionarioAVL habitaciones, DiccionarioHash equipos){
        boolean puedeSalir = false;
        Equipo eq = (Equipo)equipos.obtenerInformacion(nombre);
        Habitacion hab;
        if(eq != null){
            hab = (Habitacion)habitaciones.obtenerDato(eq.getHab());
            if(hab.getSalidaExt()){
                puedeSalir = (eq.getPtjeTotal()+eq.getPtjeActual())>=eq.getPtjeExigido();
                // el ptje actual es igual o mayor al exigido o la suma del ptje total y actual es igual o mayor al exigido
            }
        }
        return puedeSalir;
    }

    private static String posiblesDesafios(String nombre, int codHab, DiccionarioAVL habitaciones, DiccionarioAVL desafios, DiccionarioHash equipos, GrafoEtiq planoCasa){
        String cadena = "";
        Lista lis;
        Desafio aux;
        int i;
        Equipo eq = (Equipo)equipos.obtenerInformacion(nombre);
        Habitacion hab = (Habitacion)habitaciones.obtenerDato(eq.getHab());
        Habitacion hab2 = (Habitacion)habitaciones.obtenerDato(codHab);
        if(eq != null && hab != null && hab2 != null){
            int ptje = (int)planoCasa.valorDelArco(hab, hab2);
            /* if(ptje != -1){
                for(i=1;i<=lis.longitud();i++){
                    aux = (Desafio)lis.recuperar(i);
                    cadena += aux.getNom() + ", ";
                }
            }else{
                cadena += "Las habitaciones no son contiguas";
            } */
        }
        return cadena;
    }

    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */
    /* -------------------------------------------------- Consulta General ----------------------------------------------------- */
    /* -------------------------------------------------- --------------------- ----------------------------------------------------- */

    public static void mostrarSistema(DiccionarioAVL habitaciones, DiccionarioAVL desafios, DiccionarioHash equipos, GrafoEtiq planoCasa, HashMapAbierto desafiosResueltos, HashMapAbierto habitacionesVisitadas){
        System.out.println(habitaciones.toString());
        System.out.println();
        System.out.println(equipos.toString());
        System.out.println();
        System.out.println(desafios.toString());
        System.out.println();
        System.out.println(planoCasa.toString());
        System.out.println();
        System.out.println(desafiosResueltos.toString());
        System.out.println();
        System.out.println(habitacionesVisitadas.toString());
    }
}
