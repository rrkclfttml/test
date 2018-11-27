package sqlmap;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyAppSqlConfig {
	private static final SqlSessionFactory sqlMapper;
	static {
		try {
				String resource = "sqlmap/SqlMapConfig.xml";
		        Reader reader = Resources.getResourceAsReader(resource);
		        sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException ("Error initializing MyAppSqlConfig class. Cause:" + e);
		}
	}
	public static SqlSessionFactory getSqlMapInstance () {
		return sqlMapper;
	}
}