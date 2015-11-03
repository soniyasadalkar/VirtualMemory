
public class ReadEntry 
{
	private char mode;
	private char[] virtual_addr = new char[5];; //index starts from 1
	
	public void setAccess(char m,String v)
	{
		mode = m;
		virtual_addr = v.toCharArray();
	}
	
	public char getMode()
	{
		return mode;
	}
	
	public char[] getVirtualAddr()
	{
		return virtual_addr;
	}
	
}