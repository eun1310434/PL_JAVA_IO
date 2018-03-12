/*=====================================================================
□ Infomation
  ○ Data : 07.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ Byte단위 입출력 클래스
    - 클래스의 이름이 InputStream이나 OutputStream으로 끝납니다.
    - 파일로 부터 1byte씩 읽어들여 파일에 1byte씩 저장하는 프로그램을 작성

  ○ ByteStream01 클래스보다 더욱 빠름
    - byte array를 활용하여 데이터를 갖고 오는데 512의 배수로 크기를 설정
    - 1byte씩 read 하여도 보통 512byte씩 읽어 옴 <- 매우 비효율적
	  : 512byte 중 1byte를 읽어와서 511byte는 버림 그렇기에 512byte의 배수로 파일을 읽어오는 것이 바람직함


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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStream02 {
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
		String dir = dir_parent + "DATA_ByteStream02.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		//Data Out
		FileOutputStream out = new FileOutputStream(file);
		buffer = "eun1310434@naver.com\n".getBytes();
		out.write(buffer,0,buffer.length);//0번째 부터 끝까지 작성
		out.write(buffer);//전체 다 작성
		out.close();
		
		//Data In
		FileInputStream in = new FileInputStream(file);
		//Data In
		while(true) {
			//FileInputStream과 FileOutputStream는 1바이트씩 읽어들여 1바이트씩 저장
			//return type은 정수 이며, 마지막 1byte에 데이터를 저장한 4byte(정수)로 읽어드림
			//이는 읽어드리는 값이 없을 시 음수를 return하기 위함
			int readCount = in.read(buffer);
			
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
        System.out.print("수행시간 : " + (endTime-startTime)); 
	}
}