package UserRelated;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		UserInfo user = new UserInfo();
		//String username = request.getParameter("username");
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
    	System.out.println(username);
    	System.out.println(password);
    	System.out.println(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setUsername(username);
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        Integer userid = null;
        JSONObject jsonObj = new JSONObject();
        int total = 0;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.185.19.191:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from User where username=?"
        			);
        	ps.setObject(1, username);
        	rs = ps.executeQuery();
        	Integer regSuccess = -1;//-1 indicates name repeated, 1 means success
        	if(rs.next()){
        		regSuccess = -1;
        	}
        	else{
        		regSuccess = 1;
        	}
        	if(regSuccess>0){
        		if(ps!=null) ps.close();
        		ps = (PreparedStatement) ct.prepareStatement(
        				"INSERT INTO User(userid,username,password,mail) VALUES(?,?,?,?)"
        				);
        		ps.setInt(1, 0);
        		ps.setString(2, username);
        		ps.setString(3, password);
        		ps.setString(4, email);
        		System.out.println(ps.executeUpdate());
        		ps.close();
        		PreparedStatement ps2 = (PreparedStatement) ct.prepareStatement(
            			"select * from User where username=? and password=?");
                ResultSet rs2 = null;
                ps2.setObject(1, username);
            	ps2.setObject(2, password);
            	rs2 = ps2.executeQuery();
            	while(rs2.next()){
            	userid = rs2.getInt("userid");
            	jsonObj.put("userid", userid);
            		}
        		System.out.println("×¢²á³É¹¦");
        		}
        	jsonObj.put("registerState", regSuccess);
        	out.println(jsonObj.toString());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
            if(rs!=null)
            {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rs=null;
            }
            if(ps!=null)
            {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps=null;
            }
            if(ct!=null)
            {
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct=null;
            }
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
