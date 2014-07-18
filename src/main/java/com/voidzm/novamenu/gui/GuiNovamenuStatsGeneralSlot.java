package com.voidzm.novamenu.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

public class GuiNovamenuStatsGeneralSlot extends GuiNovamenuSlot {

	private GuiNovamenuStats parent;
	
	public GuiNovamenuStatsGeneralSlot(GuiNovamenuStats stats) {
		super(stats.getMinecraft(), stats.width, stats.height, 40, stats.height - 72, 10);
		this.parent = stats;
		this.setShowSelectionBox(false);
	}

	@Override
	protected int getSize() {
		return StatList.generalStats.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {}

	@Override
	protected boolean isSelected(int i) {
		return false;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		StatBase statbase = (StatBase)StatList.generalStats.get(par1);
		this.parent.drawString(this.parent.getFontRenderer(), StatCollector.translateToLocal(statbase.getName()), par2 + 2, par3 + 1, par1 % 2 == 0 ? 16777215 : 9474192);
		String s = statbase.func_75968_a(GuiNovamenuStats.getStatsFileWriter(this.parent).writeStat(statbase));
		this.parent.drawString(this.parent.getFontRenderer(), s, par2 + 2 + 213 - this.parent.getFontRenderer().getStringWidth(s), par3 + 1, par1 % 2 == 0 ? 16777215 : 9474192);
	}

}
