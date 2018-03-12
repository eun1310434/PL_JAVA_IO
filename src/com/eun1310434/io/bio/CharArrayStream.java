/*=====================================================================
�� Infomation
    �� Data : 02.03.2018
    �� Mail : eun1310434@naver.com
    �� Blog : https://blog.naver.com/eun1310434
    �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���


�� Function
  �� ���ڿ� �����
    - Ŭ���� �̸��� Reader�� Writer�� ���� �� char���� ����� Ŭ����

  �� CharArrayStream�� Ȱ���� ���� ����� Ȱ��
    - CharArrayWriter, CharArrayReader
      
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
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
public class CharArrayStream {
	public static void main(String[] ar) throws IOException {

		//Data Out
		CharArrayWriter out = new CharArrayWriter();
		out.write("eun1310434\n"); //���� �Է�
		
		char[] ch = new char[]{'A', 'B', 'C', 'D', 'E', '\n'};
		out.write(ch); // ��ü�� �����
		out.write(ch, 0, 2); // 0 ~ 2������ ���
		out.close();
		
		char[] data = out.toCharArray(); // out�� �����͸� ���� ��

		//Data In
		CharArrayReader in = new CharArrayReader(data);
		while(true) {
			int x = in.read();
			if(x == -1) break;
			System.out.print((char)x);
		}
		in.close();
	}
}