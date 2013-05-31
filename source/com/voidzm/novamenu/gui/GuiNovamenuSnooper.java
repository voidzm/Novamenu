package com.voidzm.novamenu.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;

public class GuiNovamenuSnooper extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;

	private GameSettings settings;

	private List list1 = new ArrayList();
	private List list2 = new ArrayList();

	private String screenTitle;
	private String[] stringArray;
	private GuiNovamenuSnooperSlot snooperSlot;
	private GuiButtonTransparent buttonAllow;

	public boolean isIngame = false;

	public GuiNovamenuSnooper(GuiNovamenuScreen par1GuiScreen, GameSettings par2GameSettings) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		if(par1GuiScreen instanceof GuiNovamenuOptions) {
			this.isIngame = ((GuiNovamenuOptions)par1GuiScreen).isIngame;
		}
		this.settings = par2GameSettings;
	}

	public void initGui() {
		this.screenTitle = StatCollector.translateToLocal("options.snooper.title");
		String s = StatCollector.translateToLocal("options.snooper.desc");
		ArrayList arraylist = new ArrayList();
		Iterator iterator = this.fontRenderer.listFormattedStringToWidth(s, this.width - 30).iterator();
		while(iterator.hasNext()) {
			String s1 = (String)iterator.next();
			arraylist.add(s1);
		}
		this.stringArray = (String[])arraylist.toArray(new String[0]);
		this.list1.clear();
		this.list2.clear();
		this.buttons.clear();
		this.buttons.add(this.buttonAllow = new GuiButtonTransparent(this, this.width / 2 - 152, this.height - 30, 150, 16, 3, this.settings.getKeyBinding(EnumOptions.SNOOPER_ENABLED)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 2, this.height - 30, 150, 16, 2, StatCollector.translateToLocal("gui.done")));
		boolean flag = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
		Iterator iterator1 = (new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();
		Entry entry;
		while(iterator1.hasNext()) {
			entry = (Entry)iterator1.next();
			this.list1.add((flag ? "C " : "") + (String)entry.getKey());
			this.list2.add(this.fontRenderer.trimStringToWidth((String)entry.getValue(), this.width - 220));
		}
		if(flag) {
			iterator1 = (new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();
			while(iterator1.hasNext()) {
				entry = (Entry)iterator1.next();
				this.list1.add("S " + (String)entry.getKey());
				this.list2.add(this.fontRenderer.trimStringToWidth((String)entry.getValue(), this.width - 220));
			}
		}
		this.snooperSlot = new GuiNovamenuSnooperSlot(this);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 2:
				this.settings.saveOptions();
				this.settings.saveOptions();
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
			case 3:
				this.settings.setOptionValue(EnumOptions.SNOOPER_ENABLED, 1);
				this.buttonAllow.text = this.settings.getKeyBinding(EnumOptions.SNOOPER_ENABLED);
				break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(!isIngame) {
			super.drawScreenBackground(par1, par2, par3);
		}
		this.drawRect(0, 0, width, 80, 0xBB000000);
		this.drawRect(0, 80, width, 81, 0xDD000000);
		this.drawRect(0, 81, width, height-41, 0x88000000);
		this.drawRect(0, height-41, width, height-40, 0xDD000000);
		this.drawRect(0, height-40, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.snooperSlot.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 18, 16777215);
		int k = 32;
		String[] astring = this.stringArray;
		int l = astring.length;
		for(int i1 = 0; i1 < l; ++i1) {
			String s = astring[i1];
			this.drawCenteredString(this.fontRenderer, s, this.width / 2, k, 8421504);
			k += this.fontRenderer.FONT_HEIGHT;
		}
	}

	public static List fetchList1(GuiNovamenuSnooper par0GuiSnooper) {
		return par0GuiSnooper.list1;
	}

	public static List fetchList2(GuiNovamenuSnooper par0GuiSnooper) {
		return par0GuiSnooper.list2;
	}

}
