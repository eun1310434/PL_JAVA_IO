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
package com.eun1310434.aio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.Future;

public class AsynchronousNetworkClient {
	public static void main(String[] ar) throws Exception {
		Scanner in = new Scanner(System.in);
		AsynchronousSocketChannel clientSocket = AsynchronousSocketChannel.open();
		clientSocket.connect(new InetSocketAddress("localhost", 54321)).get();
		
		while(true) {
			System.out.print("Send Message = ");
			String msg = in.nextLine();
			ByteBuffer byteData = ByteBuffer.wrap(msg.getBytes());
			
			Future<Integer> sendResult = clientSocket.write(byteData);
			
			while(!sendResult.isDone());
			
			byteData.clear();
			Thread.sleep(100);
			if(msg.trim().equalsIgnoreCase("over")) break;
		}
		
		in.close();
		clientSocket.close();
	}
}
