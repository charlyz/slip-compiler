package slip.mylexer;

import java.io.IOException;
import java.io.InputStream;


public class Pointer
{
	// Positions du pointeur dans le fichier
	private int row, col;
	// Fichier d'entrée
	private InputStream input;
	// Caractère actuel
	private char c;
	
	public Pointer(InputStream is) throws IOException
	{
		this.input = is;
		c = (char)is.read();
		row = 1;
		col = 1;
	}
	
	public char getChar()
	{
		return c;
	}
	
	public char remove() throws IOException
	{	
		if(c == '\n')
		{
			row++;
			col = 1;
		}
		else
			col++;
		
		char old = c; 
		c = (char)input.read();
		return old;
	}
	
	public boolean hasNext()
	{
		return (c != (char)-1);
	}
	
	public int getCurrentCol()
	{
		return col;
	}
	
	public int getCurrentRow()
	{
		return row;
	}
	
	public void close()
	{
		try 
		{
			input.close(); 
		}
		catch(IOException e)
		{
			System.out.println("Impossible de fermer le fichier: "+input);
		}
	}
}