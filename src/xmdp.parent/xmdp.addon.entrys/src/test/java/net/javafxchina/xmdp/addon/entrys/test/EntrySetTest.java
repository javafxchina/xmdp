package net.javafxchina.xmdp.addon.entrys.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.javafxchina.xmdp.addon.entrys.EntrySet;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleButton;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.SideMenu;

public class EntrySetTest {

	@Test
	public void test() throws Exception {
		EntrySet ns=new EntrySet();
		ns.init();
		List<SideMenu> sms=ns.getSideMenus();
		assertNotEquals(sms.size(), 0);
		for(SideMenu sm:sms) {
			System.out.println(sm);
		}
		
		List<ModuleButton> mbs=ns.getModuleButtons();
		assertNotEquals(mbs.size(), 0);
		for(ModuleButton sm:mbs) {
			System.out.println(sm);
		}
		
		List<ModuleInfo> mis=ns.getModuleInfos();
		assertNotEquals(mis.size(), 0);
		for(ModuleInfo sm:mis) {
			System.out.println(sm);
		}
		
	}

}
