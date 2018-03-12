/*=====================================================================
�� Infomation
  �� Data : 07.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� DataStream�� Ȱ���� ���� ����� Ȱ��
    - DataOutputStream, DataInputStream
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

package com.eun1310434.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataStream01 {
	public static void main(String[] ar) {
        long startTime = System.currentTimeMillis();   //�ð�üũ
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_DataStream01.txt";
		File file = new File(dir);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//close �� ȣ������ �ʾƵ� �ڵ����� close�ǰ� �ϴ� ��� : try with resources
        try(//io��ü ����
        		//������ out
        		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        		//������ in
        		DataInputStream in = new DataInputStream(new FileInputStream(file));
        		
        ){//io��ü ���
    		out.writeInt(63);
    		out.writeBoolean(true);
    		
    		System.out.println(in.readInt()); // <- out.writeInt(63); �о� ��
    		System.out.println(in.readBoolean()); // <- out.writeBoolean(true);
    		
        }catch(Exception ex){
            ex.printStackTrace();
        }
		
		/*
		 * ���� ���Ͽ� ����� ���� 
		 * :   ? <-������ Ÿ������ �����
		 */

        //�޼ҵ尡 �������� �ð��� ���ϱ� ����. 
        long endTime = System.currentTimeMillis();
        //�޼ҵ带 �����ϴµ� �ɸ� �ð��� ���� �� ����. 
        System.out.println("����ð� : " + (endTime-startTime)); 
	}
}