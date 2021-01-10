package net.javafxchina.xmdp.core.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.javafxchina.xmdp.core.util.JSONUtil;

public class JSONUtilTest {
	class Item {
		String key;
		String showName;
		int x;
		int y;

		public Item(String key, String showName, int x, int y) {
			super();
			this.key = key;
			this.showName = showName;
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Item)) {
				return false;
			}
			Item objItem = (Item) obj;
			if (this.key.equals(objItem.key) && this.showName.equals(objItem.showName) && this.x == objItem.x
					&& this.y == objItem.y) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 将对象数组写成json 字符串
	 */
	@Test
	public void test() {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item("1", "张三abc1", 1, 1000));
		String result = JSONUtil.toJson(list);
		System.out.println(result);
		assertNotNull(result);

		List<Item> list2 = JSONUtil.getList(result, Item[].class);
		assertEquals(list.get(0), list2.get(0));
	}

	/**
	 * 将json串写入文件，然后重新读取并比较
	 * @throws Exception
	 */
	@Test
	public void testFile() throws Exception {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item("1", "张三abc1", 1, 1000));
		File file = new File("d:/test.json");
		Item[] its = new Item[list.size()];
		its = list.toArray(its);
		JSONUtil.toJson(file, its, Item[].class);

		URL url = file.toURI().toURL();
		List<Item> list2 = JSONUtil.getList(url, Item[].class);
		assertEquals(list.get(0), list2.get(0));
	}

}
