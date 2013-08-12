package com.voidzm.novamenu.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuiNovamenuLanguageSlot extends GuiNovamenuSlot {

	private final GuiNovamenuLanguage parent;
	
	private List list;
	private Map map;

	public GuiNovamenuLanguageSlot(GuiNovamenuLanguage screen) {
		super(screen.getMinecraft(), screen.width, screen.height, 40, screen.height - 60, 18);
		this.parent = screen;
		this.map = Maps.newHashMap();
		this.list = Lists.newArrayList();
		Iterator iterator = GuiNovamenuLanguage.getLanguageManager(screen).func_135040_d().iterator();
		while(iterator.hasNext()) {
			Language language = (Language)iterator.next();
			this.map.put(language.func_135034_a(), language);
			this.list.add(language.func_135034_a());
		}
	}

	@Override
	protected int getSize() {
		return this.list.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		Language language = (Language)this.map.get(this.list.get(par1));
		GuiNovamenuLanguage.getLanguageManager(this.parent).func_135045_a(language);
		GuiNovamenuLanguage.getGameSettings(this.parent).language = language.func_135034_a();
		this.parent.getMinecraft().func_110436_a();
		this.parent.getFontRenderer().setUnicodeFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).func_135042_a());
		this.parent.getFontRenderer().setBidiFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).func_135044_b());
		GuiNovamenuLanguage.getDoneButton(this.parent).text = I18n.func_135053_a("gui.done");
		GuiNovamenuLanguage.getGameSettings(this.parent).saveOptions();
	}

	@Override
	protected boolean isSelected(int i) {
		return ((String)this.list.get(i)).equals(GuiNovamenuLanguage.getLanguageManager(this.parent).func_135041_c().func_135034_a());
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 18;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		this.parent.getFontRenderer().setBidiFlag(true);
		this.parent.drawCenteredString(this.parent.getFontRenderer(), (String)this.map.get(this.list.get(par1)).toString(), this.parent.width / 2, par3 + 1, 16777215);
		this.parent.getFontRenderer().setBidiFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).func_135041_c().func_135035_b());
	}

}
