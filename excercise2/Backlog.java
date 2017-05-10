import java.util.ArrayList;
import java.lang.RuntimeException;

public class Backlog{
	public ArrayList<Actividad> backlog;


	public Backlog(){
		backlog = new ArrayList<Actividad>();
	}

	//este metodo se encarga de obtener la actividad con maxima prioridad posible,
	//teniendo en cuenta la limitacion del tamaño del sprint
	public Actividad getMaxPriorityBacklog(int capacity)throws RuntimeException{ //Capacity es el espacio que queda por llenar en el sprint
		int index = 0;
		//mientras el costo no entre en el sprint y el backlog aun tenga actividades, entonces incrementamos
		while((backlog.get(index).costoHr > capacity)&&(index<backlog.size())){
			index++;
		}
		//si el backlog aun tiene actividades, entonces sumamos esa actividad al sprint
		if( index < backlog.size()){
			Actividad aux = backlog.get(index);
			backlog.remove(index);
			return aux;
		}else{
			//en caso de no entrar ninguna otra actividad en el sprint, dispara excepcion.
			throw new RuntimeException("No hay posibles actividades");
		}
	}

	//este metodo se encarga de insertar las actividades en el backlog cordenadamente,
	//teniendo en cuenta en primer lugar la prioridad y en segundo lugar el costo.
	public void insertBacklog(Actividad nact){
		int index=0;
		//mientras la prioridad sea menor a las del backlog, entoces cicla hasta encontrar el lugar correspondiente
		while((nact.valor < getPriority(index)) && (index < backlog.size())){
			index++;
		}
		//agregamos la actividad al backlog
		backlog.add(index,nact);
	}

	public int getPriority(int index){
			if (index >= backlog.size())
				return -1;
			return backlog.get(index).valor;
	}

	public String toString(){
		System.out.println(" ");
		for (int index=0; index < backlog.size(); index++) {
			System.out.println("* "+ backlog.get(index).funcionalidad + " con costo " + backlog.get(index).costoHr + " y prioridad "+ backlog.get(index).valor);
		}
		System.out.println(" ");
    return "End";
	}




}
