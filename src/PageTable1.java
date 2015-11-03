
public class PageTable1 
{
	PageTableEntry pt[];
        
	
        PageTable1()
        {
            pt= new PageTableEntry[64];
            
            for(int i=0;i<64;i++)
            {
                pt[i] = new PageTableEntry();
            }
        }
        
	
}