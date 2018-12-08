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
/**
 * Servlet implementation class GetInfo
 */
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer userid = Integer.parseInt(request.getParameter("userid"));
		response.setContentType("text/html£»charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		Integer getSuccess = -1;
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        JSONObject jsonObj = new JSONObject();
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.185.19.191:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");

        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from User where userid=?");
        	ps.setObject(1, userid);
        	rs = ps.executeQuery();
        	if(rs.next()){
        	jsonObj.put("username",rs.getString("username"));
        	jsonObj.put("email",rs.getString("mail"));
        	jsonObj.put("exerciseamount",rs.getInt("exerciseamount"));
        	jsonObj.put("averagescore",rs.getInt("averagescore"));
        	jsonObj.put("friendamount",rs.getInt("friendamount"));
        	jsonObj.put("traindegree",rs.getInt("traindegree"));
        	jsonObj.put("wordamount",rs.getInt("wordamount"));
        	jsonObj.put("wordpronounce",rs.getInt("wordpronounce"));
        	jsonObj.put("achievements",rs.getString("achievements"));
        	getSuccess = 1;
        	}
        	else
        	{
        		getSuccess = -1;
        	}
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
        out.println(jsonObj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
