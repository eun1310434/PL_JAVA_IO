/*=====================================================================
□ Infomation
  ○ Data : 07.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍

□ Function
  ○ DataStream을 활용한 실제 파일 입출력 활용
      - DataOutputStream, DataInputStream
      - ArrayList 활용
  ○ close 를 호출하지 않아도 자동으로 close되게 하는 방법 : try with resources
    - try-with-resources 블럭 선언
      : close()메소드를 사용자가 호출하지 않더라도, Exception이 발생하지 않았다면 자동으로 close()가 되게 할 수 있는 방법


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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class DataStream02 {
	private static Scanner cin = new Scanner(System.in);
	
	private static File dataFile;
	private static int DataCount= 0;
	private static List<String> STRING = new ArrayList<>();
	private static List<Integer> INTEGER = new ArrayList<>();
	private static List<Double> DOUBLE = new ArrayList<>();
	private static List<Float> FLOAT = new ArrayList<>();
    long startTime = System.currentTimeMillis();   //시간체크
	
	static {
		//Class load시 같이 실행됨.
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		
		dataFile = new File(dir_parent, "DATA_DataStream02.dat");
		if(dataFile.exists()) {
			
			try {
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile), 512));
				DataCount = in.readInt();
				for(int n = 0; n < DataCount; ++n) {
					STRING.add(in.readUTF());
					INTEGER.add(in.readInt());
					DOUBLE.add(in.readDouble());
					FLOAT.add(in.readFloat());
				}
				in.close();
			}catch(Exception ex) {
				System.err.println("Loading error : " + ex.getMessage());
				System.exit(-1);
			}
		}
	}
	
	public static void inputData() {
		DataCount++;
		System.out.print("STRING = ");
		STRING.add(cin.next());
		System.out.print("INTEGER = ");
		INTEGER.add(cin.nextInt());
		System.out.print("DOUBLE = ");
		DOUBLE.add(cin.nextDouble());
		System.out.print("FLOAT = ");
		FLOAT.add(cin.nextFloat());
		System.out.print("총 DataCount : "+DataCount);
	}
	
	public static void outputData() {
		for(int n = 0; n < STRING.size(); ++n) {
			System.out.println("-----------------------");
			System.out.println("DATA : "+(n + 1));
			System.out.println("Stirng : "+STRING.get(n));
			System.out.println("INTEGER : "+INTEGER.get(n));
			System.out.println("DOUBLE : "+DOUBLE.get(n));
			System.out.println("FLOAT : "+FLOAT.get(n));
		}
	}
	
	public static void saveData() {
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile), 512));
			out.writeInt(DataCount);//총 개수 저장
			for(int n = 0; n < DataCount; ++n) {
				out.writeUTF(STRING.get(n));
				out.writeInt(INTEGER.get(n));
				out.writeDouble(DOUBLE.get(n));
				out.writeFloat(FLOAT.get(n));
			}
			out.close();
		}catch(Exception ex) {
			System.err.println("저장 중 오류 발생 : " + ex.getMessage());
			System.exit(-1);
		}
		System.out.printf("총 %d개의 정보를 저장했습니다.\n", DataCount);
	}
	
	public static void main(String[] ar) {
		int command = 0;
		up: while(true) {
			System.out.println("CODE - 1.입력 2.전체출력 3.저장후종료");
			System.out.println("CODE >>");
			command = cin.nextInt();
			switch(command) {
				case 1 : inputData(); break;
				case 2 : outputData(); break;
				case 3 : saveData(); break up;
				default: System.out.println("잘못 입력 하셨습니다.");
			}
			System.out.println("");
		}
		cin.close();
	}
}