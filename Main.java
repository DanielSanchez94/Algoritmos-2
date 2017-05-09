public class Main {
  public static void main(String[] args) {
    Actividad a1 = new Actividad("act1", 10, 2);
    Actividad a2 = new Actividad("act2",10,1);
    Actividad a3 = new Actividad("act3",10,4);
    Actividad a4 = new Actividad("act4",9,2);
    Actividad a5 = new Actividad("act5",9,10);
    Actividad a6 = new Actividad("act6",5,6);
    Actividad a7 = new Actividad("act7",5,4);
    Actividad a8 = new Actividad("act8",23,4);

    Backlog backlog = new Backlog();
    Sprint sprint = new Sprint(7);

    backlog.insertBacklog(a1);
    backlog.insertBacklog(a2);
    backlog.insertBacklog(a3);
    backlog.insertBacklog(a4);
    backlog.insertBacklog(a5);
    backlog.insertBacklog(a6);
    backlog.insertBacklog(a7);
    backlog.insertBacklog(a8);
    backlog.toString();
    try{
      sprint.completeSprint(backlog);
    }catch(RuntimeException e){
      System.out.println(e.getMessage());
    }
    sprint.toString();
  }
}
