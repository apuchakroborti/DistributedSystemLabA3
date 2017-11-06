package Server;

import load_balancing_a3.*;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Master_Handeling_Thread extends Thread
{
    Socket socket;//accepted socket from client
    int buf_size;
    ArrayList<Integer> buffer;
    ArrayList<Integer> result_buffer;
    int worker_id[];
    private static int i;
    public Master_Handeling_Thread(Socket socket,ArrayList<Integer> buffer,int buf_size,int worker_id[]) 
    {
            i=1;
            this.socket = socket;//client socket
            this.buffer = buffer;//master buffer
            this.buf_size = buf_size;//master buffer size
            this.worker_id = worker_id;//master listed worker ids
            start();
    }
    public void prnt(String str)
    {
        System.out.print(str);
    }

    @Override
    public void run()
    {
        try 
        {
            //scanner for receiving data from client
            Scanner input = new Scanner(socket.getInputStream());
            //PrintStream for client 
            PrintStream printStream = new PrintStream(socket.getOutputStream());
           

            while(true)
            {


                int current_master_buf_size = buffer.size();
                //prnt("buffer size1=>"+current_master_buf_size+"\n");
                int new_req_start_pos = current_master_buf_size;
                //receiving input from client 
                while(input.hasNextInt()==true)
                {

                        int number = input.nextInt();//receive data from client 
                        //prnt("number=>"+number+"\n");
                        if(number==-1) break;
                        //storing the client data into buffer
                        if(buffer.size()<buf_size) buffer.add(number);

                        //prnt("buffer size2=>"+buffer.size()+"\n");
                }


                //master new result buffer
                //ArrayList<Send_Receive_Thread> arrayList = new ArrayList<>();
                ArrayList<Master_Thread> master_result_buffer = new ArrayList<>();

                //load balancing
                //int i=1;
                for(;current_master_buf_size<buffer.size();current_master_buf_size++)
                {
                        //taking new requested data
                        int value = buffer.get(current_master_buf_size);
                        //prnt("i%worker_id.length=>"+i%worker_id.length+"\n");
                        //prnt("i="+current_master_buf_size+"worker_id="+worker_id.length+"\n");
                        //int selected_worker_id = worker_id[current_master_buf_size%worker_id.length];
                        prnt("Selected worker=>"+(i+1)+"\n");
                        int selected_worker_id = worker_id[i%worker_id.length];
                        i++;
                        i=i%worker_id.length;
                        
                        //requesting haldeling thread
                        master_result_buffer.add(new Master_Thread(value, selected_worker_id,socket));

                }

                for (Master_Thread send_Receive_Thread : master_result_buffer) 
                {
                        //waiting for master until worker handel all the request
                        send_Receive_Thread.join();
                }

                //sending -1 to client ensuring all the request has halndeled 
                printStream.println("-1");


                //if the finished request are removed from master buffer 
                for(current_master_buf_size=buffer.size()-1;current_master_buf_size>=new_req_start_pos;current_master_buf_size--)
                {
                        //prnt("buffer_content=>"+buffer.get(current_master_buf_size)+"\n");
                        buffer.remove(current_master_buf_size);
                }



            }
        } 
        catch (Exception e) 
        {
                // TODO: handle exception
        }
    }


}
