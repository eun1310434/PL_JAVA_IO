/*=====================================================================
�� Infomation
  �� Data : 07.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� Byte���� ����� Ŭ����
    - Ŭ������ �̸��� InputStream�̳� OutputStream���� �����ϴ�.
    - ���Ϸ� ���� 1byte�� �о�鿩 ���Ͽ� 1byte�� �����ϴ� ���α׷��� �ۼ�


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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStream01 {
	public static void main(String[] ar) throws IOException {

        long startTime = System.currentTimeMillis();   //�ð�üũ
        
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_ByteStream01.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		//Data Out
		FileOutputStream out = new FileOutputStream(file);
		byte outStrByte[] = "eun1310434@naver.com\n".getBytes();
		out.write(outStrByte,0,outStrByte.length);//0��° ���� 12��° ���� �ۼ�
		out.write(outStrByte);//��ü �� �ۼ�
		out.close();
		
		
		//Data In
		FileInputStream in = new FileInputStream(file);
		while(true) {
			//FileInputStream�� FileOutputStream�� 1����Ʈ�� �о�鿩 1����Ʈ�� ����
			//return type�� ���� �̸�, ������ 1byte�� �����͸� ������ 4byte(����)�� �о�帲
			//�̴� �о�帮�� ���� ���� �� ������ return�ϱ� ����
			int readCount = in.read();
			if(readCount < 0) { //�о�帰 ���� ������ ���� 
				//System.out.println(readCount);//-1�� ������ �����
				break;
			}
			//System.out.println(readCount);
			System.out.print((char)readCount);
		}
		
		//�� �ݱ�
		in.close();		
		
        //�޼ҵ尡 �������� �ð� Ȯ��
        long endTime = System.currentTimeMillis();
        //�޼ҵ带 �����ϴµ� �ɸ� �ð� Ȯ��
        System.out.println("����ð� : " + (endTime-startTime));  
	}
}