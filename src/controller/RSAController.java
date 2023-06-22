package controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import model.RSA;

/**
 * 
 * @author TRUONG CONG MANH - 2021601910
 *
 */
public class RSAController {
	
	public static final Scanner sc = new Scanner(System.in);
	
	// Object's properties
	private RSA rsa;
	
	// Contructor methods
	public RSAController() {
		super();
		this.rsa = new RSA();
	}

	public RSAController(RSA rsa) {
		super();
		this.rsa = rsa;
	}

	// Getter and Setter methods
	public RSA getRSA() {
		return rsa;
	}

	public void setRSA(RSA rsa) {
		this.rsa = rsa;
	}
	

	/**
	 * Sàng nguyên tố Eratosthenes <br/>
	 * ỨNG DỤNG kiểm tra cực kỳ nhanh một số có phải là số nguyên tố hay không (thông qua chỉ số)
	 * @param size - Độ lớn của danh sách
	 * @return danh sách vị trí các số nguyên tố
	 */
	public List<Long> eratosthenes(long size) {
		List<Long> listOfPrime = new ArrayList<Long>();
		for (long i = 0; i <= size; i++) {
			listOfPrime.add(i);
		}
		
		listOfPrime.set(0, 0L);
		listOfPrime.set(1, 0L);
		for (int i = 2; i * i <= size; i++) {
			if (listOfPrime.get(i) == 0)
				continue;
			
			for (int j = i; j <= size; j++) {
				if (i * j > size)
					break;
				listOfPrime.set(i * j, 0L);
			}
		}
		return listOfPrime;
	}
	
	/**
	 * Euclid - tìm ước chung lớn nhất GCD(a, b) <br/>
	 * ỨNG DỤNG kiểm tra điều kiện của e (khóa công khai): GCD(e, phiN) = 1
	 * @param a
	 * @param b
	 * @return ước chung lớn nhất của a và b
	 */
	public long euclideanGCD(long a, long b) {
		if (b == 0)
			return a;
		return euclideanGCD(b, a % b);
	}
	
	/**
	 * Euclid mở rộng - tìm nghịch đảo modulo: a^-1 mod n <br/>
	 * ỨNG DỤNG tìm d (khóa bí mật): d = e^-1 mod phiN
	 * @param a
	 * @param n
	 * @return số nghịch đảo của a trong modulo n
	 */
	public long extendedEuclideanInverse(long a, long n) {
		long r0 = n, r1 = a;
		long r2 = r0 % r1;
		long q = r0 / r1;
		long t0 = 0, t1 = 1;

		while (r2 != 0) {
			long tmp = t0 - q * t1;
			tmp = mod(tmp, n);
			r0 = r1;
			r1 = r2;
			r2 = r0 % r1;
			q = r0 / r1;
			t0 = t1;
			t1 = tmp;
		}
		
		
		if (r1 == 1)
			return t1;
		System.out.println("Khong tim duoc " + a + "^-1 mod " + n);
		return -1;
	}
	
	/**
	 * Bình phương và nhân - tìm: a^k mod n <br/>
	 * ỨNG DỤNG để mã hóa, giải mã RSA <br/>
	 * Encrypt: cipherText = plainText^e mod n <br/>
	 * Decrypt: plainText = cipherText^d mod n
	 * @param a - giá trị unicode của một ký tự
	 * @param k - số mũ
	 * @param n - giá trị (không gian) modulo
	 * @return kết quả
	 */
	public int squareAndMultiply(int a, long k, long n) {
		long result = 1;
		int base = a;
		long exponent = k;
		
		List<Long> binary = new ArrayList<Long>();
		while (exponent > 0) {
			binary.add(exponent % 2);
			exponent /= 2;
		}
		
		for (long i = binary.size() - 1; i >= 0; i--) {
			result *= result;
			result = mod(result, n);
			if (binary.get((int) i) == 1) {
				result *= base;
				result = mod(result, n);
			}
		}
        return (int) result;
	}
	
	/**
	 * Hàm tính a modulo b
	 * @param a
	 * @param b
	 * @return kết quả
	 */
	public long mod(long a, long b) {
		long result = a % b;
		if (result < 0) {
            result += b;
        }
        return result;
	}

	/**
	 * Mã hóa RSA
	 * @param plainText - bản rõ
	 * @return bản mã của RSA (dưới dạng chuỗi theo Base64)
	 */	
	public String encryptRSA(String plainText) {
		// Chuyển bản rõ thành bytes
		byte[] textToBytes = plainText.getBytes(StandardCharsets.UTF_8);
		
		// Mã hóa bytes sang chuỗi bằng Base64
		String base64Encoded = Base64.getEncoder().encodeToString(textToBytes);
		
		// Chuyển chuỗi được mã hóa bằng Base64 thành mảng các giá trị Unicode
		int[] unicodeArray = new int[base64Encoded.length()];
		for (int i = 0; i < base64Encoded.length(); i++) {
			unicodeArray[i] = base64Encoded.charAt(i);
		}
		
		// Mã hóa từng giá trị Unicode bằng RSA
		int[] encryptedArray = new int[unicodeArray.length];
		for (int i = 0; i < unicodeArray.length; i++) {
			encryptedArray[i] = squareAndMultiply(unicodeArray[i], rsa.getE(), rsa.getN());
		}
		
		// Chuyển các giá trị Unicode được mã hóa (đang có giá trị rất lớn) thành một chuỗi
		String encryptedText = "";
		for (int i = 0; i < encryptedArray.length; i++) {
			encryptedText = encryptedText + (char) encryptedArray[i];
		}
		
		// Chuyển chuỗi được mã hóa thành bytes
		byte[] encryptedData = encryptedText.getBytes(StandardCharsets.UTF_8);
		
		// Mã hóa các bytes đã được mã hóa bằng Base64
		return Base64.getEncoder().encodeToString(encryptedData);
	}

	/**
	 * Mã hóa RSA
	 * @param cipherText - bản mã (dạng chuỗi theo Base64)
	 * @return bản rõ
	 */
	public String decryptRSA(String cipherText) {
		// Giải mã bản mã bằng Base64
		byte[] decodedCipherText = Base64.getMimeDecoder().decode(cipherText);
		
		// Chuyển các byte đã giải mã thành một chuỗi
		String decodedText = new String(decodedCipherText, StandardCharsets.UTF_8);
		
		// Chuyển chuỗi đã giải mã bằng Base64 thành mảng Unicode
		int[] unicodeArray = new int[decodedText.length()];
		for (int i = 0; i < decodedText.length(); i++) {
			unicodeArray[i] = decodedText.charAt(i);
		}
		
		// Giải mã từng giá trị Unicode bằng RSA
		int [] decryptedArray = new int[unicodeArray.length];
		for (int i = 0; i < decryptedArray.length; i++) {
			decryptedArray[i] = squareAndMultiply(unicodeArray[i], rsa.getD(), rsa.getN());
		}
		
		// Chuyển các giá trị Unicode được giải mã (đang có giá trị rất lớn) thành một chuỗi
		String decryptedString = "";
		for (int i = 0; i < decryptedArray.length; i++) {
			decryptedString = decryptedString + (char) decryptedArray[i];
		}
		
		// Chuyển chuỗi được giải mã thành bytes
		byte[] decodedData = Base64.getMimeDecoder().decode(decryptedString);
		
		// Chuyển các byte đã giải mã thành một chuỗi bằng UTF_8
		String decryptedText = new String(decodedData, StandardCharsets.UTF_8);
		return decryptedText;
	}
	
	public boolean automaticKeyGeneration() {
		// Sàng số nguyên tố: 256 số đầu tiên được mảng "listOfPrime"
		List<Long> listOfPrime = eratosthenes(256);
		
		// Biến lưu giá trị số ngẫu nhiên
		int random;
		
		// Chọn ngẫu nhiên số nguyên tố (lớn) thứ nhất - P
		do {
			random = (int) (Math.random() * 1000) % (listOfPrime.size() - 100) + 100;
			rsa.setP(random);
		} while (listOfPrime.get(random) == 0);
		
		// Chọn ngẫu nhiên số nguyên tố (lớn) thứ hai - Q
		do {
			random = (int) (Math.random() * 1000) % (listOfPrime.size() - 100) + 100;
			rsa.setQ(random);
		} while (listOfPrime.get(random) == 0 || rsa.getP() == rsa.getQ());
		
		long p = rsa.getP();
		long q = rsa.getQ();
		
		// Tính số modulo của hệ thống - N
		rsa.setN(p, q);	
		
		// Tính giá trị hàm số Euler - Phi(N)
		rsa.setPhiN(p, q);	
		
		// Tìm số mũ khóa công khai - E
		do {
			random = (int) ((int) (Math.random() * rsa.getPhiN() * 10) % rsa.getPhiN() + 1);
			rsa.setE(random);
		} while (euclideanGCD(rsa.getE(), rsa.getPhiN()) != 1);
		
		// Tính số mũ khóa bí mật - D
		rsa.setD(extendedEuclideanInverse(rsa.getE(), rsa.getPhiN()));
		
		return true;
	}
	
	
	public boolean checkManualKeyGeneration(long p, long q, long e) {
		// Sàng số nguyên tố: 256 số đầu tiên được mảng "listOfPrime"
		List<Long> listOfPrime = eratosthenes(256);
		
		if (listOfPrime.get((int) p) == 0 || listOfPrime.get((int) q) == 0 || p == q)
			return false;
		
		rsa.setP(p);
		rsa.setQ(q);
		
		// Tính số modulo của hệ thống - N
		rsa.setN(p, q);	
		
		// Tính giá trị hàm số Euler - Phi(N)
		rsa.setPhiN(p, q);
		
		if (euclideanGCD(e, rsa.getPhiN()) == 0 || (1 >= e && e >= rsa.getPhiN()))
			return false;
		
		rsa.setE(e);
		
		// Tính số mũ khóa bí mật - D
		rsa.setD(extendedEuclideanInverse(rsa.getE(), rsa.getPhiN()));
		
		return true;
	}

}
