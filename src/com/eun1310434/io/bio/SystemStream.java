/*=====================================================================
□ Infomation
  ○ Data : 08.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ 문자열 입출력
    - 클래스 이름이 Reader나 Writer로 끝남 → char단위 입출력 클래스

  ○ BufferedReader 
    - readLine() 메소드가 한줄씩 읽음
    - readLine()메소드는 읽어낼 때 더 이상 읽어 들일 내용이 없을 때 null을 리턴

  ○ System.in 
    - 키보드를 의미 (InputStream )
    -InputStream 타입이므로 BufferedReader의 생성자에 바로 들어갈 수 없으므로 InputStreamReader 클래스를 이용

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
package com.eun1310434.io.bio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
public class SystemStream {
	public static void main(String[] ar) throws IOException {
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_SystemStream.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		
		PrintWriter out = 
				new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
		
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(System.in));
		
		out.print("NAME = ");
		out.flush();
		String name = in.readLine();
		
		out.print("Age = ");
		out.flush();
		int age = Integer.parseInt(in.readLine());
		
		out.print("Weight = ");
		out.flush();
		float weight = Float.parseFloat(in.readLine());
		
		out.println();
		
		out.println("Name : " + name);
		out.println("Age : " + age);
		out.println("Weight : " + weight);
		
		in.close();
		out.close();
	}
}