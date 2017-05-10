import java.util.ArrayList;
import java.lang.RuntimeException;

/** Clase que define un backlog como un ArrayList de actividades
* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
* @param backlog Lista de actividades que representa al backlog
*/
public class Backlog{
	public ArrayList<Actividad> backlog;

	/** Constructor de la clase
	*/
	public Backlog(){
		backlog = new ArrayList<Actividad>();
	}// Fin del constructor

	/** Metodo utilizado para obtener la actividad de maximo valor en el backlog que entre en el sprint
	* @param capacity Horas disponibles en el sprint
	* @return Actividad La actividad de maximo valor que entre en el sprint
	*/
	public Actividad getMaxPriorityBacklog(int capacity)throws RuntimeException{ //Capacity es el espacio que queda por llenar en el sprint
		int index = 0;
		//mientras el costo no entre en el sprint y el index no este al final del backlog se incrementa el index
		while((backlog.get(index).costoHr > capacity)&&(index<backlog.size())){
			index++;
		}
		if( index < backlog.size()){ // si el index no recorrio todo el backlog
			Actividad aux = backlog.get(index); //Obtengo la actividad
			backlog.remove(index); // Se elimina del backlog
			return aux;
		}else{
			//en caso de no entrar ninguna otra actividad en el sprint, dispara excepcion.
			throw new RuntimeException("No hay posibles actividades");
		}
	}// Fin del metodo

	/** Metodo utilizado para insertar una actividad en el backlog respetando un orden descendente respecto del
	* valor de las actividades
	* @param nact Actividad que se desea insertar en el backlog
	* @see #getPriority(int index)
	*/
	public void insertBacklog(Actividad nact){
		int index=0;
		// Mientras la prioridad sea menor a las del elemento corriente del backlog, ciclar hasta el lugar correspondiente
		while((nact.valor < getPriority(index)) && (index < backlog.size())){
			index++;
		}
		backlog.add(index,nact); //Agregamos la actividad al backlog
	}// Fin del metodo

	/** Metodo utilizado para retornar el valor de una actividad, si no existe retorna -1
	* @param index Indice de la actividad a retornar
	* @return int
	*/
	public int getPriority(int index){
			if (index >= backlog.size())
				return -1;
			return backlog.get(index).valor;
	}// Fin del metodo

	/** Metodo que imprime por pantalla el backlog
  * @return String
  */
	public String toString(){
		System.out.println(" ");
		for (int index=0; index < backlog.size(); index++) {
			System.out.println("* "+ backlog.get(index).funcionalidad + " con costo " + backlog.get(index).costoHr + " y prioridad "+ backlog.get(index).valor);
		}
		System.out.println(" ");
    return "End";
	}// Fin del metodo
}// Fin de la clase
