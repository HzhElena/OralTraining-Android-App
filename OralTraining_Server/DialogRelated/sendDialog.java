package DialogRelated;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class sendDialog
 */
public class sendDialog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendDialog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		Dialog dialog = new Dialog();
		String repositoryPath = request.getSession().getServletContext().getRealPath("/upload/temp");
		factory.setRepository(new File(repositoryPath));
		ServletInputStream  sis=request.getInputStream();

		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload uploader = new ServletFileUpload(factory);
		
		Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
		
		Integer userid = null;
		String recordtime = "";
		Integer uploadSuccess = -1;
		Integer score = null;
		try {
			ArrayList<FileItem> list = (ArrayList<FileItem>) uploader.parseRequest(request);
			System.out.println(list.size());
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    System.out.println(name + ":" + value);
                    if(name == "sendtime")
                    {
                    	recordtime = value;
                    }
                    else if(name == "senderid")
                    {
                    	dialog.senderid =Integer.parseInt(value);
                    }
                    else if(name == "receiverid")
                    {
                    	dialog.receiverid =Integer.parseInt(value);
                    }
                    else if(name =="record")
                    {
                    	BASE64Decoder decoder = new BASE64Decoder();
                    	String path = "D://res/dialogs/" + dialog.senderid.toString() + "_"+ recordtime + ".mp3";
                    	PrintWriter out = response.getWriter();
                    	byte[] bytes = decoder.decodeBuffer(value);
                    	OutputStream out1 = new FileOutputStream(path);
                    	out1.write(bytes);
            			out1.flush();
            			out1.close();
            			uploadSuccess = 1;
            			System.out.println("上传成功");
            			
                    }
                }
			}

			score = 100;
			Class.forName("com.mysql.jdbc.Driver");

        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.182.61.160:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");
        	ps = (PreparedStatement) ct.prepareStatement(
    				"INSERT INTO Dialog(speechid,speechscore,ownerid,speechpath,speechtime) VALUES(?,?,?,?)"
    				);
        	ps.setInt(1, 0);
    		ps.setInt(2, score);
    		ps.setInt(3, userid);
    		ps.setString(4, recordtime);
    		ps.executeUpdate();
			}
		catch(Exception e)
		{
            e.printStackTrace();
        }
        
		PrintWriter out = response.getWriter();
		out.append("success="+uploadSuccess.toString()+"\n"+"score="+score.toString());
		out.flush();
        out.close();
	}

}
