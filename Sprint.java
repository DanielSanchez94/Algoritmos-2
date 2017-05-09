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
        hoursDisp -= aux.costo;
      }
	}

  public String toString(){
		for (int index=0; index < listActivities.size(); index++) {
			System.out.println(index + " : " + listActivities.get(index).funcionalidad + " con costo " + listActivities.get(index).costo);
		}
    return "End";
	}
}
