/*=====================================================================
□ Infomation
  ○ Data : 07.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ BufferedStream을 활용한 파일 입출력
    - BufferedInputStream(BufferedOutputStream)은 바이트 입력(출력) 스트림에 연결되어 버퍼를 제공해주는 보조 스트림
  ○ 빠른속도 보장 : 메모리 버퍼에 데이터를 보냄으로써 쓰기 속도가 향상
    - 버퍼는 데이터가 쌓이기를 기다렸다가 꽉 차게 되면 데이터를 한꺼번에 하드 디스크로 보냄으로써 출력 횟수를 줄여줌
    - 프로그램 ← FileInputStream                       ← 파일(HDD) : 속도 느림 
    - 프로그램 ← BufferedInputStream ← FileInputStream ← 파일(HDD) : 속도 빠름 
    - 프로그램의 실행 성능은 입출력이 가장 늦은 장치를 따라감. 
      CPU와 메모리가 아무리 뛰어나도 하드 디스크의 입출력이 늦어지면 프로그램의 실행 속도는 하드 디스크의 처리 속도 따라 느려짐
              느린 네트워크 환경이라면 컴퓨터 사양이 아무리 좋아도 메신저나 게임의 속도가 느려짐 
      → 프로그램이 입출력 소스와 직접 작업하지 않고 중간에 메모리 버퍼(buffer)와 작업함으로써 실행 성능을 향상시킴
                  예를 들어 프로그램이 직접 하드 디스크에 데이터를 보내지 않고 메모리 버퍼에 데이터를 보냄으로써 쓰기 속도가 향상된다. 
                  버퍼는 데이터가 쌓이기를 기다렸다가 꽉 차게 되면 데이터를 한꺼번에 하드 디스크로 보냄으로써 출력 횟수를 줄여줌
    - 보조 스트림 중에서 이와 같이 메모리 버퍼를 제공하는 것이 BufferedInputStream, BufferedOutputStream
      (BufferedReader, BufferedWriter 도 마찬가지인데 얘네는 바이트가 아니라 문자 기반 스트림)    
    - 버퍼사이즈는 마음데로 설정해도 되는가?
      → It depends on a lot of factors, there's no universally "optimal" size. 
      → 512kB is probably good enough.
      → If you want, you can always benchmark it for various buffer sizes: 
      → this will let you know what the best option is for your computer and OS.
  ○ BufferedStream을 활용하면 외부적으로는 큰 변화는 없으나 내부적으로 효과적으로 관리 가능
  ○ BufferedStream은 스스로 파일에 접근하지 못함 FileStream을 사용해야 함
  ○ BufferedStream은  FileStream의 바이트 입출력스트림을 상속하여 실제 필터기능을 제공하는 클래스


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
      
  ○ BIO 
    - Basic(Clocking) Input Output
    - 입출력시 멈춤, 다양한 입출력 필요시 대기가 발생.
    - 공유자원 접근 불가

  ○ 구성
    - Writer  ← BufferWriter
              ← CharArrayWriter
              ← FilterWriter
              ← OutputStreamWriter ← FileWriter
              ← PipedWriter
              ← PrintWriter
              ← StringWriter

    - Reader ← BufferReader  ← LineNumberReader
              ← CharArrayReader
              ← FilterReader ← PushbackReader
              ← InputStreamReader ← FileWriter
              ← PipedReader
              ← StringReader
              
=====================================================================*/

package com.eun1310434.bio;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferStream {
	public static void main(String[] ar) throws IOException {
        
		long startTime = System.currentTimeMillis();   //시간체크
        
        //buffer사이즈만큼 읽어들이고, 쓸때도 buffer사이즈만큼 쓰게해서 같은크기의 데이타를 읽고 쓰게함으로 누락되는 데이타를 없게함
		byte [] buffer = new byte[32];
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_BufferStream.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		//Data Out
		FileOutputStream out = new FileOutputStream(file);
		
		//임시로 저장할 버퍼의 크기를 1024byte로 마음대로 조절
		//즉 파일에 데이터를 쓰기 위해서는 1024byte가 모여야 보조기억장치에 접근하여 작성
		//메모리 버퍼에 데이터를 보냄으로써 쓰기 속도가 향상
		//버퍼는 데이터가 쌓이기를 기다렸다가 꽉 차게 되면 데이터를 한꺼번에 하드 디스크로 보냄으로써 출력 횟수를 줄여줌
		//버퍼 크기는 OS에 따라 다르며 보통 512로 설정
		BufferedOutputStream out2 = new BufferedOutputStream(out, 1024); //내부버퍼 사이트 1024
		
		//만약 버퍼의 크기를 지정하지 않을 시 512의 크기로 default 설정
		//BufferedOutputStream out2 = new BufferedOutputStream(out);
		
		buffer = "eun1310434@naver.com\n".getBytes();
		out.write(buffer,0,buffer.length);//0번째 부터 끝까지 작성
		out2.write(buffer);//전체 다 작성
		out2.close();
		
		//Data In
		FileInputStream in = new FileInputStream(file);
		
		//임시로 저장할 버퍼의 크기를 1024byte로 마음대로 조절
		//즉 파일에 데이터를 쓰기 위해서는 1024byte가 모여야 보조기억장치에 접근하여 작성
		//출력 스트림을 지정된 버퍼사이즈(1024byte)로 생성
		//메모리 버퍼에 데이터를 보냄으로써 쓰기 속도가 향상
		//버퍼는 데이터가 쌓이기를 기다렸다가 꽉 차게 되면 데이터를 한꺼번에 하드 디스크로 보냄으로써 출력 횟수를 줄여줌
		//버퍼 크기는 OS에 따라 다르며 보통 512로 설정
		BufferedInputStream in2 = new BufferedInputStream(in, 1024);//내부버퍼 사이트 1024

		//만약 버퍼의 크기를 지정하지 않을 시 512의 크기로 default 설정
		//BufferedInputStream in2 = new BufferedInputStream(in); 

		//Data In
		while(true) {
			//FileInputStream과 FileOutputStream는 1바이트씩 읽어들여 1바이트씩 저장
			//return type은 정수 이며, 마지막 1byte에 데이터를 저장한 4byte(정수)로 읽어드림
			//이는 읽어드리는 값이 없을 시 음수를 return하기 위함
			int readCount = in2.read(buffer);
			
			if(readCount < 0) { //읽어드린 값이 없을시 음수 
				//System.out.println(readCount);//-1로 찍히고 종료됨
				break;
			}
			
			for(int i = 0 ; i < buffer.length ; i++){
				System.out.print((char)buffer[i]); //buffer의 크기만큼 담겨져 있기에 문자가 안나옴
			}
			//System.out.print(new String(buffer));//위에있는 for문과 같음
		}
		
		//꼭 닫기
		in.close();	

		
		
        //메소드가 끝났을때 시간 확인
        long endTime = System.currentTimeMillis();
        //메소드를 수행하는데 걸린 시간 확인
        System.out.println("수행시간 : " + (endTime-startTime));
	}
}