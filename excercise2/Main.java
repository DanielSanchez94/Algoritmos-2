import java.util.Scanner;
/** Clase que define el menu principal de nuestro programa
* @author: Armas Lucas, Sanchez Daniel
*/
public class Main {

  /** Metodo principal
  * @param args
  */
  public static void main(String[] args) {
    // Se crean actividades para insertar en el backlog
    Actividad a = new Actividad("A",1,2);
    Actividad b = new Actividad("B",2,3);
    Actividad c = new Actividad("C",3,4);
    Actividad d = new Actividad("D",4,5);
    Actividad e = new Actividad("E",5,10);
    Actividad f = new Actividad("F",6,5);

    // Se solicita al usuario el numero de dias que dura el sprint,
    // se asumen 8 horas de trabajo por dia
    System.out.println(" Ingrese la duracion en dias de su sprint: ");
    Scanner var = new Scanner(System.in);
    int capacity = (var.nextInt()*8);
    // Se inicializa el sprint y el backlog
    CreateSprint createSprint = new CreateSprint(capacity);
    // Se agregan las actividades creadas al backlog
    createSprint.backlog.add(a);
    createSprint.backlog.add(b);
    createSprint.backlog.add(c);
    createSprint.backlog.add(d);
    createSprint.backlog.add(e);
    createSprint.backlog.add(f);
    // Se muestra por pantalla el backlog
    System.out.println("El backlog contiene las siguientes actividades");
    createSprint.toString(createSprint.backlog);
    // Se completa la matriz de calculos y se llena el sprint
    // Se muestra por pantalla el sprint resultante
    System.out.println("Actividades que conforman el sprint: ("+capacity+" horas) ");
    System.out.println("Valor en el sprint: " +
      createSprint.toString(createSprint.getPathOfTheMatrix
        (createSprint.backlog.size(), createSprint.capacity ,createSprint.completeMatrix())));

  }// Fin del metodo
}// Fin de la clase
