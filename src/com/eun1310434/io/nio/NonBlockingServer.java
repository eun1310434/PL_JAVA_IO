/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ ByteBuffer를 활용한 네트워크 서버
    - NonBlocking IO
	- Blocking IO


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
      
  ○ NIO 
    - BIO(블로킹, 바이트 스트림 기반) → NIO(넌블로킹, 버퍼를 사용하는 채널 기반)
    - BIO(힙영역에 메모리를 사용 시스템 메모리 접근) → NIO(ByteBuffer를 사용 바로 시스템에 접근하여 메모리를 할당)
    - 공유자원 접근 가능, 실행속도가 매우 증가, 부하도 증가
    - Blocking? : 어떠한 프로그램에서 파일을 사용하고 있으면, 다른 사용자는 해당파일을 접근하지 못함 의미
    
  ○ 구성
    - Buffer ← ByteBuffer ← MappedByteBuffer
             ← CharBuffer
             ← SherBuffer
             ← intBuffer
             ← LongBuffer
             ← DoubleBuffer
=====================================================================*/
package com.eun1310434.io.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockingServer {
	public static void main(String[] ar) throws Exception {
		Selector selector = Selector.open();
		
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress("localhost", 54321));
		serverSocket.configureBlocking(false);
		serverSocket.register(selector, serverSocket.validOps());
		
		System.out.println("Server Ready...");
		
		while(true) {
			selector.select();
			
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			
			while(selectedKeys.hasNext()) {
				SelectionKey selectedKey = selectedKeys.next();
				
				if(selectedKey.isAcceptable()) {
					SocketChannel client = serverSocket.accept();
					client.configureBlocking(false);
					client.register(selector, SelectionKey.OP_READ);
					System.out.println("Connection : " + client.getLocalAddress());
				
				} else if(selectedKey.isReadable()) {
					
					//체널을 획득
					SocketChannel client = (SocketChannel)selectedKey.channel();
					
					//시스템
					ByteBuffer byteData = ByteBuffer.allocateDirect(256);//시스템 메모리 사용시
					//ByteBuffer byteData = ByteBuffer.allocate(256);//힙 메모리 사용시
					client.read(byteData);

					String receiveData ="";
					if(byteData.hasArray()){
						//힙 메모리 사용시
						receiveData = new String(byteData.array()).trim();
					}else{
						//시스템 메모리 사용시
						byteData.flip();//포지션을 제일 처음으로 위치
						while(byteData.hasRemaining()){
							receiveData = receiveData  + (char) byteData.get();//buffer의 크기만큼 담겨져 있기에 문자가 안나옴
						}
						byteData.clear();//깨끗하게 정리
					}

					if(receiveData.equalsIgnoreCase("over")) {
						client.close();
					} else {
						System.out.println("Message : " + receiveData);
					}
				
				}
				selectedKeys.remove();
			}
		}
	}
}
