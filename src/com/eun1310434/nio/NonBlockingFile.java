/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ FileChannel을 활용한 non blacking io 사용

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NonBlockingFile {
	public static void main(String[] ar) throws Exception {
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_NonBlockingFile.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		

		//Data Out
		byte[] data = "eun1310434@naver.com\n".getBytes();

		//ByteBuffer설정
		//ByteBuffer out_byteData = ByteBuffer.wrap(data);//작성할때는 사실 파일의 크기를 알기에 wrap사용
		//ByteBuffer out_byteData = ByteBuffer.allocate(512);//메모리 할당
		ByteBuffer out_byteData = ByteBuffer.allocateDirect(512);//메모리 할당
		out_byteData.put(data);//메모리 입력
		out_byteData.flip();//포지션을 제일 처음으로 위치
		
		//ByteBuffer out_byteData = ByteBuffer.wrap(data);//메모리 입력
		
		FileOutputStream out = new FileOutputStream(file);//파일출력 설정
		
		//Channel 생성 - 1.7버전 전
		FileChannel out_channel = out.getChannel();//체널을 갖고옴... 공유자원 접근
		
		//Channel 생성 - 1.7버전 후 : FileOutputStream없이 직접 할당 가능
		//FileChannel out_channel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.WRITE);
		
		out_channel.write(out_byteData);//채널과 메모리 연결
		out.close();

		
		//Data In
		//ByteBuffer in_byteData = ByteBuffer.allocate(512);//메모리 할당
		ByteBuffer in_byteData = ByteBuffer.allocateDirect(512);//메모리 할당
		
		FileInputStream in = new FileInputStream(file);
		
		FileChannel in_channel = in.getChannel();//채널을 갖고옴... 공유자원 접근
		
		int result = in_channel.read(in_byteData);//채널과 메모리 연결하여 할당 메모리 만큼 읽어옴
		
		while(result != -1){
			in_byteData.flip();//포지션을 제일 처음으로 위치
			
			while(in_byteData.hasRemaining()){
				System.out.print((char)in_byteData.get()); //buffer의 크기만큼 담겨져 있기에 문자가 안나옴
			}
			in_byteData.clear();//깨끗하게 정리
			
			result = in_channel.read(in_byteData);//채널과 메모리 연결하여 할당 메모리 만큼 읽어옴
		}
		in.close();
	}
}
