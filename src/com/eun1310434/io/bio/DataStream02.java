/*=====================================================================
�� Infomation
  �� Data : 07.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���

�� Function
  �� DataStream�� Ȱ���� ���� ���� ����� Ȱ��
      - DataOutputStream, DataInputStream
      - ArrayList Ȱ��
  �� close �� ȣ������ �ʾƵ� �ڵ����� close�ǰ� �ϴ� ��� : try with resources
    - try-with-resources �� ����
      : close()�޼ҵ带 ����ڰ� ȣ������ �ʴ���, Exception�� �߻����� �ʾҴٸ� �ڵ����� close()�� �ǰ� �� �� �ִ� ���


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
    long startTime = System.currentTimeMillis();   //�ð�üũ
	
	static {
		//Class load�� ���� �����.
		
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
		System.out.print("�� DataCount : "+DataCount);
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
			out.writeInt(DataCount);//�� ���� ����
			for(int n = 0; n < DataCount; ++n) {
				out.writeUTF(STRING.get(n));
				out.writeInt(INTEGER.get(n));
				out.writeDouble(DOUBLE.get(n));
				out.writeFloat(FLOAT.get(n));
			}
			out.close();
		}catch(Exception ex) {
			System.err.println("���� �� ���� �߻� : " + ex.getMessage());
			System.exit(-1);
		}
		System.out.printf("�� %d���� ������ �����߽��ϴ�.\n", DataCount);
	}
	
	public static void main(String[] ar) {
		int command = 0;
		up: while(true) {
			System.out.println("CODE - 1.�Է� 2.��ü��� 3.����������");
			System.out.println("CODE >>");
			command = cin.nextInt();
			switch(command) {
				case 1 : inputData(); break;
				case 2 : outputData(); break;
				case 3 : saveData(); break up;
				default: System.out.println("�߸� �Է� �ϼ̽��ϴ�.");
			}
			System.out.println("");
		}
		cin.close();
	}
}