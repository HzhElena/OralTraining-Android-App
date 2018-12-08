package DialogRelated;

public class Dialog {
	public Integer id;
	public String dialogtime;
	public Integer senderid;
	public Integer receiverid;
	public Integer status;
	public Dialog() {}
	public Dialog(int id,String dialogtime,int senderid,int receiverid,int status)
	{
		this.id = id;
		this.dialogtime = dialogtime;
		this.senderid = senderid;
		this.receiverid = receiverid;
		this.status = status;
	}
	public void setSender(int id)
	{
		this.senderid = id;
	}
	public void setReceiver(int id)
	{
		this.receiverid = id;
	}
	public void setStatuc(int status)
	{
		this.status = status;
	}
	public void setId(Integer id)
 	{
 		this.id = id;
 	}
}
