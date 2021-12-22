package com.wallet.servlet;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallet.pojo.User;
import com.wallet.util.WalletViews;

public class TransAnalyze extends HttpServlet {
    public TransAnalyze() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
        try{
            String periodicity = request.getParameter("periodicity");
            if (periodicity != null) {
                Runtime rt = Runtime.getRuntime();
                String command = "java -cp /usr/local/tomcat/webapps/wallet/WEB-INF/lib/mysql-connector-java-8.0.27.jar /usr/local/Analyze.java " + user.getId() + " " + periodicity;
                Process p = rt.exec(new String[]{"sh", "-c", command});
                p.waitFor();
                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String line=null;
                String res = "";
                while ( (line = br.readLine()) != null) {
                    System.out.println("OUTPUT" + ">" + line);
                    res = res + line + "\n";   
                }
                isr = new InputStreamReader(p.getErrorStream());
                br = new BufferedReader(isr);
                while ( (line = br.readLine()) != null) {
                    System.out.println("Error: " + ">" + line);   
                }
                List<List<String>> records = new ArrayList<List<String>>();
                String misctext = "";
                if(res != "") {
                    String[] lines = res.split("\n");
                    for(int i = 0; i < lines.length; i++ ) {
                        String row = lines[i];
                        String[] values = row.split(",");
                        if (values.length == 2) {
                            records.add(Arrays.asList(values));
                        }
                        else {
                            misctext += row;
                        }
                    }
                }

                request.setAttribute("analysis", records);
                request.setAttribute("misctext", misctext);
                request.setAttribute("periodicity", periodicity);
		        request.getRequestDispatcher(WalletViews.TransAnalysisView).forward(request, response);
            }
            else {
                request.getRequestDispatcher(WalletViews.TransAnalysisView).forward(request, response);
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
