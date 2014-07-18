package com.voidzm.novamenu.gui;

import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.ModContainer;

public class GuiNovamenuModSlot extends GuiNovamenuSlot {

	private GuiNovamenuModList parent;
	private ArrayList<ModContainer> modList;
	
	public GuiNovamenuModSlot(GuiNovamenuModList screen, ArrayList<ModContainer> mods) {
		super(screen.getMinecraft(), screen.width, screen.height, 40, screen.height-50, 35);
		this.parent = screen;
		this.modList = mods;
	}

	@Override
	protected int getSize() {
		return this.modList.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		this.parent.selectModIndex(par1);
	}

	@Override
	protected boolean isSelected(int i) {
		return this.parent.modIndexSelected(i);
	}

	@Override
	protected int getContentHeight() {
		return (this.getSize()) * 35 + 1;
	}

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		ModContainer mc = this.modList.get(listIndex);
		if(Loader.instance().getModState(mc) == ModState.DISABLED) {
			this.parent.drawString(this.parent.getFontRenderer(), mc.getName(), var2 + 3, var3 + 2, 0xFF2222);
			this.parent.drawString(this.parent.getFontRenderer(), mc.getDisplayVersion(), var2 + 3, var3 + 12, 0xFF2222);
			this.parent.drawString(this.parent.getFontRenderer(), "DISABLED", var2 + 3, var3 + 22, 0xFF2222);
		}
		else {
			this.parent.drawString(this.parent.getFontRenderer(), mc.getName(), var2 + 3, var3 + 2, 0xFFFFFF);
			this.parent.drawString(this.parent.getFontRenderer(), mc.getDisplayVersion(), var2 + 3, var3 + 12, 0xCCCCCC);
			this.parent.drawString(this.parent.getFontRenderer(), mc.getMetadata() != null ? mc.getMetadata().getChildModCountString() : "Metadata not found", var2 + 3, var3 + 22, 0xCCCCCC);
		}
	}
	
}
