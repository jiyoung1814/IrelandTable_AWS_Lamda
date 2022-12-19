package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server extends Thread{

    public final static int SERVER_PORT=8989;
    ServerSocket ss=null;

    public TCP_Server(){
        try {
            ss=new ServerSocket(SERVER_PORT);

        }catch(Exception e) {
            System.out.println("serverSocket Error: "+e.getMessage());
        }
    }

    public void run(){
        while(true){
            try {
//                System.out.println("Waiting connection...");
                Socket socket=ss.accept();		//새끼 Socket 넘겨줌
                System.out.println("[ Connection Info ]");
                System.out.println("client address:"+socket.getInetAddress());	//클라이언트 IP주소
                System.out.println("client port:"+socket.getPort());			//클라이언트 포트 번호
                System.out.println("my port:"+socket.getLocalPort());		//나(Server, Local)의 포트


                Worker w = new Worker(socket);
                w.start();

            }catch(Exception e) {
                System.out.println("serverSocket Error: "+e.getMessage());
            }
        }
    }

    public class Worker extends Thread{
        byte[] byteLengthType = new byte[15];
        private Socket socket;
        BufferedReader br;
        BufferedWriter bw;
        String readValue ="";


        public Worker(Socket socket){
            this.socket = socket;
            try{
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            }catch (Exception e){
                System.out.println("InputStream, OutputStream Error: "+  e.getMessage());
            }
        }

        public void run(){
            try {
                while(!readValue.equals("done")){
                    readValue = br.readLine();
                    System.out.println(readValue);
                }

                bw.write("done");
                bw.flush();


            } catch (IOException e) {
                System.out.println("read Error: "+  e.getMessage());
            }
            finally {
                if(socket !=null){
                    System.out.println("socket Closed");
                    try {
                        socket.close();
                    } catch (Exception e) {
                        System.out.println("close Error: "+  e.getMessage());
                    }
                }
            }
        }
    }
}
