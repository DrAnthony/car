package team.ccpd.demo.tool;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//使用socket实现进程间通信
//饿汉单例，未考虑多线程
public class PythonThread {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket=null;
    private static PythonThread instance=null;

    private PythonThread(){
        init();
    }
    private void init(){
        try {
            socket=new Socket("127.0.0.1",6000);
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static PythonThread getInstance(){
        if(instance==null){
            instance=new PythonThread();
        }
        return instance;
    }

    public String send(String path){
        writer.print(path);
        writer.flush();
        String line;
        try {
            line=reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "flase";
        }
        return line;
    }
}
