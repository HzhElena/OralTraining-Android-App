package UserRelated;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class UploadImage
 */
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String base64 = request.getParameter("image");
		String username = request.getParameter("user");
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			byte[] bytes = decoder.decodeBuffer(base64);
			for(int i=0;i<bytes.length;++i)
			{
				if(bytes[i]<0) bytes[i]+=256;
			}
			Class.forName("com.mysql.jdbc.Driver");
			Integer userid;
			Connection ct= null;
	        PreparedStatement ps =null;
	        ResultSet rs = null;
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.182.96.163:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true","admin","1234");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select username from User where username=?");
        	ps.setObject(1, username);
        	rs = ps.executeQuery();
        	userid = (Integer)rs.getInt("userid");
        	String path = "D://res/images/"+userid+"head.jpg";
			OutputStream out = new FileOutputStream(path);
			out.write(bytes);
			out.flush();
			out.close();
		}catch(Exception e)
		{
			System.out.println("error!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

}
