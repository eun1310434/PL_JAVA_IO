/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ ByteBuffer의 메모리 자르기
    - byteBuffer.slice()

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

public class ByteBufferAPI02 {

	public static void main(String[] ar) {
		byte[] data = "eun1310434@naver.com\n".getBytes();
		
		//시스템 메모리에 직접 할당
		//ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
		
		//힙메모리에 할당
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		byteBuffer.put(data);

		//시스템 메모리에 직접 할당이 아닐시에는 힙 메모리를 사용하며 Array를 활용
		//시스템 메모리 -> hasArray() = false
		//힙 메모리 -> hasArray() = true
		System.out.println("byteBuffer.hasArray() : "+ byteBuffer.hasArray());
		
		
		byte[] slice = null;
		ByteBuffer byteBuffer_slice = null;
		
		//복사
		if(byteBuffer.hasArray()){
			//힙 메모리 사용시
			//byteBuffer.array()는 byteBuffer의 전체 공유
			slice = byteBuffer.array();
		}else{
			//시스템 메모리 사용시
			//slice()는 byteBuffer의 전체 크기중 사용한 곳을 제외한 크기를 공유
			byteBuffer_slice = byteBuffer.slice();
		}

		//byteBuffer에 put을 하게 되면 byteBuffer_slice와 slice에 또한 동일한 메모리를 공유하기에 같이보임
		byteBuffer.put("origin insert\n".getBytes());
		
		
		//출력
		//원본
		System.out.println("origin---------------------------------");
		byteBuffer.flip();
		while(byteBuffer.hasRemaining()){
			System.out.print((char)byteBuffer.get()); //buffer의 크기만큼 담겨져 있기에 문자가 안나옴
		}
		byteBuffer.clear();//깨끗하게 정리
		
		
		//복사본
		if(byteBuffer_slice == null){
			System.out.println("slice---------------------------------");
			for(int i = 0; i < slice.length; ++i) {
				System.out.print((char)slice[i]);
			}
		}else{
			System.out.println("byteBuffer_slice---------------------------------");
			while(byteBuffer_slice.hasRemaining()){
				System.out.print((char)byteBuffer_slice.get()); //buffer의 크기만큼 담겨져 있기에 문자가 안나옴
			}
			byteBuffer_slice.clear();//깨끗하게 정리
		}
	}
}