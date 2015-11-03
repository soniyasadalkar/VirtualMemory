import java.util.*;
public class OsSimulation  
{
	public static RunningProcess r ;
	static ArrayList<Integer> readyQueue ;
	static ArrayList<Integer> blockedQueue;
	
	
	
	OsSimulation()
	{
            r = new RunningProcess();
             readyQueue = new ArrayList<Integer>();
            blockedQueue = new ArrayList<Integer>();
		
	}
	public static void displayQueue()
        {
            int i;
            System.out.print("Blocked Queue");
            for(i=0;i<blockedQueue.size();i++)
            {
                System.out.print(" : " +(blockedQueue.get(i).intValue())+ " ");
            }
            System.out.println();
        }
        
        public static void displayReadyQ()
        {
            int i;
            System.out.print("Ready Queue");
            for(i=0;i<readyQueue.size();i++)
            {
                System.out.print(" : " +(readyQueue.get(i).intValue())+ " ");
            }
            System.out.println();
        }
        public static void init_queue()
        {
            int i;
            for(i=1;i<=Main.n;i++)
            {
                readyQueue.add(new Integer(i));
            }
        }
	public void run()
	{
			System.out.println();
            System.out.println("OS SIMULATION RUNNING");
            System.out.println();
            
		    int i=0;
	       init_queue();
	        int count=0;
	        MemoryMgmtHw1 mhw = new MemoryMgmtHw1();
		     
	        while(!readyQueue.isEmpty())
                {
                    
                    r.setRun(readyQueue.get(i).intValue()); 
                   
                       mhw.run();
                       System.out.println("Size of readyQueue: "+readyQueue.size()+"\n\n");
                 }
	System.out.println("exit os run  " +count );	
	}
}