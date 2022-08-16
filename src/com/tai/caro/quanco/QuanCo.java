package com.tai.caro.quanco;

public class QuanCo {
	public static final char O = 'O';
	public static final char X = 'X';

	private int x, y;
	private char loaiQuanCo;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public QuanCo(int x, int y, char loaiQuanCo) {
		this.x = x;
		this.y = y;
		this.loaiQuanCo = loaiQuanCo;
	}

	public char getLoaiQuanCo() {
		return loaiQuanCo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuanCo) {
			QuanCo quanCo2 = (QuanCo) obj;
			return x == quanCo2.x && y == quanCo2.y;
		}
		return super.equals(obj);
	}
}
