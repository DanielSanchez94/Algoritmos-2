import java.util.ArrayList;
import java.lang.RuntimeException;
import java.lang.Math;

/** Clase que define un sprint, representado como una lista de actividades
* @author: Armas Lucas, Sanchez Daniel
*/
public class CreateSprint{
  	public ArrayList<Actividad> backlog;
  	public ArrayList<Actividad> sprint;
  	public int capacity;


  	/**Constructor de la clase
  	* @param num Representa la duracion del sprint
  	*/
  	public CreateSprint(int num){
    	backlog = new ArrayList<Actividad>();
   		sprint = new ArrayList<Actividad>();
    	capacity = num;
  	}// Fin del constructor

  /** Metodo utilizado para completar la matriz de resultados
  * @return int[][]
  */
  public int[][] completeMatrix(){
    int k[][] = new int[backlog.size()+1][capacity+1];
    for (int i=0; i<=backlog.size(); i++) {
      for (int w=0; w<=capacity; w++) {
        if (i==0 || w==0)
          k[i][w]=0;
        else  if(backlog.get(i-1).costoHr <= w){  // Si la actividad corriente entra en el sprint
                Actividad current = backlog.get(i-1); //Motivo de acortar longitud de siguiente condicion
                // Se queda con el maximo entre la suma de las actividades agregadas anteriormente
                // y la suma anterior mas el valor del elemento corriente
                k[i][w] = Math.max((current.valor)+k[i-1][w-current.costoHr] , k[i-1][w]);
              }else{
                k[i][w]= k[i-1][w];
              }
      }
    }
    //PRINTING DE MATRIX
    /* for (int[] i : k) {
      for (int w : i) {
        System.out.format("%5d", w);
      }
      System.out.println();
    }*/

    return k;
  }// Fin del metodo

 /** Metodo utilizado para obtener las actividades que se agregaron al sprint
 * @param i indice para recorrer las filas de una matriz
 * @param w indice para recorrer las columnas de una matriz
 * @param k matriz que contiene los resultados necesarios
 * @return ArrayList, retorna un array list deactividades
 * @see getPathOfTheMatrix
 */
	public ArrayList<Actividad> getPathOfTheMatrix(int i, int w, int[][] k){
    if (i == 0){
			return sprint;
		}
    if (k[i][w] > k[i-1][w]){
    	sprint.add(backlog.get(i-1));
    	return  getPathOfTheMatrix(i-1, w-backlog.get(i-1).costoHr, k);
  	}else{
  		return  getPathOfTheMatrix(i-1, w , k);
  	}
  }// Fin del metodo

  /** Metodo que imprime por pantalla el sprint
  * @param arrayL Representa el ArrayList que queremos recorrer
  * @return int
  */
  public int toString(ArrayList<Actividad> arrayL){
    System.out.println(" ");
    if(arrayL.size()==0){
      System.out.println("No hay actividades en el backlog o no entran en el sprint");
    }
    int result = 0;
		for (int index=0; index < arrayL.size(); index++) {
      result += arrayL.get(index).valor;
			System.out.println("* "+ arrayL.get(index).funcionalidad + " de valor " + arrayL.get(index).valor + " y costo "+ arrayL.get(index).costoHr);
		}
    System.out.println(" ");
    return result;
	}// Fin del metodo

}// Fin de la clase
