import java.util.Scanner;
/** Clase que define el menu principal de nuestro programa
* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
*/
public class Main {

  /** Metodo principal
  * @param args
  */
  public static void main(String[] args) {
    // Se crean actividades para insertar en el backlog
    Actividad a1 = new Actividad("act1", 10, 2);
    Actividad a2 = new Actividad("act2",10,1);
    Actividad a3 = new Actividad("act3",10,4);
    Actividad a4 = new Actividad("act4",9,2);
    Actividad a5 = new Actividad("act5",9,10);
    Actividad a6 = new Actividad("act6",5,6);
    Actividad a7 = new Actividad("act7",5,4);
    Actividad a8 = new Actividad("act8",2,1);
    // Se inicializa el backlog
    Backlog backlog = new Backlog();
    // Se agregan las actividades creadas al backlog
    backlog.insertBacklog(a1);
    backlog.insertBacklog(a2);
    backlog.insertBacklog(a3);
    backlog.insertBacklog(a4);
    backlog.insertBacklog(a5);
    backlog.insertBacklog(a6);
    backlog.insertBacklog(a7);
    backlog.insertBacklog(a8);
    // Se muestra por pantalla el backlog
    System.out.println("El backlog contiene las siguientes actividades");
    backlog.toString();
    System.out.println(" Ingrese la duracion en dias de su sprint: ");
    // Se solicita al usuario el numero de dias que dura el sprint
    Scanner var = new Scanner(System.in);
    int capacity = var.nextInt();
    // Se inicializa el sprint con su capacidad
    // Asumimos una cantidad de 8 horas por dia
    Sprint sprint = new Sprint(capacity*8);
    // Se completa el sprint
    int result = sprint.completeSprint(backlog);
    // Se muestra por pantalla el maximo costo que se puede sumar en el sprint
    System.out.println("Resultado: " + result);
  }// Fin del metodo
}// Fin de la clase
