package model;

/**
 * 
 * @author TRUONG CONG MANH - 2021601910
 *
 */
public class RSA {
	
	// RSA's properties
	private long p;	// Số nguyên tố (lớn) thứ nhất
	private long q;	// Số nguyên tố (lớn) thứ hai
	private long n;	// Số modulo của hệ thống
	private long phiN;	// Giá trị hàm số Euler
	private long e;	// Số mũ khóa công khai
	private long d;	// Số mũ khóa bí mật
	
	// Constructor methods
	public RSA() {
		super();
	}
	
	public RSA(long p, long q, int e, int d) {
		super();
		this.p = p;
		this.q = q;
		this.setN(p, q);
		this.setPhiN(p, q);
		this.e = e;
		this.d = d;
	}

	public RSA(long p, long q, long n, long phiN, int e, int d) {
		super();
		this.p = p;
		this.q = q;
		this.n = n;
		this.phiN = phiN;
		this.e = e;
		this.d = d;
	}

	// Getter and Setter methods
	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public long getQ() {
		return q;
	}

	public void setQ(long q) {
		this.q = q;
	}

	public long getN() {
		return n;
	}

	public void setN(long n) {
		this.n = n;
	}
	
	// Overload
	public void setN(long p, long q) {
		this.n = p * q;
	}

	public long getPhiN() {
		return phiN;
	}

	public void setPhiN(long phiN) {
		this.phiN = phiN;
	}
	
	// Overload
	public void setPhiN(long p, long q) {
		this.phiN = (p - 1) * (q - 1);
	}

	public long getE() {
		return e;
	}

	public void setE(long e2) {
		this.e = e2;
	}

	public long getD() {
		return d;
	}

	public void setD(long l) {
		this.d = l;
	}
	
}
