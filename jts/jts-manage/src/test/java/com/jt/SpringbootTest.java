package com.jt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTest {

	@Autowired
	private ItemMapper mapper;
	
	@Test
	public void testMapper() {
		List<Item> list = mapper.selectList(null);
		System.out.println(list);
	}
}
