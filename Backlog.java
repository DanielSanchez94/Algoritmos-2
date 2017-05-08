import java.util.ArrayList;
import java.lang.RuntimeException;

public class Backlog{
	public ArrayList<Actividad> backlog;
	public ArrayList<Actividad> sprint;
	public int capacitySprint;

	//
	public Backlog(int capacity){
		backlog = new ArrayList<Actividad>();
		sprint = new ArrayList<Actividad>();
		capacitySprint = capacity;
	}

	//este metodo se encarga de obtener la actividad con maxima prioridad posible, 
	//teniendo en cuenta la limitacion del tamaño del sprint
	public Actividad getMaxPriorityBacklog(int capacity)throws RuntimeException{ //Capacity es el espacio que queda por llenar en el sprint
		int index = 0;
		//mientras el costo no entre en el sprint y el backlog aun tenga actividades, entonces incrementamos
		while((backlog.get(index).costo > capacity)&&(index<backlog.size())){
			index++;
		}
		//si el backlog aun tiene actividades, entonces sumamos esa actividad al sprint
		if( index++ < backlog.size()){
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
		while((nact.prioridad < backlog.get(index).prioridad)&&(index<backlog.size())){
			index++;
		}
		//luego de encontrar el lugar de prioridad que corresponde, priorizamos respecto al costo en horas de cada act.
		while((nact.costo > backlog.get(index).costo)&&(index < backlog.size())){
			index++;
		}
		//agregamos la actividad al backlog
		backlog.add(index,nact);
	}
	//muestra todos los elementos que deberian realizar en ese sprint
	public void toString(){
		for (int index=0; index<backlog.size(); index++) {
			System.out.println(i + " : " + sprint.get(index).funcionalidad + " con costo " + sprint.get(i).costo);
		}
	}

	//metodo que inserta ordenadamente los elementos correspondientes(con mayor prioridad)al sprint
	public void insertSprint(int tamañosprint){
		int i=0;
		int cap=tamañosprint;
		//mientras la prioridad sea menor a las del backlog, entoces cicla hasta encontrar el lugar correspondiente
		while(i<backlog.size()){
			cap=cap-;
			sprint[i]=getMaxPriorityBacklog(cap);
			i++;
		}
	}
}
//el eliminar vamos a tener que hacerlo por separado para que en insertarSprint podamos restarle a cap, el costo de el elemento que insertamos