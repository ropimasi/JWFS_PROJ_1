package application.entity;


import java.util.ArrayList;
import java.util.List;
import application.dao.JdbcUserLevelDao;



public class UserLevelEntity {
	
	/* Constants containing standards values from Business-and-Technical-Rules: */
	public static final int NAME_MIN_LEN = 3;
	public static final int NAME_MAX_LEN = 16;
	private static final JdbcUserLevelDao dao = new JdbcUserLevelDao();
	private static List<String> namesList = new ArrayList<String>();
	
	/* Static-Constructor, for statics classes only, before Constructor. */
	static {
		setNamesList(dao.list());
	}
	
	
	
	/* Constructor of insrances. */
	public UserLevelEntity() {}
	
	
	
	public static List<String> getNamesList() { return namesList; }
	
	
	
	private static void setNamesList(List<String> nameList) { UserLevelEntity.namesList = nameList; }
	
}
