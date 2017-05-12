/** Clase que define una actividad con su funcionalidad, valor y costo de tiempo para
* realizarse.
* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
*/
public class Actividad{

	public String funcionalidad;
	public int valor;//de 1 a 3, 1 tiene mas prioridad que todos y al aumentar cada vez tiene menos prioridad.
	public int costoHr;//horas que demanda realizar dicha tarea

	/** Constructor de la clase
	* @author: Armas Lucas, Martinez Agustin, Sanchez Daniel
	* @param fun Descripcion de la actividad
	* @param val Vinculado a la prioridad y utilidad de la actividad
	* @param cos Costo en horas demandado para realizar la actividad
	*/
	public Actividad(String fun,int val, int cos){
		this.funcionalidad=fun;
		this.valor=val;
		this.costoHr=cos;
	} // Fin del constructor
}// Fin de la clase
