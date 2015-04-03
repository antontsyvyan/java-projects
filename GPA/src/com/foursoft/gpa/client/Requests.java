package com.foursoft.gpa.client;

import javax.swing.*;
import java.awt.event.*;
import javafx.embed.swing.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.web.*;
import javafx.scene.effect.*;

public class Requests extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HTMLEditor edtr;

	public Requests() {
		this.setSize(600, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		JButton b1 = new JButton("Read");
		b1.setSize(150, 22);
		b1.setLocation(10, 10);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, edtr.getHtmlText());
			}
		});
		getContentPane().add(b1);
		final JFXPanel jfx = new JFXPanel();
		jfx.setSize(560, 300);
		jfx.setLocation(10, 40);
		getContentPane().add(jfx);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Group root = new Group();
				Scene scene = new Scene(root, 400, 300);
				jfx.setScene(scene);
				javafx.scene.shape.Rectangle rctngl = new javafx.scene.shape.Rectangle();
				rctngl.setTranslateX(20);
				rctngl.setTranslateY(30);
				rctngl.setWidth(500);
				rctngl.setHeight(250);
				rctngl.setEffect(new Shadow());
				root.getChildren().add(rctngl);
				edtr = new HTMLEditor();
				edtr.setHtmlText("Blablabla");
				edtr.setTranslateX(20);
				edtr.setTranslateY(30);
				edtr.setPrefWidth(500);
				edtr.setPrefHeight(250);
				root.getChildren().add(edtr);
			}
		});
	}

	public static void main(String[] args) {
		new Requests();
	}
}
