package SpeechRelated;

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
import Scoring.ScoringControl;

/**
 * Servlet implementation class UploadSpeech
 */
public class UploadSpeech extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadSpeech() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("…œ¥´”Ô“Ùget");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		DiskFileItemFactory factory = new DiskFileItemFactory();
		//String repositoryPath = request.getSession().getServletContext().getRealPath("upload/temp");
		String repositoryPath = "D://res/records/";
		File uploadDir = new File(repositoryPath);
        if(!uploadDir.exists())
        {
        	uploadDir.mkdir();
        }
		factory.setRepository(new File(repositoryPath));
		ServletInputStream  sis=request.getInputStream();
//		FileOutputStream fos=new FileOutputStream(file);

		factory.setSizeThreshold(1024 * 1024);
		
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("UTF-8");
		uploader.setSizeMax(MAX_REQUEST_SIZE);
		uploader.setFileSizeMax(MAX_FILE_SIZE);
		
		Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
		

		Integer userid = null;
		String recordtime = "";
		Integer uploadSuccess = -1;
		String recordid = "";
		Integer score = 0;
		String filepath="";
		String path = "";
		try {
			ArrayList<FileItem> list = (ArrayList<FileItem>) uploader.parseRequest(request);
			System.out.println(list.size());
			for (FileItem fileItem : list) {
				if (!fileItem.isFormField())
				{
					String fileName = new File(fileItem.getName()).getName();
					filepath = repositoryPath + fileName;
					
					File storeFile = new File(filepath);
					System.out.println(filepath);
					fileItem.write(storeFile);
					System.out.println("File");
					uploadSuccess = 1;
				}
				else {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    if(name.equals("recordtime"))
                    {
                    	recordtime = value;
                    }
                    else if(name.equals("userid"))
                    {
                    	
                    	userid = Integer.parseInt(value);
                    }
                   else if (name.equals("recordid"))
                    {
                    	recordid = value;
                    }
                }
			}
			System.out.println("recordid = ");
			System.out.println(recordid);
			String p = filepath.substring(0, filepath.length()-4);
			score = ScoringControl.returnFinalResult(recordid,filepath);
			Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.185.19.191:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");
        	ps = (PreparedStatement) ct.prepareStatement(
    				"INSERT INTO Speech(speechid,speechscore,ownerid,speechpath,speechtime) VALUES(?,?,?,?,?)"
    				);
        	ps.setInt(1, 0);
    		ps.setInt(2, score);
    		ps.setInt(3, userid);
    		ps.setString(4, filepath);
    		ps.setString(5, recordtime);
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
