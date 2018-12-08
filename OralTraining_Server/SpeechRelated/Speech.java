package SpeechRelated;

public class Speech {
	private String speechtime="";
	private String speechpath="";
	private int speechscore;
	private int ownerid;
	public void set_owner(int id)
	{
		ownerid = id;
	}
	public void set_path(String path)
	{
		speechpath = path;
	}
	public void set_score(int score)
	{
		speechscore = score;
	}
	public void set_time(String time)
	{
		speechtime = time;
	}
}
