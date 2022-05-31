package kr.co.gpgp.config;

import kr.co.gpgp.GpgpApplication;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class JasptTest implements CommandLineRunner {


    public static void main(String [] args) {
        SpringApplication.run(GpgpApplication.class, args);
        System.out.println("=========== Server Start ===========");
    }

    @Override
    public void run(String... args) throws Exception {

        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("test"); //2번 설정의 암호화 키를 입력

        String enc = pbeEnc.encrypt("1234"); //암호화 할 내용
        System.out.println("enc = " + enc); //암호화 한 내용을 출력

        //테스트용 복호화
        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }
}
