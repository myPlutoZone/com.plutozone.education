/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF PLUTOZONE.COM.
 * PLUTOZONE.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2017 PLUTOZONE.COM ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 plutozone.com에 있으며,
 * plutozone.com이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * plutozone.com의 지적재산권 침해에 해당된다.
 * Copyright (C) 2017 plutozone.com All Rights Reserved.
 *
 *
 * Program		: com.plutozone.util
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: MessageClient.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20170614133300][pluto@plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.util.messenger;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @version 1.0.0
 * @author pluto@plutozone.com
 * 
 * @since 2017-06-14
 * <p>DESCRIPTION: 메신저 클라이언트 클래스</p>
 * <p>IMPORTANT:</p>
 */
public class MessageClient extends Frame implements Runnable, ActionListener, WindowListener {
	
	/** Serial version UID */
	private static final long serialVersionUID = 20170614133900L;
	
	/** Listener */
	protected Thread listener = null;
	
	/** Socket */
	protected Socket socket;
	public int port;
	
	/** Input/Output Object Stream */
	protected ObjectInputStream objectInputStream;
	protected ObjectOutputStream objectOutputStream;
	
	/** UI */
	protected Button btnConnect, btnDisconnect;
	protected TextArea fieldContent;
	protected TextField fieldName;
	protected TextField fieldMessage;
	
	/** Connection State */
	protected boolean isConnected = false;
	
	public static void main(String[] args) {
		new MessageClient(8888);
	}
	
	/**
	 * @param port [포트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public MessageClient(int port) {
		
		super("Messenger GUI(Java AWT) Client Version 1.0.0");
		
		this.port = port;
		
		Panel conPanel = new Panel();
		Label lbl = new Label("Name: ");
		fieldName = new TextField("Your Name");
		fieldName.selectAll();
		
		btnConnect = new Button("Connect");
		btnConnect.addActionListener(this);
		
		btnDisconnect = new Button("Disconnect");
		btnDisconnect.addActionListener(this);
		
		conPanel.add(lbl);
		conPanel.add(fieldName);
		conPanel.add(btnConnect);
		conPanel.add(btnDisconnect);
		
		fieldContent = new TextArea();
		fieldContent.setEditable(false);
		
		fieldMessage = new TextField();
		fieldMessage.addActionListener(this);
		
		setLayout(new BorderLayout());
		add("North", conPanel);
		add("Center", fieldContent);
		add("South", fieldMessage);
		
		addWindowListener(this);
		setSize(500, 400);
		setVisible(true);
		
		setChatMode(false);
	}
	
	/**
	 * @param mode [모드]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void setChatMode(boolean mode) {
		if (mode == true) {
			btnConnect.setEnabled(false);
			btnDisconnect.setEnabled(true);
			fieldName.setEditable(false);
			fieldMessage.setEditable(true);
		}
		else {
			btnConnect.setEnabled(true);
			btnDisconnect.setEnabled(false);
			fieldName.setEditable(true);
			fieldMessage.setEditable(false);
		}
	}
	
	/**
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public synchronized void stop() {
		if (listener != null) {
			listener = null;
			try {
				socket.close();
				objectOutputStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public void run() {
		MessageObject messageObject = null;
		
		while (isConnected) {
			try {
				messageObject = (MessageObject) objectInputStream.readObject();
				fieldContent.append("[" + messageObject.getName() + "]" + messageObject.getMessage() + "\r\n");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param actionEvent [액션 이벤트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent actionEvent) {
		
		if (actionEvent.getSource() == btnConnect) {
			try {
				socket = new Socket("localhost", port);
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			}
			catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			String message = "************** " + fieldName.getText() + " has arrived! **************";
			MessageObject content = new MessageObject(1, fieldName.getText(), message);
			broadcast(content);
			
			fieldMessage.setText("");
			isConnected = true;
			listener = new Thread(this);
			listener.start();
			setChatMode(true);
		}
		else if (actionEvent.getSource() == btnDisconnect) {
			String message = "************** " + fieldName.getText() + " has exit! **************";
			MessageObject co = new MessageObject(-1, fieldName.getText(), message);
			try {
				objectOutputStream.writeObject(co);
				objectOutputStream.flush();
			}
			catch (IOException e) {
				try {
					objectOutputStream.close();
					objectInputStream.close();
					socket.close();
				}
				catch (Exception ioe) {}
			}
			isConnected = false;
			listener.stop();
			setChatMode(false);
		}
		else if (actionEvent.getSource() == fieldMessage) {
			if (fieldMessage.getText().equals("")) return;
			
			MessageObject content = new MessageObject(1, fieldName.getText(), fieldMessage.getText());
			broadcast(content);
			fieldMessage.setText("");
		}
	}
	
	/**
	 * @param windowEvent [윈도우 이벤트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@SuppressWarnings("deprecation")
	public void windowClosing(WindowEvent windowEvent) {
		
		String message = "************** " + fieldName.getText() + " has exit! **************";
		MessageObject co = new MessageObject(-1, fieldName.getText(), message);
		try {
			objectOutputStream.writeObject(co);
			objectOutputStream.flush();
		}
		catch (IOException e) {
			try {
				objectOutputStream.close();
				objectInputStream.close();
				socket.close();
			}
			catch (Exception ioe) {	}
		}
		
		if (listener != null) listener.stop();
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	/**
	 * @param messageObject [메시지 객체]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void broadcast(MessageObject messageObject) {
		try {
			objectOutputStream.writeObject(messageObject);
			objectOutputStream.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void windowActivated(WindowEvent evt) {}
	public void windowClosed(WindowEvent evt) {}
	public void windowDeactivated(WindowEvent evt) {}
	public void windowDeiconified(WindowEvent evt) {}
	public void windowIconified(WindowEvent evt) {}
	public void windowOpened(WindowEvent evt) {}
	
}