public class Actividad{

	public String funcionalidad;
	public int prioridad;//de 1 a 3, 1 tiene mas prioridad que todos y al aumentar cada vez tiene menos prioridad.
	public int costo;//horas que demanda realizar dicha tarea

	public Actividad(String fun,int pri, int cos){
		this.funcionalidad=fun;
		this.prioridad=pri;
		this.costo=cos;
	}
}
