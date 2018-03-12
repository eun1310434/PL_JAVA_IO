/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ ByteBuffer에 시스템 메모리 직접 접근확인
    - ByteBuffer.allocateDirect(128);
  ○ ByteBuffer에 힙 메모리를 거쳐 시스템 메모리 접근확인
    - ByteBuffer.allocate(128);

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
import java.nio.ByteBuffer;

public class ByteBufferAPI01 {
	public static void main(String[] ar) {
		byte[] data = new byte[]{12, 12, 12, 12, 12, 12};//6
		System.out.println("data - size : " + data.length);
		
		ByteBuffer direct = ByteBuffer.allocateDirect(128); // 아주 빠른 처리에 적합하나 부하가 심함
		direct.put(data);
		
		ByteBuffer nonDirect = ByteBuffer.allocate(128); // 일반적인 사용
		nonDirect.put(data);
		
		//Direct
		//시스템 메모리 직접 접근 → 빠름 + 부하높음
		System.out.println("Direct - isDirect : " + direct.isDirect()); 
		
		//시스템 메모리 직접 접근 → 빠름 + 부하높음
		System.out.println("Direct - hasArray : " + direct.hasArray());
		
		//IO 가능
		System.out.println("Direct - isReadOnly : " + direct.isReadOnly());
		System.out.println("Direct - toString() : " + direct.toString());
		
		//Non Direct
		//시스템 메모리 직접 접근 → 느림 + 부하낮음
		System.out.println("Non Direct - isDirect : " + nonDirect.isDirect()); 
		
		//힙메모리 사용 하여 시스템 메모리 접근→ Array필요
		System.out.println("Non Direct - hasArray : " + nonDirect.hasArray());

		//IO 가능
		System.out.println("Non Direct - isReadOnly : " + nonDirect.isReadOnly());
		System.out.println("Non Direct - toString() : " + nonDirect.toString());
	}
}