
import java.util.*;

public class MemoryMgmtHw1 
{
	static PageTable1 PT[];
	static int ci[] ; // current access for a particular process
        
	
	MemoryMgmtHw1()
	{
                 PT = new PageTable1[Main.n+1];
                 int i;
                 
                 for(i=1;i<=Main.n;i++)
                 {
                     PT[i] = new PageTable1();
                 }
                 ci = new int[Main.n+1];
                Arrays.fill(ci, 1);
                
                 
                
	}
        static void setEntry(int pid,int page,int frame,char mode )
	{
                if(frame!=0)
                {
                    PT[pid].pt[page].p = true;
                }
                else
                {
                    PT[pid].pt[page].p = false;
                }
		PT[pid].pt[page].m = mode;
		PT[pid].pt[page].frame_no = frame;
	}
	static PageTableEntry getEntry(int pid,int page)
	{
		return PT[pid].pt[page];
	}
	void useEntry(int pid,int page,String vir_addr)
	{
		PageTableEntry p = PT[pid].pt[page];
                
		System.out.println("Program "+pid+" accessing "+vir_addr+" in mode "+p.m);
	}
	public void run()
	{
		try
		{	
				RunningProcess r = OsSimulation.r;
                                int running = r.getRun();
                                System.out.println("Process running : " + running);
                            
                            
				ReadEntry re = getNextMemAccess(running);
		             if(re != null)
                     {
                         int frame = PT[running].pt[getPage(re.getVirtualAddr())].frame_no;
                        setEntry(running, getPage(re.getVirtualAddr()),frame,re.getMode());
         		
									if(virtual2PhyAddr(running,re))
									{
					                                    System.out.println("translation successful");
					                                    useEntry(running, getPage(re.getVirtualAddr()), new String(re.getVirtualAddr()));
					                                    ci[running]++;
									}
									else
									{
					                                     System.out.println("translation unsuccessful");
					                                    SignalHandler sh = new SignalHandler(running,getPage(re.getVirtualAddr()),re);
					                                    sh.run();
					                                   
					                                   
									}
                                 
                             }
                             else
                             {
                                 System.out.println("process "+running + " completed execution");
                                 
                                 int k;
                                 for(k=1;k<=Main.no_frames;k++)
                                 {
                                     if(Main.fr[k].pid == running)
                                     {
                                         Main.fr[k].pid=0;
                                         Main.fr[k].bit=0;
                                         Main.fr[k].pageNo = 0;
                                     }
                                 }
                                 MemoryMgmtHw1.display_frames(Main.fr);
                             }
				
				
			
		}
		catch(Exception e)
		{
			System.out.println(e);
                        e.printStackTrace();
		}
				
	}
	
	ReadEntry getNextMemAccess(int running)
	{
                if(ci[running] < Main.ti[running])
                {
                    System.out.println("process "+running+" ci : "+ ci[running]);
                    int entry = ci[running];
                    ReadEntry re = Main.pAccess[running][entry];
                    
                    return re;
                }
               else
                {
                    OsSimulation.readyQueue.remove(new Integer(running));
                    OsSimulation.displayReadyQ();
                    return null;
                }
		
	}
        
        static public void display_frames(Frame[] f)
        {
            int i;
            
            for(i=1;i<=Main.no_frames;i++)
            {
                System.out.print("("+f[i].pid+","+f[i].pageNo+")"+ " | ");
            }
            System.out.println();
        }
	boolean virtual2PhyAddr(int pid,ReadEntry r)
	{
		
		int page = getPage(r.getVirtualAddr());
		System.out.println("Page no: " + page);
               
                PageTableEntry pe = getEntry(pid,page);
		if(pe.p == true)
                    
                {   
                    
                    display_frames(Main.fr);
                    return true;
                }
		else
		{
                    display_frames(Main.fr);
                    return false;
		}
               
	}
        public void init_hashtable(Hashtable h)
        {
              h.put('0',"0000");
              h.put('1',"0001");
              h.put('2',"0010");
              h.put('3',"0011");
              h.put('4',"0100");
              h.put('5',"0101");
              h.put('6',"0110");
              h.put('7',"0111");
              h.put('8',"1000");
              h.put('9',"1001");
              h.put('A',"1010");
              h.put('B',"1011");
              h.put('C',"1100");
              h.put('D',"1101");
              h.put('E',"1110");
              h.put('F',"1111");
              
               
        }
        public int getPage(char[] va)
        {
            int i,j,k;
            char[] str = new char[9];
            Hashtable h = new Hashtable();
            init_hashtable(h);
           
            char[] str_bin=new char[5];
            j=1;
            try
            {
            for(i=0;i<2;i++)
            {
                str_bin =  ((String)h.get(va[i])).toCharArray();
             
                for(k=0;k<4;k++)
                {
                    str[j] = str_bin[k];
                    j++;
                }
                
            }
            }
            catch(NullPointerException n)
            {
            	n.printStackTrace();
            }
            
            char[] s1 = new char[6];
            for(i=0;i<6;i++)
            {
                s1[i] = str[i+1];
                
            }
            String s = new String(s1);
            
            int pageNo;
         
             pageNo = Integer.parseInt(s,2);
           
            
           return pageNo;
            
            
            
        }
        
}