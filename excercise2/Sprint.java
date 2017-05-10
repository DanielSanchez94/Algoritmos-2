import java.util.ArrayList;
import java.lang.RuntimeException;

/** Clase que define un sprint, representado como una lista de actividades
* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
* @param listActivities ArrayList de Actividades que representa las actividades
* del sprint
* @param capacity Numero de horas que dura el sprint
*/
public class Sprint{
  public ArrayList<Actividad> listActivities;
  public int capacity;

  /**Constructor de la clase
  * @param num Representa la duracion del sprint
  */
  public Sprint(int num){
    listActivities = new ArrayList<Actividad>();
    capacity = num;
  }// Fin del constructor

  /** Metodo utilizado para completar el sprint con actividades
  * @param backlog Backlog de donde se obtienen las actividades que van al sprint
  */
  public void completeSprint(Backlog backlog){
    int hoursDisp = capacity;
		while(hoursDisp > 0){ //Mientras haya lugar en el sprint
      Actividad aux = backlog.getMaxPriorityBacklog(hoursDisp); //Obtiene la proxima actividad que entre en el sprint
      listActivities.add(aux); //Agrega la actividad al sprint
      hoursDisp -= aux.costoHr; //Decrementa el espacio en el sprint
    }
	}// Fin del metodo

  /** Metodo que imprime por pantalla el sprint
  * @return String
  */
  public String toString(){
    System.out.println(" ");
    if(listActivities.size()==0){
      System.out.println("El sprint queda vacio pues no hay actividades en el backlog");
    }
		for (int index=0; index < listActivities.size(); index++) {
			System.out.println("* "+ listActivities.get(index).funcionalidad + " con costo " + listActivities.get(index).costoHr + " y valor "+ listActivities.get(index).valor);
		}
    System.out.println(" ");
    return "End";
	}// Fin del metodo
}// Fin de la clase
