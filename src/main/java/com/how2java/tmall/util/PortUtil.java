package com.how2java.tmall.util;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

public class PortUtil {
    public static boolean testPort(int port){
        try{
            ServerSocket ss=new ServerSocket(port);
            ss.close();
            return false;
        }catch (BindException e){
            return true;
        }catch (IOException e){
            return true;
        }
    }

    public static void checkPort(int port,String server,boolean shutdown){
        if(!testPort(port)){
            if(shutdown){
                String message=String.format("在端口 %d 未检查得到 %s 启动%n",port,server);
                JOptionPane.showMessageDialog(null,message);
                System.exit(1);
            }else {
                String message=String.format("在端口 %d 未检查得到 %s 启动%n,是否继续",port,server);
                if(JOptionPane.showConfirmDialog(null,message)!=JOptionPane.OK_OPTION){
                    System.exit(1);
                }
            }
        }
    }
}
