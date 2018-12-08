package UserRelated;

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

import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class ChangeInfo
 */
public class ChangeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		UserInfo user = new UserInfo();
		Integer userid = Integer.parseInt(request.getParameter("userid"));
		String target = request.getParameter("target");
		Integer changefield = Integer.parseInt(request.getParameter("changefield"));
		String change = "";
		switch(changefield)
		{
		case 1:
			change = "username";
			break;
		case 2:
			change = "password";
			break;
		case 3:
			change = "mail";
			break;
		case 4:
			change = "exerciseamount";
			break;
		case 5:
			change = "averagescore";
			break;
		case 6:
			change = "friendamount";
			break;
		case 7:
			change = "traindegree";
			break;
		case 8:
			change = "wordamount";
			break;
		case 9:
			change = "wordpronounce";
		}

		response.setContentType("text/html£»charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//Modify database
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        Integer changeSuccess = -1;//-1 indicates name repeated, 1 means success
        try{

        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://10.185.19.191:3306/free_talk_schema?verifyServerCertificate=false&useSSL=true&useOldAliasMetadataBehavior=true","admin","1234");
        	//Execute select
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from User where userid=?"
        			);
        	ps.setObject(1, userid);
        	rs = ps.executeQuery();

        	if(rs.next()){
        		if(changefield.equals(1))
        		{
        			PreparedStatement ps3 = (PreparedStatement) ct.prepareStatement(
                			"select * from User where username=?");
        			ps3.setString(1,target);
        			ResultSet rs3 = ps3.executeQuery();
        			if(rs3.next()){
        				changeSuccess = -2;  //This username has been used
        			}
        			else
        			{
        				PreparedStatement ps2 = (PreparedStatement) ct.prepareStatement(
                    			"UPDATE User SET username=?" +" WHERE userid=?");
        				ps2.setString(1, target);
        				ps2.setInt(2, userid);
        				System.out.println(ps2.executeUpdate());
        				changeSuccess = 1;
        			}
        		}
        		else{
        		PreparedStatement ps2 = (PreparedStatement) ct.prepareStatement(
            			"UPDATE User SET "+change+"=?" +" WHERE userid=?");
        		ps2.setInt(2, userid);
        		switch(changefield)
        		{
        		case 2: case 3:
        			ps2.setString(1, target);
        			break;
        		default:
        			Integer t = Integer.parseInt(target);
        			ps2.setInt(1, t);
        			break;
        		}
        		rs2 = ps.executeQuery();
        		System.out.println(ps2.executeUpdate());
        		changeSuccess = 1;
        		}
        	}
        	else
        	{
        		changeSuccess = -1; //failed
        	}
        }
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        finally
        {
            //Close resources
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
        	//return value
        JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject().put("changeState", changeSuccess);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
