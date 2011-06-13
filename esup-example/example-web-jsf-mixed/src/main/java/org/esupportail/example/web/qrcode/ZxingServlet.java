/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.qrcode;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class ZxingServlet extends HttpServlet {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -425341850069548253L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String host = request.getLocalName();
		int port = request.getLocalPort();
		String imageFormat = request.getParameter("f");
		String pw = request.getParameter("w");
		String ph = request.getParameter("h");
		String text = request.getParameter("t");
		if (text == null) {
			text = "**ERROR No text passed to encode";
		} else {
			text = "http://" + host + ":" + port + text;
		}
		int width;
		int height;
		if (pw == null) {
			width = 200;
		} else {
			width = Integer.parseInt(pw);
		}
		if (ph == null) {
			height = 200;
		} else {
			height = Integer.parseInt(ph);
		}
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = null;
		OutputStream out = response.getOutputStream();
		try {
			matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToStream(matrix, imageFormat, out);
			response.setContentType("image/" + imageFormat);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
