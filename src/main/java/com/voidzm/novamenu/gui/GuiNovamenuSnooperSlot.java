package com.voidzm.novamenu.gui;

import net.minecraft.client.renderer.Tessellator;

public class GuiNovamenuSnooperSlot extends GuiNovamenuSlot {

	private GuiNovamenuSnooper parent;

	public GuiNovamenuSnooperSlot(GuiNovamenuSnooper par1GuiSnooper) {
		super(par1GuiSnooper.getMinecraft(), par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.getFontRenderer().FONT_HEIGHT + 1);
		this.parent = par1GuiSnooper;
	}

	@Override
	protected int getSize() {
		return GuiNovamenuSnooper.fetchList1(this.parent).size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {}

	@Override
	protected boolean isSelected(int i) {
		return false;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		this.parent.getFontRenderer().drawString((String)GuiNovamenuSnooper.fetchList1(this.parent).get(par1), 10, par3, 16777215);
		this.parent.getFontRenderer().drawString((String)GuiNovamenuSnooper.fetchList2(this.parent).get(par1), 230, par3, 16777215);
	}

	@Override
	protected int getScrollBarX() {
		return this.parent.width - 10;
	}

}
