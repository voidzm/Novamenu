package com.voidzm.novamenu.gui;

import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.texturepacks.ITexturePack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiNovamenuTexturePackSlot extends GuiNovamenuSlot {

	private GuiNovamenuTexturePacks parent;

	public GuiNovamenuTexturePackSlot(GuiNovamenuTexturePacks par1) {
		super(GuiNovamenuTexturePacks.fetchMinecraft(par1), par1.width, par1.height, 32, par1.height - 51, 36);
		this.parent = par1;
	}

	@Override
	protected int getSize() {
		return GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.availableTexturePacks().size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		List list = GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.availableTexturePacks();
		try {
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.setTexturePack((ITexturePack)list.get(par1));
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderEngine.refreshTextures();
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderGlobal.loadRenderers();
		}
		catch(Exception exception) {
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.setTexturePack((ITexturePack)list.get(0));
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderEngine.refreshTextures();
			GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderGlobal.loadRenderers();
		}
	}

	@Override
	protected boolean isSelected(int i) {
		List list = GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.availableTexturePacks();
		return GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.getSelectedTexturePack() == list.get(i);
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 36;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		ITexturePack itexturepack = (ITexturePack)GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.availableTexturePacks().get(par1);
		itexturepack.bindThumbnailTexture(GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderEngine);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		par5Tessellator.startDrawingQuads();
		par5Tessellator.setColorOpaque_I(16777215);
		par5Tessellator.addVertexWithUV((double)par2, (double)(par3 + par4), 0.0D, 0.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)(par3 + par4), 0.0D, 1.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)par3, 0.0D, 1.0D, 0.0D);
		par5Tessellator.addVertexWithUV((double)par2, (double)par3, 0.0D, 0.0D, 0.0D);
		par5Tessellator.draw();
		String s = itexturepack.getTexturePackFileName();
		if(!itexturepack.isCompatible()) {
			s = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("texturePack.incompatible") + " - " + s;
		}
		if(s.length() > 32) {
			s = s.substring(0, 32).trim() + "...";
		}
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), s, par2 + 32 + 2, par3 + 1, 16777215);
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), itexturepack.getFirstDescriptionLine(), par2 + 32 + 2, par3 + 12, 8421504);
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), itexturepack.getSecondDescriptionLine(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
	}

}
