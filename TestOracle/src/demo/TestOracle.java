package demo;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

public class TestOracle {
	
	
	//测试存储过程
	@Test
	public void testProcedure() throws SQLException{
		String sql="{call queryInfo(?,?,?,?)}";
		Connection con=JdbcUtils.getConnection();
		CallableStatement call=con.prepareCall(sql);
		
		//对于in输入参数,赋值
		call.setInt(1, 7839);
		
		//对于out参数
		call.registerOutParameter(2, OracleTypes.VARCHAR);
		call.registerOutParameter(3, OracleTypes.NUMBER);
		call.registerOutParameter(4, OracleTypes.VARCHAR);
		//执行调用
		call.execute();
		
		//输出
		String name=call.getString(2);
		double sal=call.getDouble(3);
		String job=call.getString(4);
		
		System.out.println(name);
		System.out.println(sal);
		System.out.println(job);
	}
	
	//测试存储函数
	@Test
	public void testFunction() throws SQLException{
		String sql="{?=call queryIncome(?)}";
		Connection con=JdbcUtils.getConnection();
		CallableStatement call=con.prepareCall(sql);
		
		call.registerOutParameter(1, OracleTypes.NUMBER);
		call.setInt(2, 7839);
		
		call.execute();
		
		double num = call.getDouble(1);
		System.out.println(num);
		
		
		
	}
	
	//测试包体
	@Test
	public void testPackage() throws SQLException{
		
		String sql="{call mypackage.queryEmpList(?,?)}";
		//获取连接
		Connection con=JdbcUtils.getConnection();
		//获取CallableStatement
		CallableStatement call=con.prepareCall(sql);
		
		//设置参数 out参数
		call.registerOutParameter(2,OracleTypes.CURSOR);
		//设置参数 in参数
		call.setInt(1, 10);
		
		//执行
		call.execute();
		
		//取出结果
		ResultSet rs = ((OracleCallableStatement)call).getCursor(2);
		
		while(rs.next()){
			String name = rs.getString(1);
			System.out.println(name);
		}
		
	}
}
