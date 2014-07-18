//**
//**  GuiNovamenuWorldSlot.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.util.Date;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.storage.SaveFormatComparator;

public class GuiNovamenuWorldSlot extends GuiNovamenuSlot {

	private GuiNovamenuSelectWorld parent;

	public GuiNovamenuWorldSlot(GuiNovamenuSelectWorld guiParent) {
		super(Minecraft.getMinecraft(), guiParent.width, guiParent.height, 48, guiParent.height - 80, 36);
		this.parent = guiParent;
	}

	@Override
	protected int getSize() {
		return GuiNovamenuSelectWorld.getSize(this.parent).size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		GuiNovamenuSelectWorld.onElementSelected(this.parent, par1);
		boolean flag1 = GuiNovamenuSelectWorld.getSelectedWorld(this.parent) >= 0 && GuiNovamenuSelectWorld.getSelectedWorld(this.parent) < this.getSize();
		GuiNovamenuSelectWorld.getSelectButton(this.parent).enabled = flag1;
		GuiNovamenuSelectWorld.getRenameButton(this.parent).enabled = flag1;
		GuiNovamenuSelectWorld.getDeleteButton(this.parent).enabled = flag1;
		GuiNovamenuSelectWorld.func_82312_f(this.parent).enabled = flag1;
		if(par2 && flag1) {
			this.parent.selectWorld(par1);
		}
	}

	@Override
	protected boolean isSelected(int i) {
		return i == GuiNovamenuSelectWorld.getSelectedWorld(this.parent);
	}

	@Override
	protected int getContentHeight() {
		return GuiNovamenuSelectWorld.getSize(this.parent).size() * 36;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		SaveFormatComparator saveformatcomparator = (SaveFormatComparator)GuiNovamenuSelectWorld.getSize(this.parent).get(par1);
		String s = saveformatcomparator.getDisplayName();
		if(s == null || MathHelper.stringNullOrLengthZero(s)) {
			s = GuiNovamenuSelectWorld.func_82313_g(this.parent) + " " + (par1 + 1);
		}
		String s1 = saveformatcomparator.getFileName();
		s1 = s1 + " (" + GuiNovamenuSelectWorld.func_82315_h(this.parent).format(new Date(saveformatcomparator.getLastTimePlayed()));
		s1 = s1 + ")";
		String s2 = "";
		if(saveformatcomparator.requiresConversion()) {
			s2 = GuiNovamenuSelectWorld.func_82311_i(this.parent) + " " + s2;
		}
		else {
			s2 = GuiNovamenuSelectWorld.func_82314_j(this.parent)[saveformatcomparator.getEnumGameType().getID()];
			if(saveformatcomparator.isHardcoreModeEnabled()) {
				s2 = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gameMode.hardcore") + EnumChatFormatting.RESET;
			}
			if(saveformatcomparator.getCheatsEnabled()) {
				s2 = s2 + ", " + StatCollector.translateToLocal("selectWorld.cheats");
			}
		}
		this.parent.drawString(this.parent.fontRenderer(), s, par2 + 2, par3 + 1, 16777215);
		this.parent.drawString(this.parent.fontRenderer(), s1, par2 + 2, par3 + 12, 8421504);
		this.parent.drawString(this.parent.fontRenderer(), s2, par2 + 2, par3 + 12 + 10, 8421504);
	}

}
