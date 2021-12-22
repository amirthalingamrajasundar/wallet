package com.wallet.servlet;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallet.util.WalletViews;

public class LoadImage extends HttpServlet {
    public LoadImage() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String fileName = request.getParameter("filename");
            if (fileName != null) {
                // FileInputStream fin = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\wallet\\images\\" + fileName);
                FileInputStream fin = new FileInputStream( System.getenv("CATALINA_HOME") + "/webapps/wallet/images/" + fileName);
                if(fileName.contains("png") || fileName.contains("jpg")){
                    response.setContentType("image/jpeg");
                }
                else {
                    response.setContentType("text/html");
                }
                ServletOutputStream out;
                out = response.getOutputStream();
                BufferedInputStream bin = new BufferedInputStream(fin);
                BufferedOutputStream bout = new BufferedOutputStream(out);
                int ch =0;
                while((ch=bin.read())!=-1){
                    bout.write(ch);
                }    
                bin.close();
                fin.close();
                bout.close();
                out.close();
            }
            else {
                request.setAttribute("error", "File name should not be empty");
                request.getRequestDispatcher(WalletViews.ErrorPage).forward(request, response);
            }
            return;
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stacktracestr = sw.toString();
            request.setAttribute("error", stacktracestr);
            request.getRequestDispatcher(WalletViews.ErrorPage).forward(request, response);
        }
	}
}
