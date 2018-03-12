/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr, 

�� Function
  �� 

�� Study
  �� IO
    - IO  �� Byte �� InputStream(:abstract class)
                 �� OutputStream(:abstract class)
          �� Char �� Reader(:abstract class)
                 �� Writer(:abstract class)
    - byte���� �����Ŭ���� 
      : InputStream, OutputStream �߻�Ŭ���� ���
    - char(����)���� �����Ŭ����
      : Reader, Writer��� �߻�Ŭ���� ���      
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ -> �پ��� ����� ����� �����ϴ� Ŭ����
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ ���� -> ���κ��� �Է¹��� ������, ��� ���������� ��Ÿ���� Ŭ����
    - FileIO(���� �����) 
      : FileInputStream, OutputStream, FileReader, FileWriter
    - ByteIO(�迭 �����)
      : ByteArrayInputStream, ByteArrayOutputStream, CharReader, CharWriter
    - �پ��� ��� �����(FileIO,ByteIO�� ���μ� ��� ȿ���� Ȱ��)
      : DataInputStream, DatOutputStream, BufferedReader, PrintWriter
    - �ڹ�IO�� Decorator Pattern�� Ȱ���Ͽ� ����
      : �ϳ��� Ŭ������ ��� �ϴ� ��ó�� �����ڿ��� ���μ� ���ο� ����� ��� �߰� �� �� �ֵ��� Ŭ������ ����� ���
      
  �� AIO 
    - AIO(Asynchronous Input Output)
    - ���� ������� �����Ͽ��� �ٸ� �۾��� ���������� �۾� �� �� ����
    - ������ �۾��� �ʿ���� ���� �� �� ����� ����
      * �� ������ ������ �ʿ��(������ �����ϰ� ���Ͽ� �����ؾ� �Ǵ°��)�� ��� �Ұ�.
    - Path Ŭ������ Ȱ���Ͽ� ��� ���� ������ ����.
    - java.nio.channels ��Ű���� Ŭ����
      01) AsynchronousFileChannel
      02) AsynchronousServerSocketChannel
      03) AsynchronousSocketChannel
    - java.nio.file ��Ű���� Ŭ����
      : Files, Paths
=====================================================================*/
package com.eun1310434.io.aio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AsynchronousNetworkServer {
	public static class Reader extends Thread {
		private AsynchronousSocketChannel clientSocket;
		
		public Reader(AsynchronousSocketChannel clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			while(true) {
				ByteBuffer byteData = ByteBuffer.allocate(100);
				
				//�����͸� ���о����� Ȯ��
				Future<Integer> result = clientSocket.read(byteData);
				
				while(!result.isDone());
				
				byteData.flip();
				String msg = new String(byteData.array()).trim();
				
				
				//Client ���� over �̶� �ۼ��� ���� ����
				if(msg.equals("over")){
					break;
				}
				
				
				try {
					System.out.println(clientSocket.getRemoteAddress() + " >> " + msg);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				byteData.clear();
			}
			
			
			
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] ar) throws Exception {
		AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress("localhost", 54321));
		
		System.out.println("Server Ready...");
		
		while(true) {
			Future<AsynchronousSocketChannel> result = serverSocket.accept();
			AsynchronousSocketChannel clientSocket = result.get();
			
			if(clientSocket != null && clientSocket.isOpen()) {
				new Reader(clientSocket).run();
			}
		}
	}
}
