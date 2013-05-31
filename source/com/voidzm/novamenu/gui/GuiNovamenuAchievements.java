package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.stats.StatFileWriter;

public class GuiNovamenuAchievements extends GuiAchievements {

	public GuiNovamenuAchievements(StatFileWriter par1StatFileWriter) {
		super(par1StatFileWriter);
	}
	
	@Override
	public void drawDefaultBackground() {
		this.drawRect(0, 0, width, height, 0xBB000000);
	}

}
