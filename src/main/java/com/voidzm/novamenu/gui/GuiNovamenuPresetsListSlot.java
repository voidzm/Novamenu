//**
//**  GuiNovamenuPresetsListSlot.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiNovamenuPresetsListSlot extends GuiNovamenuSlot {

	public int field_82459_a;
	private final GuiNovamenuFlatPresets parent;

	public GuiNovamenuPresetsListSlot(GuiNovamenuFlatPresets screen) {
		super(screen.getMinecraft(), screen.width, screen.height, 80, screen.height - 37, 24);
		this.parent = screen;
		this.field_82459_a = -1;
	}

	private void func_82457_a(int par1, int par2, int par3) {
		this.func_82456_d(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		GuiNovamenuFlatPresets.getPresetIconRenderer().renderItemIntoGUI(this.parent.getFontRenderer(), this.parent.getMinecraft().renderEngine, new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	private void func_82456_d(int par1, int par2) {
		this.func_82455_b(par1, par2, 0, 0);
	}

	private void func_82455_b(int par1, int par2, int par3, int par4) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.parent.getMinecraft().getTextureManager().bindTexture(Gui.statIcons);
		float f = 0.0078125F;
		float f1 = 0.0078125F;
		boolean flag = true;
		boolean flag1 = true;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + 18, this.parent.getZLevel(), (par3 + 0) * 0.0078125F, (par4 + 18) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 18, par2 + 18, this.parent.getZLevel(), (par3 + 18) * 0.0078125F, (par4 + 18) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 18, par2 + 0, this.parent.getZLevel(), (par3 + 18) * 0.0078125F, (par4 + 0) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, this.parent.getZLevel(), (par3 + 0) * 0.0078125F, (par4 + 0) * 0.0078125F);
		tessellator.draw();
	}

	@Override
	protected int getSize() {
		return GuiNovamenuFlatPresets.getPresets().size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		this.field_82459_a = par1;
		this.parent.func_82296_g();
		GuiNovamenuFlatPresets.func_82298_b(this.parent).setText(((GuiNovamenuFlatPresets.GuiFlatPresetsItem)GuiNovamenuFlatPresets.getPresets().get(GuiNovamenuFlatPresets.func_82292_a(this.parent).field_82459_a)).presetData);
	}

	@Override
	protected boolean isSelected(int par1) {
		return par1 == this.field_82459_a;
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		GuiNovamenuFlatPresets.GuiFlatPresetsItem guiflatpresetsitem = (GuiNovamenuFlatPresets.GuiFlatPresetsItem)GuiNovamenuFlatPresets.getPresets().get(par1);
		this.func_82457_a(par2, par3, guiflatpresetsitem.iconId);
		this.parent.getFontRenderer().drawString(guiflatpresetsitem.presetName, par2 + 18 + 5, par3 + 6, 16777215);
	}

}
