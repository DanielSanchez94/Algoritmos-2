public class Actividades{
	
	Integer durSprint;//dias
	String funcionalidad;
	String descripcion;
	Integer prioridad;//de 1 a 3, 1 tiene mas prioridad que todos y al aumentar cada vez tiene menos prioridad.
	Integer costo;//horas que demanda realizar dicha tarea

	public Actividades(Integer dp, String fun, String descr, Integer pri, Integer cos){
		this.durSprint=dp;
		this.funcionalidad=fun;
		this.descripcion=descr;
		this.prioridad=prioridad;
		this.costo=costo;
	}
}