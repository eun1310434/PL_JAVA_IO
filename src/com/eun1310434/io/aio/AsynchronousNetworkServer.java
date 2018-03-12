/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr, 

□ Function
  ○ 

□ Study
  ○ IO
    - IO  ← Byte ← InputStream(:abstract class)
                 ← OutputStream(:abstract class)
          ← Char ← Reader(:abstract class)
                 ← Writer(:abstract class)
    - byte단위 입출력클래스 
      : InputStream, OutputStream 추상클래스 상속
    - char(문자)단위 입출력클래스
      : Reader, Writer라는 추상클래스 상속      
    - 4가지 추상클래스를 받아들이는 생성자 -> 다양한 입출력 방법을 제공하는 클래스
    - 4가지 추상클래스를 받아들이는 생성자 없음 -> 어디로부터 입력받을 것인지, 어디에 쓸것인지를 나타내는 클래스
    - FileIO(파일 입출력) 
      : FileInputStream, OutputStream, FileReader, FileWriter
    - ByteIO(배열 입출력)
      : ByteArrayInputStream, ByteArrayOutputStream, CharReader, CharWriter
    - 다양한 방식 입출력(FileIO,ByteIO를 감싸서 기능 효율적 활용)
      : DataInputStream, DatOutputStream, BufferedReader, PrintWriter
    - 자바IO는 Decorator Pattern을 활용하여 만듬
      : 하나의 클래스를 장식 하는 것처럼 생성자에서 감싸서 새로운 기능을 계속 추가 할 수 있도록 클래스를 만드는 방식
      
  ○ AIO 
    - AIO(Asynchronous Input Output)
    - 파일 입출력을 실행하여도 다른 작업을 지속적으로 작업 할 수 있음
    - 순차적 작업이 필요없는 일을 할 때 사용이 가능
      * 단 순차적 적업이 필요시(파일을 생성하고 파일에 접근해야 되는경우)에 사용 불가.
    - Path 클래스를 활용하여 경로 설정 관리가 편함.
    - java.nio.channels 패키지의 클래스
      01) AsynchronousFileChannel
      02) AsynchronousServerSocketChannel
      03) AsynchronousSocketChannel
    - java.nio.file 패키지의 클래스
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
				
				//데이터를 다읽었는지 확인
				Future<Integer> result = clientSocket.read(byteData);
				
				while(!result.isDone());
				
				byteData.flip();
				String msg = new String(byteData.array()).trim();
				
				
				//Client 에서 over 이라 작성시 서버 종료
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
