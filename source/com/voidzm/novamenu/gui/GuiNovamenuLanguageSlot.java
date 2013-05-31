package com.voidzm.novamenu.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

public class GuiNovamenuLanguageSlot extends GuiNovamenuSlot {

	private final GuiNovamenuLanguage parent;
	
	private ArrayList arrayList;
	private TreeMap treeMap;

	public GuiNovamenuLanguageSlot(GuiNovamenuLanguage screen) {
		super(screen.getMinecraft(), screen.width, screen.height, 40, screen.height - 60, 18);
		this.parent = screen;
		this.treeMap = StringTranslate.getInstance().getLanguageList();
		this.arrayList = new ArrayList();
		Iterator iterator = this.treeMap.keySet().iterator();
		while(iterator.hasNext()) {
			String s = (String)iterator.next();
			this.arrayList.add(s);
		}
	}

	@Override
	protected int getSize() {
		return this.arrayList.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		StringTranslate.getInstance().setLanguage((String)this.arrayList.get(par1), false);
		this.parent.getMinecraft().fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
		GuiNovamenuLanguage.getGameSettings(this.parent).language = (String)this.arrayList.get(par1);
		this.parent.getFontRenderer().setBidiFlag(StringTranslate.isBidirectional(GuiNovamenuLanguage.getGameSettings(this.parent).language));
		GuiNovamenuLanguage.getDoneButton(this.parent).text = StringTranslate.getInstance().translateKey("gui.done");
		GuiNovamenuLanguage.getGameSettings(this.parent).saveOptions();
	}

	@Override
	protected boolean isSelected(int i) {
		return ((String)this.arrayList.get(i)).equals(StringTranslate.getInstance().getCurrentLanguage());
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 18;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		ScaledResolution res = new ScaledResolution(GuiNovamenuLanguage.getGameSettings(this.parent), parent.getMinecraft().displayWidth, parent.getMinecraft().displayHeight);
		int f = res.getScaleFactor();
		GL11.glScissor(0, 61*f, parent.width*f, (parent.height-102)*f);
		this.parent.getFontRenderer().setBidiFlag(true);
		this.parent.drawCenteredString(this.parent.getFontRenderer(), (String)this.treeMap.get(this.arrayList.get(par1)), this.parent.width / 2, par3 + 1, 16777215);
		this.parent.getFontRenderer().setBidiFlag(StringTranslate.isBidirectional(GuiNovamenuLanguage.getGameSettings(this.parent).language));
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();
	}

}
