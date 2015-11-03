
import java.io.*;

/* pid starts from one */

import java.util.*;
public class Main
{
	static int no_frames;
	static int n;
	public static ReadEntry pAccess[][];
	public static int ti[] ;// total accesses  for each process array
	public static Frame fr[]; 
	static int count = 0;  
	public static void main(String[] args)
	{
		System.out.print("Enter the NO.OF PROCESSES : ");
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
                        
		ti = new int[n+1];
                pAccess = new ReadEntry[n+1][n+1];
		System.out.print("Enter the Size of Real Memory in kb: ");
		int size = scan.nextInt();
		no_frames = size;
                fr = new Frame[no_frames+1];
		
                int i=0;
                
                for(i=1;i<=no_frames;i++)
                {
                       fr[i] = new Frame(); 
                }
                try
                {
                	
                    File f = new File(System.getProperty("user.dir") + "/input1.dat");
                    FileInputStream fstream = new FileInputStream(f);
                    Main.ReadFile(fstream);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                } 
                OsSimulation os = new OsSimulation();
                
               os.run();
            
		
	}
	static void ReadFile(FileInputStream fstream)throws IOException
	{
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str;
		String strarray[];
		
		Arrays.fill(ti,1);
                int pid ;
		int index;
                
		while( (str = br.readLine()) != null )
		{
			strarray = str.split(",");
		
			pid = Integer.parseInt(strarray[0]);
			index = ti[pid];
                        ReadEntry r = new ReadEntry();
			r.setAccess(strarray[1].charAt(0),strarray[2]);
			pAccess[pid][index] = r;
			ti[pid]++;
                        count++;
		}
               
		
		display(n);
		
	}
	public static void display(int n)
	{
		int i,j;
		for(i=1;i<=n;i++)
		{
                       
			for(j=1;j<ti[i];j++)
			{
				ReadEntry r = pAccess[i][j];
				System.out.print(i + " " + r.getMode() + " " );
                                char[] va = r.getVirtualAddr();
                                for(char c:va)
                                    System.out.print(c);
                                System.out.println();
			}
		}
	}
}
