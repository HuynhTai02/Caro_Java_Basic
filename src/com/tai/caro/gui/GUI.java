package com.tai.caro.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.tai.caro.banco.BanCo;
import com.tai.caro.main.Main;

public class GUI extends JFrame {
	private static final int W_Frame = 1200;
	private static final int H_Frame = 800;

	public GUI() {
		initViews();
	}

	private void initViews() {
		setSize(W_Frame, H_Frame);
		setTitle("GAME CARO");
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setLayout(new CardLayout());
		add(new BanCo(Color.black, "Player 1", "Player 2"));
	}

}
