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
 * File			: MessageServer.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20170614133200][pluto#plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.util.messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 * 
 * @since 2017-06-14
 * <p>DESCRIPTION: 메신저 서버 클래스</p>
 * <p>IMPORTANT:</p>
 */
public class MessageServer {
	
	/** Server */
	ServerSocket server		= null;
	
	/** Client */
	public Socket client	= null;
	
	public static void main(String args[]) {
		new MessageServer(8888);
	}
	
	/**
	 * @param port [포트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public MessageServer(int port) {
		try {
			server = new ServerSocket(port);
			
			while (true) {
				client = server.accept();
				System.out.println("Client accpeted..." + client.getInetAddress().getHostAddress());
				
				ClientHandler handler = new ClientHandler(client);
				handler.start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}