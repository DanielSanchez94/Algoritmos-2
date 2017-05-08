import java.util.ArrayList;
import java.lang.RuntimeException;

public class Backlog{
	public ArrayList<Actividad> backlog;
	public ArrayList<Actividad> sprint;
	public int capacitySprint;

	public Backlog(int capacity){
		backlog = new ArrayList<Actividad>();
		sprint = new ArrayList<Actividad>();
		capacitySprint = capacity;
	}

	public Actividad getMaxPriorityBacklog(int capacity)throws RuntimeException{ //Capacity es el espacio que queda por llenar en el sprint
		int index = 0;
		while((backlog.get(index).costo > capacity)&&(index<backlog.size())){
			index++;
		}
		if( index++ < backlog.size()){
			Actividad aux = backlog.get(index);
			backlog.remove(index);
			return aux;
		}else{
			throw new RuntimeException("No hay posibles actividades");
		}
	}

	public void insertBacklog(Actividad nact){
		int index=0;
		while((nact.prioridad < backlog.get(index).prioridad)&&(index<backlog.size())){
				index++;
		}
		while((nact.costo > backlog.get(index).costo)&&(index < backlog.size())){
			index++;
		}
		backlog.add(index,nact);
	}
	public void toString(){
		for (int index=0; index<backlog.size(); index++) {
			System.out.println(i + " : " + sprint.get(index).funcionalidad + " con costo " + sprint.get(i).costo);
		}
	}
}
