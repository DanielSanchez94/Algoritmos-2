import java.util.ArrayList;
import java.lang.RuntimeException;
import java.lang.Math;

/** Clase que define un sprint, representado como una lista de actividades
* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
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

  	/** Metodo utilizado para completar el sprint con actividades
  	* @see #sumParc()
  	*/
  	public ArrayList<Actividad> completeMatrix(){
    	int k[][] = new int[backlog.size()+1][capacity+1];
    	//inicioalizacion de la primer fila y primer columna con 0's
    	for (int i=0; i<=backlog.size(); i++) {
      		for (int w=0; w<=capacity; w++) {
        		if (i==0 || w==0)
          			k[i][w]=0;
        		else 
        			if(backlog.get(i-1).costoHr <= w){
                		Actividad current = backlog.get(i-1); //Motivo de acortar longitud de siguiente condicion
                		// Si la actividad corriente entra en el sprint, maximiza el sprint parcial
                		// y si no ha sido agregada antes, se la agrega al sprint
                		k[i][w] = Math.max((current.valor)+k[i-1][w-current.costoHr] , k[i-1][w]);
              		}else{
                		k[i][w]= k[i-1][w];
              		}
      		}
    	}
		ArrayList<Actividad> list= new ArrayList<Actividad>();
    return (getPathOfTheMatrix(backlog.size(), capacity , list, k));
    }// Fin del metodo

    /** Metodo utilizado para obtener las actividades del sprint, en base a la matriz obtenida en el metodo completeMatrix
  	* @return ArrayList<Actividad>
  	* @Param i indice de columnas
  	* @Param w indice de filas
  	* @Param res lista vacia, donde vamos a obtener las actividades del sprint
  	* @Param k matriz obtenida de el metodo completeMatrix
  	*/
    public ArrayList<Actividad> getPathOfTheMatrix(int i,int w, ArrayList<Actividad> res, int[][] k){
		//los indices comienzan del lugar mas abajo y mas a la derecha
		if (i == 0){
			return res;
		}
		//si el elemento corriente, es mas grande que el que tiene "arriba" 
      	if (k[i][w] > k[i-1][w]){
      		//lo guardo en el sprint, ya que ese items, fue el que me optimiso el sprint
      		res.add(backlog.get(i-1));
      		//llamo de nuevo a la matriz pero indicandole los indices correspondientes, "achicando"
      		//la matriz, teniendo en cuenta lo que agregue al sprint.
      		return  getPathOfTheMatrix(i-1, w-backlog.get(i-1).costoHr, res, k);
  		}else{
  			//si el elemento no fue menor al de arriba, entonces llamo recursivamente la funcion, 
      		//de tal manera que el elemento agregado al sprint sea nuevamente el de la esquina 
      		//inferior derecha de la matriz 
  			return  getPathOfTheMatrix(i-1, w , res, k);
  		}
  	}
    
  	/** Metodo utilizado para calcular la cantidad de horas ocupadas del sprint
  	* @return int
  	*/ 
  	public int sumParc(){
    	int result=0;
    	for (int i=0; i<sprint.size(); i++) {
      		result += sprint.get(i).costoHr;
    	}
    return result;
  	}// Fin del metodo

  	/** Metodo que imprime por pantalla el sprint
  	* @param arrayL Representa el ArrayList que queremos recorrer
  	* @return String
  	*/
  	public String toString(ArrayList<Actividad> arrayL){
    	if(arrayL.size()==0){
      		System.out.println("No hay actividades en el backlog o no entran en el sprint");
    	}
		for (int index=0; index < arrayL.size(); index++) {
			System.out.println("* "+ arrayL.get(index).funcionalidad + " de valor " + arrayL.get(index).valor + " y costo "+ arrayL.get(index).costoHr);
		}
    	System.out.println(" ");
    return "End";
	}// Fin del metodo

}// Fin de la clase

/*codigo extra

//Printing the matrix
	for (int[] i : k) {
        for (int w : i) {
            System.out.format("%5d", w);
        }
        System.out.println();
    }
*/   