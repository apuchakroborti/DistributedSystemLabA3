package Server;
import load_balancing_a3.*;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Request_Processing extends Thread
{
	Socket socket;
	int buf_size;
	ArrayList<Integer> buffer;
        int own_id;
	public Request_Processing(Socket socket,ArrayList<Integer> buffer,int buf_size,int oid) 
	{
		this.socket = socket;//master socket
		this.buf_size = buf_size;
		this.buffer = buffer;//worker buffer
                own_id=oid;
		start();
	}
	
	@Override
	public void run() 
	{
            try 
            {
                //master printStream
                PrintStream writer = new PrintStream(socket.getOutputStream());
                //master sending scaaner
                Scanner scanner = new Scanner(socket.getInputStream());
                String input_line = "";

                while(scanner.hasNextLine()==true)
                {
                    input_line = scanner.nextLine();

                    if(input_line.compareTo("Y")==0)
                    {
                            synchronized (buffer) 
                            {
                                    if(buffer.size()==buf_size)  input_line = "N"+input_line;
                            }

                            writer.println(input_line);

                    }
                    else break;

                }




                int num = Integer.parseInt(input_line);

                synchronized (buffer) 
                {
                        buffer.add(num);
                }

                sleep(1000);

                synchronized (buffer) 
                {
                        buffer.remove(new Integer(num));
                }

                //writer.println(num+" "+num*num);
                writer.println(num+" "+num*num+" "+own_id);
                scanner.close();


            } 
            catch (Exception e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
	}
}
