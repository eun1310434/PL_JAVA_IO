/*=====================================================================
□ Infomation
  ○ Data : 07.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ PrintStream을 활용한 파일 입출력
  ○ BufferOutStream 보다 편리  


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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class PrintStreamAPI {
	public static void main(String[] ar) throws IOException {
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_PrintStreamAPI.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		//Data Out
		FileOutputStream out = new FileOutputStream(file);
		BufferedOutputStream out2 = new BufferedOutputStream(out, 512); // 512byte로 저장
		PrintStream out3 = new PrintStream(out2);
		out3.println("eun1310434@naver.com");
		out3.close();
		
		//Data In
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream in2 = new BufferedInputStream(in, 512); // 512byte로 저장
		while(true) {
			//FileInputStream과 FileOutputStream는 1바이트씩 읽어들여 1바이트씩 저장
			//return type은 정수 이며, 마지막 1byte에 데이터를 저장한 4byte(정수)로 읽어드림
			//이는 읽어드리는 값이 없을 시 음수를 return하기 위함
			int readCount = in2.read();
			if(readCount < 0) { //읽어드린 값이 없을시 음수 
				//System.out.println(readCount);//-1로 찍히고 종료됨
				break;
			}
			System.out.print((char)readCount);
		}
		//꼭 닫기
		in.close();		
	}
}