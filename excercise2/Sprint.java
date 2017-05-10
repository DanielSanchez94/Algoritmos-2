import java.util.ArrayList;
import java.lang.RuntimeException;
import java.lang.Math;

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
  public int completeSprint(Backlog backlog){
    int k[][] = new int[backlog.backlog.size()+1][capacity+1];
    for (int i=0; i<=backlog.backlog.size(); i++) {
      for (int w=0; w<=capacity; w++) {
        if (i==0 || w==0)
          k[i][w]=0;
        else if(backlog.backlog.get(i-1).costoHr <= w)
                k[i][w]= Math.max((backlog.backlog.get(i-1).valor)+k[i-1][w-backlog.backlog.get(i-1).costoHr] , k[i-1][w]);
              else
                k[i][w]= k[i-1][w];
      }
    }
    return k[backlog.backlog.size()][capacity];
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
