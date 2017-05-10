import java.util.ArrayList;
import java.lang.RuntimeException;

public class Sprint{
  public ArrayList<Actividad> listActivities;
  public int capacity;

  public Sprint(int num){
    listActivities = new ArrayList<Actividad>();
    capacity = num;
  }

  public void completeSprint(Backlog backlog){
    int hoursDisp = capacity;
		while(hoursDisp > 0){
      Actividad aux = backlog.getMaxPriorityBacklog(hoursDisp);
      listActivities.add(aux);
      hoursDisp -= aux.costoHr;
    }
	}

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
	}
}
