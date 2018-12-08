package UserRelated;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		UserInfo user = new UserInfo();
		//String username = request.getParameter("user");
		String password = request.getParameter("pass");
		String username = new String(request.getParameter("user").getBytes("iso-8859-1"),"utf-8");
		//String password = new String(request.getParameter("pass").getBytes("iso-8859-1"),"utf-8");
		user.setPassword(password);
		user.setUsername(username);
		System.out.println(username + "--" + password);
		//结果文件
		response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
		//SdkHttpResult result = null;
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{

        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.185.19.191:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");
        	//执行查询
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from User where username=? and password=?");
        	ps.setObject(1, username);
        	ps.setObject(2, password);
        	System.out.println(username);
        	System.out.println(password);
        	rs = ps.executeQuery();
        	Integer userid = new Integer(-1);
        	String email = null;
        	Integer exerciseAmount = null;
        	Integer averageScore = null;
        	Integer friendAmount = null;
        	Integer trainDegree = null;
        	Integer wordAmount = null;
        	Integer wordPronounce = null;
        	String achievements = null;
        	Integer regSuccess = -1;//-1 indicates name repeated, 1 means success
        	System.out.println(regSuccess);
        	JSONObject jsonObj = new JSONObject();
        	if(rs.next()){
        		System.out.println("Success!");
        		userid = (Integer)rs.getInt("userid");
        		email = rs.getString("mail");
        		exerciseAmount = (Integer)rs.getInt("exerciseamount");
        		averageScore = (Integer)rs.getInt("averagescore");
        		friendAmount = (Integer)rs.getInt("friendamount");
        		trainDegree = (Integer)rs.getInt("traindegree");
        		wordAmount = (Integer)rs.getInt("wordamount");
        		wordPronounce = (Integer)rs.getInt("wordpronounce");
        		achievements = rs.getString("achievements");
        		System.out.println(userid);
        		System.out.println(email);
        		regSuccess = 1;
        		//转头像
            	String path = "D://res/images/" + userid + "head.jpg";
            	File file = new File(path);
            	if(file.exists()){ //判断头像是否存在
            	byte[] data = null;
            	InputStream in = null;
            	try{
            		//对于没有设置过头像的自动为其添加默认头像 
            		in = new FileInputStream(path);
            		data = new byte[in.available()];
            		in.read(data);
            		in.close();
            	}
            	catch(IOException e)
            	{
            		e.printStackTrace();
            	}
            	//图片转换成base64编码字符串
            	BASE64Encoder encoder = new BASE64Encoder();
            	jsonObj.put("image", encoder.encode(data));
            	}
            	else
            	{
            		jsonObj.put("image", "");
            	}
            	jsonObj.put("userid",userid);
            	jsonObj.put("email",email);
            	jsonObj.put("exerciseamount", exerciseAmount);
            	jsonObj.put("averagescore", averageScore);
            	jsonObj.put("friendamount", friendAmount);
            	jsonObj.put("traindegree", trainDegree);
            	jsonObj.put("wordamount", wordAmount);
            	jsonObj.put("wordpronounce", wordPronounce);
            	jsonObj.put("achievements", achievements);
        		//session.setAttribute("userId", userId);
        	}
        	else{
        		System.out.println("Failed");
        		PreparedStatement ps2 = (PreparedStatement) ct.prepareStatement(
            			"select * from User where username=?");
        		ps2.setObject(1, username);
        		ResultSet rs2 = ps2.executeQuery();
        		if(rs2.next()){regSuccess = -2;} //用户名和密码不匹配
        		else
        		{regSuccess = -1;} //没有用户名
        	}
        	jsonObj.put("LoginState", regSuccess);
        	out.println(jsonObj.toString());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
            //关闭资源
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
