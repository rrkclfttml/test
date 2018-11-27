package notice;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import sqlmap.MyAppSqlConfig;

public class NoticeDAO {
private static SqlSessionFactory sqlMapper;
SqlSession session = sqlMapper.openSession();
	
	static{
		sqlMapper = MyAppSqlConfig.getSqlMapInstance();
	}
	
	public boolean create(NoticeDTO dto) {
		boolean flag=false;
		int cnt=sqlMapper.hashCode();
		if(cnt>0)flag=true;
				
		return flag;
	}
}
