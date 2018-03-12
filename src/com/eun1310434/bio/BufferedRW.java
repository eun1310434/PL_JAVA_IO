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
    - ���Ͽ��� �� �پ� �Է� �޾Ƽ� ���Ͽ� ���
    - readLine() �޼ҵ尡 ���پ� ����
    - readLine()�޼ҵ�� �о �� �� �̻� �о� ���� ������ ���� �� null�� ����
    
  �� FileRW
    - ���Ͽ� ���ϰ� �����ϱ� ���ؼ� FileRW Ŭ���� �̿�
    - new FileWriter(file, true) == new OutputStreamWriter(new FileOutputStream(file,true))
    - new FileReader(file) == new InputStreamReader(new FileInputStream(file))

  �� PrintWriter
    - ���Ͽ� ���ϰ� �����ϱ� ���ؼ� �̿�
    - PrintWriter ������ �ٷ� File�� �޿��°��� ������ - PrintWriter out = new PrintWriter(file);

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
package com.eun1310434.bio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
public class BufferedRW {
	public static void main(String[] ar) throws Exception {
		Scanner cin = new Scanner(System.in);
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_BufferedRW.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		
		System.out.print("NAME : ");
		String title = cin.nextLine();
		
		System.out.print("WRITER : ");
		String author = cin.nextLine();
		
		System.out.println("CONTENTS - If you finished..Please insert '.' + enter. \n: ");
		StringBuilder contents = new StringBuilder();
		while(true) {
			String str = cin.nextLine();
			if(str.trim().equals(".")){break;}
			contents.append(str + "\n");
		}
		cin.close();

		//Data Out
		//PrintWriter : ���Ͽ� ���ϰ� �����ϱ� ���ؼ� �̿�
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)), 512), true);

		//FileWriter�� ���� �ٷ� File�� �޿��°��� ������
		//new OutputStreamWriter(new FileOutputStream(file,true)) -> new FileWriter(file, true)
		//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true), 512), true);
		
		
		//PrintWriter ������ �ٷ� File�� �޿��°��� ������
		//PrintWriter out = new PrintWriter(file);
		
		out.println("NAME : " + title);
		out.println("WRITER : " + author);
		out.println("CONTENTS : " + contents);
		out.close();
		

		//Data In
		//BufferedReader - ���پ� �Է� �ޱ����� Ŭ����
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		//new InputStreamReader(new FileInputStream(file))-> new FileReader(file)
		//BufferedReader in = new BufferedReader(new FileReader(file)); 
		while(true) {
			String str = in.readLine();
			if(str == null){break;}
			System.out.println(str);
		}
		in.close();
	}
}