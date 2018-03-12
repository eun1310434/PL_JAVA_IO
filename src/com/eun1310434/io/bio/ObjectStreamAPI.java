/*=====================================================================
�� Infomation
  �� Data : 08.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� ������Ʈ ��Ʈ���� Ȱ���� ���� �����
  �� ������Ʈ ������ ������ ����Ʈ�� �������
    - list = (ArrayList<ObjectStreamAPI_Info>) in.readObject();

�� Study
  �� IO
    - IO  �� Byte �� InputStream(:abstract class)
                 �� OutputStream(:abstract class)
          �� Char �� Reader(:abstract class)
                 �� Writer(:abstract class)
    - byte���� �����Ŭ���� 
      : InputStream, OutputStream �߻�Ŭ���� ���
    - char(����)���� �����Ŭ����
      : Reader, Writer��� �߻�Ŭ���� ���      
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ -> �پ��� ����� ����� �����ϴ� Ŭ����
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ ���� -> ���κ��� �Է¹��� ������, ��� ���������� ��Ÿ���� Ŭ����
    - FileIO(���� �����) 
      : FileInputStream, OutputStream, FileReader, FileWriter
    - ByteIO(�迭 �����)
      : ByteArrayInputStream, ByteArrayOutputStream, CharReader, CharWriter
    - �پ��� ��� �����(FileIO,ByteIO�� ���μ� ��� ȿ���� Ȱ��)
      : DataInputStream, DatOutputStream, BufferedReader, PrintWriter
    - �ڹ�IO�� Decorator Pattern�� Ȱ���Ͽ� ����
      : �ϳ��� Ŭ������ ��� �ϴ� ��ó�� �����ڿ��� ���μ� ���ο� ����� ��� �߰� �� �� �ֵ��� Ŭ������ ����� ���
      
  �� BIO 
    - Basic(Clocking) Input Output
    - ����½� ����, �پ��� ����� �ʿ�� ��Ⱑ �߻�.
    - �����ڿ� ���� �Ұ�

  �� ����
    - Writer  �� BufferWriter
              �� CharArrayWriter
              �� FilterWriter
              �� OutputStreamWriter �� FileWriter
              �� PipedWriter
              �� PrintWriter
              �� StringWriter

    - Reader �� BufferReader  �� LineNumberReader
              �� CharArrayReader
              �� FilterReader �� PushbackReader
              �� InputStreamReader �� FileWriter
              �� PipedReader
              �� StringReader
              
=====================================================================*/
package com.eun1310434.io.bio;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ObjectStreamAPI_Info implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _string;
	private int _int;
	private double _double;
	private float _float;
	
	public ObjectStreamAPI_Info(String _string, int _int, double _double, float _float) {
		this._string = _string;
		this._int = _int;
		this._double = _double;
		this._float = _float;
	}
	
	public void display() {
		System.out.println("___________________________________");
		System.out.println("String : "+_string);
		System.out.println("Integer : "+_int);
		System.out.println("Double : "+_double);
		System.out.println("Float : "+_float);
	}
}


@SuppressWarnings("unchecked")
public class ObjectStreamAPI {
	
	private static Scanner cin = new Scanner(System.in);
	private static File dataFile;
	private static List<ObjectStreamAPI_Info> list = new ArrayList<>();
	
	static {
		//Ŭ������ �����ʰ� ���ÿ� ����
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}

		//Data File Setting
		dataFile = new File(dir_parent,"DATA_ObjectStreamAPI.dat" );
		if(dataFile.exists()){
			try {
				ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(dataFile), 512));
				
				//������Ʈ ������ ������ ����Ʈ�� �������
				list = (ArrayList<ObjectStreamAPI_Info>) in.readObject();
				
				in.close();
			}catch(Exception ex) {
				System.err.println("IOException : " + ex.getMessage().toString());
				System.exit(-1);
			}
		}
	}
	
	public static void inputData() {
		System.out.print("String = ");
		String _string = cin.next();
		
		System.out.print("Integer = ");
		int _integer = cin.nextInt();
		
		System.out.print("Double = ");
		double _double = cin.nextDouble();
		
		System.out.print("Float = ");
		float _float = cin.nextFloat();
		
		ObjectStreamAPI_Info info = new ObjectStreamAPI_Info(_string, _integer, _double, _float);
		list.add(info);
	}
	
	public static void outputData() {
		for(ObjectStreamAPI_Info info: list) {
			info.display();
			System.out.println("");
		}
	}	
	
	public static void saveData() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile), 512));
			out.writeObject(list);
			out.close();
			
		}catch(Exception ex) {
			System.err.println("SAVE ERROR" + ex.getMessage());
			System.exit(-1);
		}
		System.out.printf("Total Info Count : %d \n", list.size());
	}
	
	
	public static void main(String[] ar) {
		int command = 0;
		up: while(true) {
			System.out.println("1.Insert");
			System.out.println("2.Print");
			System.out.print("3.Finish(save)");
			
			command = cin.nextInt();
			
			switch(command) {
				case 1 : inputData(); break;
				case 2 : outputData(); break;
				case 3 : saveData(); break up;
				default: System.out.println("wrong insert");
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
		cin.close();
	}
}