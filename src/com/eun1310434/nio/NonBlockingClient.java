/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ NonBlocking IO를 활용한 네트워크 클라이언트

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
package com.eun1310434.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingClient {
	public static void main(String[] ar) throws Exception {
		SocketChannel client = SocketChannel.open(
				new InetSocketAddress("localhost", 54321));
		
		byte[] data = "Hello There!!!".getBytes();
		byte[] data2 = "over".getBytes();
		ByteBuffer byteData = ByteBuffer.wrap(data);
		client.write(byteData);
		byteData.clear();
		Thread.sleep(100);
		client.write(ByteBuffer.wrap(data2));
		byteData.clear();
		
		client.close();
		
		System.out.println("Send Data!!!");
	}
}
