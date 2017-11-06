package Server;
import load_balancing_a3.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server 
{
	public static void main(String args[]) throws IOException 
	{
		
                int max_worker=1;
                int max_worker_buffer=10;
		int worker_id[] = new int[max_worker];
		
		for(int i=0;i<max_worker;i++)
		{
			//new Worker(max_worker_buffer,1235+i);
			//worker_id[i] = 1235+i;
                        new Worker(max_worker_buffer, i+5000);
                        worker_id[i]=5000+i;
		}
		
		
		
		//ServerSocket serverSocket = new ServerSocket(1234);//server socket for client
                ServerSocket serverSocket = new ServerSocket(8080);//server socket for client
		
		int buf_size = 20;
                //master buffer --size 50
		ArrayList<Integer> master_buffer = new ArrayList<>();
		
		while(true)
		{
			Socket socket = serverSocket.accept(); //waiting for client request
			//sending socket,own buffer,buffer size,worker list
                        new Master_Handeling_Thread(socket,master_buffer,buf_size,worker_id);
                }


	}
}