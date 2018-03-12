/*=====================================================================
�� Infomation
  �� Data : 07.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� BufferedStream�� Ȱ���� ���� �����
    - BufferedInputStream(BufferedOutputStream)�� ����Ʈ �Է�(���) ��Ʈ���� ����Ǿ� ���۸� �������ִ� ���� ��Ʈ��
  �� �����ӵ� ���� : �޸� ���ۿ� �����͸� �������ν� ���� �ӵ��� ���
    - ���۴� �����Ͱ� ���̱⸦ ��ٷȴٰ� �� ���� �Ǹ� �����͸� �Ѳ����� �ϵ� ��ũ�� �������ν� ��� Ƚ���� �ٿ���
    - ���α׷� �� FileInputStream                       �� ����(HDD) : �ӵ� ���� 
    - ���α׷� �� BufferedInputStream �� FileInputStream �� ����(HDD) : �ӵ� ���� 
    - ���α׷��� ���� ������ ������� ���� ���� ��ġ�� ����. 
      CPU�� �޸𸮰� �ƹ��� �پ�� �ϵ� ��ũ�� ������� �ʾ����� ���α׷��� ���� �ӵ��� �ϵ� ��ũ�� ó�� �ӵ� ���� ������
              ���� ��Ʈ��ũ ȯ���̶�� ��ǻ�� ����� �ƹ��� ���Ƶ� �޽����� ������ �ӵ��� ������ 
      �� ���α׷��� ����� �ҽ��� ���� �۾����� �ʰ� �߰��� �޸� ����(buffer)�� �۾������ν� ���� ������ ����Ŵ
                  ���� ��� ���α׷��� ���� �ϵ� ��ũ�� �����͸� ������ �ʰ� �޸� ���ۿ� �����͸� �������ν� ���� �ӵ��� ���ȴ�. 
                  ���۴� �����Ͱ� ���̱⸦ ��ٷȴٰ� �� ���� �Ǹ� �����͸� �Ѳ����� �ϵ� ��ũ�� �������ν� ��� Ƚ���� �ٿ���
    - ���� ��Ʈ�� �߿��� �̿� ���� �޸� ���۸� �����ϴ� ���� BufferedInputStream, BufferedOutputStream
      (BufferedReader, BufferedWriter �� ���������ε� ��״� ����Ʈ�� �ƴ϶� ���� ��� ��Ʈ��)    
    - ���ۻ������ �������� �����ص� �Ǵ°�?
      �� It depends on a lot of factors, there's no universally "optimal" size. 
      �� 512kB is probably good enough.
      �� If you want, you can always benchmark it for various buffer sizes: 
      �� this will let you know what the best option is for your computer and OS.
  �� BufferedStream�� Ȱ���ϸ� �ܺ������δ� ū ��ȭ�� ������ ���������� ȿ�������� ���� ����
  �� BufferedStream�� ������ ���Ͽ� �������� ���� FileStream�� ����ؾ� ��
  �� BufferedStream��  FileStream�� ����Ʈ ����½�Ʈ���� ����Ͽ� ���� ���ͱ���� �����ϴ� Ŭ����


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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferStream {
	public static void main(String[] ar) throws IOException {
        
		long startTime = System.currentTimeMillis();   //�ð�üũ
        
        //buffer�����ŭ �о���̰�, ������ buffer�����ŭ �����ؼ� ����ũ���� ����Ÿ�� �а� ���������� �����Ǵ� ����Ÿ�� ������
		byte [] buffer = new byte[32];
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_BufferStream.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		
		//Data Out
		FileOutputStream out = new FileOutputStream(file);
		
		//�ӽ÷� ������ ������ ũ�⸦ 1024byte�� ������� ����
		//�� ���Ͽ� �����͸� ���� ���ؼ��� 1024byte�� �𿩾� ���������ġ�� �����Ͽ� �ۼ�
		//�޸� ���ۿ� �����͸� �������ν� ���� �ӵ��� ���
		//���۴� �����Ͱ� ���̱⸦ ��ٷȴٰ� �� ���� �Ǹ� �����͸� �Ѳ����� �ϵ� ��ũ�� �������ν� ��� Ƚ���� �ٿ���
		//���� ũ��� OS�� ���� �ٸ��� ���� 512�� ����
		BufferedOutputStream out2 = new BufferedOutputStream(out, 1024); //���ι��� ����Ʈ 1024
		
		//���� ������ ũ�⸦ �������� ���� �� 512�� ũ��� default ����
		//BufferedOutputStream out2 = new BufferedOutputStream(out);
		
		buffer = "eun1310434@naver.com\n".getBytes();
		out.write(buffer,0,buffer.length);//0��° ���� ������ �ۼ�
		out2.write(buffer);//��ü �� �ۼ�
		out2.close();
		
		//Data In
		FileInputStream in = new FileInputStream(file);
		
		//�ӽ÷� ������ ������ ũ�⸦ 1024byte�� ������� ����
		//�� ���Ͽ� �����͸� ���� ���ؼ��� 1024byte�� �𿩾� ���������ġ�� �����Ͽ� �ۼ�
		//��� ��Ʈ���� ������ ���ۻ�����(1024byte)�� ����
		//�޸� ���ۿ� �����͸� �������ν� ���� �ӵ��� ���
		//���۴� �����Ͱ� ���̱⸦ ��ٷȴٰ� �� ���� �Ǹ� �����͸� �Ѳ����� �ϵ� ��ũ�� �������ν� ��� Ƚ���� �ٿ���
		//���� ũ��� OS�� ���� �ٸ��� ���� 512�� ����
		BufferedInputStream in2 = new BufferedInputStream(in, 1024);//���ι��� ����Ʈ 1024

		//���� ������ ũ�⸦ �������� ���� �� 512�� ũ��� default ����
		//BufferedInputStream in2 = new BufferedInputStream(in); 

		//Data In
		while(true) {
			//FileInputStream�� FileOutputStream�� 1����Ʈ�� �о�鿩 1����Ʈ�� ����
			//return type�� ���� �̸�, ������ 1byte�� �����͸� ������ 4byte(����)�� �о�帲
			//�̴� �о�帮�� ���� ���� �� ������ return�ϱ� ����
			int readCount = in2.read(buffer);
			
			if(readCount < 0) { //�о�帰 ���� ������ ���� 
				//System.out.println(readCount);//-1�� ������ �����
				break;
			}
			
			for(int i = 0 ; i < buffer.length ; i++){
				System.out.print((char)buffer[i]); //buffer�� ũ�⸸ŭ ����� �ֱ⿡ ���ڰ� �ȳ���
			}
			//System.out.print(new String(buffer));//�����ִ� for���� ����
		}
		
		//�� �ݱ�
		in.close();	

		
		
        //�޼ҵ尡 �������� �ð� Ȯ��
        long endTime = System.currentTimeMillis();
        //�޼ҵ带 �����ϴµ� �ɸ� �ð� Ȯ��
        System.out.println("����ð� : " + (endTime-startTime));
	}
}