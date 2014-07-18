package com.voidzm.novamenu.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;

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
		Iterator iterator = GuiNovamenuLanguage.getLanguageManager(screen).getLanguages().iterator();
		while(iterator.hasNext()) {
			Language language = (Language)iterator.next();
			this.map.put(language.getLanguageCode(), language);
			this.list.add(language.getLanguageCode());
		}
	}

	@Override
	protected int getSize() {
		return this.list.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		Language language = (Language)this.map.get(this.list.get(par1));
		GuiNovamenuLanguage.getLanguageManager(this.parent).setCurrentLanguage(language);
		GuiNovamenuLanguage.getGameSettings(this.parent).language = language.getLanguageCode();
		this.parent.getMinecraft().refreshResources();
		this.parent.getFontRenderer().setUnicodeFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).isCurrentLocaleUnicode());
		this.parent.getFontRenderer().setBidiFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).isCurrentLanguageBidirectional());
		GuiNovamenuLanguage.getDoneButton(this.parent).text = I18n.getString("gui.done");
		GuiNovamenuLanguage.getGameSettings(this.parent).saveOptions();
	}

	@Override
	protected boolean isSelected(int i) {
		return ((String)this.list.get(i)).equals(GuiNovamenuLanguage.getLanguageManager(this.parent).getCurrentLanguage().getLanguageCode());
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 18;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		this.parent.getFontRenderer().setBidiFlag(true);
		this.parent.drawCenteredString(this.parent.getFontRenderer(), this.map.get(this.list.get(par1)).toString(), this.parent.width / 2, par3 + 1, 16777215);
		this.parent.getFontRenderer().setBidiFlag(GuiNovamenuLanguage.getLanguageManager(this.parent).getCurrentLanguage().isBidirectional());
	}

}
