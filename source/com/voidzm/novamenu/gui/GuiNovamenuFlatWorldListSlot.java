//**
//**  GuiNovamenuFlatWorldListSlot.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.FlatLayerInfo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiNovamenuFlatWorldListSlot extends GuiNovamenuSlot {

	public int field_82454_a;
	
	private final GuiNovamenuCreateFlatWorld parent;
	
	public GuiNovamenuFlatWorldListSlot(GuiNovamenuCreateFlatWorld parentScreen) {
		super(parentScreen.getMinecraft(), parentScreen.width, parentScreen.height, 43, parentScreen.height - 60, 24);
		this.parent = parentScreen;
		this.field_82454_a = -1;
	}

	private void func_82452_a(int par1, int par2, ItemStack par3ItemStack) {
		this.func_82451_d(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(par3ItemStack != null) {
			RenderHelper.enableGUIStandardItemLighting();
			GuiNovamenuCreateFlatWorld.getRenderItem().renderItemIntoGUI(this.parent.getFontRenderer(), this.parent.getMinecraft().renderEngine, par3ItemStack, par1 + 2, par2 + 2);
			RenderHelper.disableStandardItemLighting();
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	private void func_82451_d(int par1, int par2){
		this.func_82450_b(par1, par2, 0, 0);
	}

	private void func_82450_b(int par1, int par2, int par3, int par4){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.parent.getMinecraft().func_110434_K().func_110577_a(Gui.field_110323_l);
		float f = 0.0078125F;
		float f1 = 0.0078125F;
		boolean flag = true;
		boolean flag1 = true;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 18), (double)this.parent.getZLevel(), (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
		tessellator.addVertexWithUV((double)(par1 + 18), (double)(par2 + 18), (double)this.parent.getZLevel(), (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
		tessellator.addVertexWithUV((double)(par1 + 18), (double)(par2 + 0), (double)this.parent.getZLevel(), (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.parent.getZLevel(), (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
		tessellator.draw();
	}


	@Override
	protected int getSize() {
		return GuiNovamenuCreateFlatWorld.func_82271_a(this.parent).getFlatLayers().size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		this.field_82454_a = par1;
		this.parent.func_82270_g();
	}

	@Override
	protected boolean isSelected(int i) {
		return i == this.field_82454_a;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		FlatLayerInfo flatlayerinfo = (FlatLayerInfo)GuiNovamenuCreateFlatWorld.func_82271_a(this.parent).getFlatLayers().get(GuiNovamenuCreateFlatWorld.func_82271_a(this.parent).getFlatLayers().size() - par1 - 1);
		ItemStack itemstack = flatlayerinfo.getFillBlock() == 0 ? null : new ItemStack(flatlayerinfo.getFillBlock(), 1, flatlayerinfo.getFillBlockMeta());
		String s = itemstack == null ? "Air" : Item.itemsList[flatlayerinfo.getFillBlock()].getItemStackDisplayName(itemstack);
		this.func_82452_a(par2, par3, itemstack);
		this.parent.getFontRenderer().drawString(s, par2 + 18 + 5, par3 + 3, 16777215);
		String s1;
		if(par1 == 0) {
			s1 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.top", new Object[] {Integer.valueOf(flatlayerinfo.getLayerCount())});
		}
		else if(par1 == GuiNovamenuCreateFlatWorld.func_82271_a(this.parent).getFlatLayers().size() - 1) {
			s1 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.bottom", new Object[] {Integer.valueOf(flatlayerinfo.getLayerCount())});
		}
		else {
			s1 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer", new Object[] {Integer.valueOf(flatlayerinfo.getLayerCount())});
		}

		this.parent.getFontRenderer().drawString(s1, par2 + 2 + 213 - this.parent.getFontRenderer().getStringWidth(s1), par3 + 3, 16777215);
	}

	@Override
	protected int getScrollBarX() {
		return this.parent.width - 70;
	}

}
