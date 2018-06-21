package org.study.todo_homework;

import org.junit.Assert;
import org.junit.Test;
import org.study.Dao.DiaryDAO;


public class ConnectionTest {
	
	@Test
	public void testConnection() {
		DiaryDAO dao = new DiaryDAO();
		
		Assert.assertNotNull(dao.getConnection());
	}
}
