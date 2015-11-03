
import java.util.*;
public class SignalHandler 
{
    
    int pid;
    int pageNo;
    ReadEntry re;
    SignalHandler(int pid,int pageNo,ReadEntry re)
    {
        this.pid=pid;
        this.pageNo=pageNo;
        this.re=re;
    
    }
    
    public void run()
    {
        OsSimulation.readyQueue.remove(new Integer(pid));
        OsSimulation.blockedQueue.add(new Integer(pid)); //put pid in blocked queue
        OsSimulation.displayQueue();       // print the blocked queue                     
        int i;
        int frame = -1;
        for(i=1;i<=Main.no_frames;i++)
        {
            if(Main.fr[i].bit == 0)
            {
                frame = i;
                break;
            }
        }
        if(i > Main.no_frames)
        {
        	Random r = new Random();
        	
        	int random_f = r.nextInt(Main.no_frames+1);
        	
        	while(true)
        	{
	        	if(random_f!=0)
	        	{
	        		frame = random_f;
	        		break;
	        	}
	        	else
	        	{
	        		random_f = r.nextInt(Main.no_frames+1);
	        	}
        	}
            
            int oldPid=Main.fr[frame].pid;
            int oldPageNo = Main.fr[frame].pageNo;
            
            PageTableEntry pte = MemoryMgmtHw1.getEntry(oldPid,oldPageNo);
            System.out.println("Swapping out process " + oldPid + ", page =  " +oldPageNo + " from the frame " +frame);
          
            MemoryMgmtHw1.setEntry(oldPid, oldPageNo ,0, pte.m); //update the swapped page
            if(pte.m == 'W')
            {
            	System.out.println("writing page " + oldPageNo + "of process " + oldPid + " to disk");
             }
            
           
            
        }
        
            MemoryMgmtHw1.setEntry(pid, pageNo, frame,re.getMode());
            Main.fr[frame].pid=pid;
            Main.fr[frame].bit=1;
            Main.fr[frame].pageNo = pageNo;
            
        
         System.out.println("Loading process " + pid + ",page =  " + pageNo + " in frame no "  + frame);
         OsSimulation.readyQueue.add((int)OsSimulation.readyQueue.size(), pid);
         
         OsSimulation.displayReadyQ();
         OsSimulation.blockedQueue.remove(new Integer(pid));
          MemoryMgmtHw1.display_frames(Main.fr);
        
    }
}   