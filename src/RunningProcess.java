
public class RunningProcess 
{
	private int run;
	
	synchronized void setRun(int pid)
	{
		run = pid;
		
	}
	synchronized int getRun()
	{
		return run;
	}
}