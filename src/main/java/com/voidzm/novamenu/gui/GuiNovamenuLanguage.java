package com.voidzm.novamenu.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StringTranslate;

public class GuiNovamenuLanguage extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private GuiNovamenuLanguageSlot languageSlot;
	private GameSettings settings;
	private LanguageManager languageManager;
	private GuiButtonTransparent doneButton;
	
	public boolean isIngame = false;

	public GuiNovamenuLanguage(GuiNovamenuScreen par1GuiScreen, GameSettings par2GameSettings, LanguageManager par3LanguageManager) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		if(par1GuiScreen instanceof GuiNovamenuOptions) {
			this.isIngame = ((GuiNovamenuOptions)par1GuiScreen).isIngame;
		}
		this.settings = par2GameSettings;
		this.languageManager = par3LanguageManager;
	}

	@Override
	public void initGui() {
		this.buttons.add(this.doneButton = new GuiButtonTransparent(this, this.width / 2 - 75, this.height - 26, 150, 16, 6, I18n.getString("gui.done")));
		this.languageSlot = new GuiNovamenuLanguageSlot(this);
		this.languageSlot.boxshift = 0;
		this.languageSlot.registerScrollButtons(this.buttons, 7, 8);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 6:
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(!isIngame) {
			super.drawScreenBackground(par1, par2, par3);
		}
		this.drawRect(0, 0, width, 40, 0xBB000000);
		this.drawRect(0, 40, width, 41, 0xDD000000);
		this.drawRect(0, 41, width, height-61, 0x88000000);
		this.drawRect(0, height-61, width, height-60, 0xDD000000);
		this.drawRect(0, height-60, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.languageSlot.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, I18n.getString("options.language"), this.width / 2, 18, 16777215);
		this.drawCenteredString(this.fontRenderer, "(" + I18n.getString("options.languageWarning") + ")", this.width / 2, this.height - 46, 8421504);
	}

	public static GameSettings getGameSettings(GuiNovamenuLanguage par0GuiLanguage) {
		return par0GuiLanguage.settings;
	}

	public static GuiButtonTransparent getDoneButton(GuiNovamenuLanguage par0GuiLanguage) {
		return par0GuiLanguage.doneButton;
	}
	
	public static LanguageManager getLanguageManager(GuiNovamenuLanguage par0GuiLanguage) {
		return par0GuiLanguage.languageManager;
	}

}
