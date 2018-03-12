/*=====================================================================
�� Infomation
  �� Data : 08.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� ���ڿ� �����
    - Ŭ���� �̸��� Reader�� Writer�� ���� �� char���� ����� Ŭ����

  �� BufferedReader 
    - readLine() �޼ҵ尡 ���پ� ����
    - readLine()�޼ҵ�� �о �� �� �̻� �о� ���� ������ ���� �� null�� ����

  �� System.in 
    - Ű���带 �ǹ� (InputStream )
    -InputStream Ÿ���̹Ƿ� BufferedReader�� �����ڿ� �ٷ� �� �� �����Ƿ� InputStreamReader Ŭ������ �̿�

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