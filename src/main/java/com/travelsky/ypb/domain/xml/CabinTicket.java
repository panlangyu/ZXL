package com.travelsky.ypb.domain.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Tilte:用于所有舱位对应票量</p>
 * <p>Description:JSON转换对象</p>
 * @author huc
 */
public class CabinTicket {
	private static String A;
	private static  String B;
	private  static String C;
	private static  String D;
	private static  String E;
	private static  String F;
	private static  String G;
	private  static String H;
	private static  String I;
	private static  String J;
	private static  String K;
	private static  String L;
	private static  String M;
	private  static String N;
	private  static String O;
	private  static String P;
	private static  String Q;
	private static  String R;
	private  static String S;
	private  static String T;
	private static  String U;
	private static  String V;
	private static  String W;
	private  static String X;
	private  static String Y;
	private static  String Z;
	
	static String getCabin(String XCabin) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("D", D);
		map.put("E", E);
		map.put("F", F);
		map.put("G", G);
		map.put("H", H);
		map.put("I", I);
		map.put("J", J);
		map.put("K", K);
		map.put("M", M);
		map.put("L", L);
		map.put("N", N);
		map.put("O", O);
		map.put("P", P);
		map.put("Q", Q);
		map.put("R", R);
		map.put("S", S);
		map.put("T", T);
		map.put("U", U);
		map.put("V", V);
		map.put("W", W);
		map.put("X", X);
		map.put("Y", Y);
		map.put("Z", Z);
		return map.get(XCabin);
	}
	
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	public String getE() {
		return E;
	}
	public void setE(String e) {
		E = e;
	}
	public String getF() {
		return F;
	}
	public void setF(String f) {
		F = f;
	}
	public String getG() {
		return G;
	}
	public void setG(String g) {
		G = g;
	}
	public String getH() {
		return H;
	}
	public void setH(String h) {
		H = h;
	}
	public String getI() {
		return I;
	}
	public void setI(String i) {
		I = i;
	}
	public String getJ() {
		return J;
	}
	public void setJ(String j) {
		J = j;
	}
	public String getK() {
		return K;
	}
	public void setK(String k) {
		K = k;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getM() {
		return M;
	}
	public void setM(String m) {
		M = m;
	}
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	public String getO() {
		return O;
	}
	public void setO(String o) {
		O = o;
	}
	public String getP() {
		return P;
	}
	public void setP(String p) {
		P = p;
	}
	public String getQ() {
		return Q;
	}
	public void setQ(String q) {
		Q = q;
	}
	public String getR() {
		return R;
	}
	public void setR(String r) {
		R = r;
	}
	public String getS() {
		return S;
	}
	public void setS(String s) {
		S = s;
	}
	public String getT() {
		return T;
	}
	public void setT(String t) {
		T = t;
	}
	public String getU() {
		return U;
	}
	public void setU(String u) {
		U = u;
	}
	public String getV() {
		return V;
	}
	public void setV(String v) {
		V = v;
	}
	public String getW() {
		return W;
	}
	public void setW(String w) {
		W = w;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	public String getZ() {
		return Z;
	}
	public void setZ(String z) {
		Z = z;
	}
	@Override
	public String toString() {
		return String
				.format("CabinTicket [A=%s, B=%s, C=%s, D=%s, E=%s, F=%s, G=%s, H=%s, I=%s, J=%s, K=%s, L=%s, M=%s, N=%s, O=%s, P=%s, Q=%s, R=%s, S=%s, T=%s, U=%s, V=%s, W=%s, X=%s, Y=%s, Z=%s]",
						A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R,S, T, U, V, W, X, Y, Z);
	}
	
	public Map<String, String> toMap(){
		Map<String, String> map  =  new HashMap<String, String>();
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("D", D);
		map.put("E", E);
		map.put("F", F);
		map.put("G", G);
		map.put("H", H);
		map.put("I", I);
		map.put("J", J);
		map.put("K", K);
		map.put("L", L);
		map.put("M", M);
		map.put("N", N);
		map.put("O", O);
		map.put("P", P);
		map.put("Q", Q);
		map.put("R", R);
		map.put("S", S);
		map.put("T", T);
		map.put("U", U);
		map.put("V", V);
		map.put("W", W);
		map.put("X", X);
		map.put("Y", Y);
		map.put("Z", Z);
		return  map;
	}
	
}
