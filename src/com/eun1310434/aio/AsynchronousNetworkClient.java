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
