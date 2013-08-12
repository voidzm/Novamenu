package com.voidzm.novamenu.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StringTranslate;

public class GuiNovamenuMultiplayerOptions extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;

	private GameSettings settings;
	private String chatOptionsTitle;
	private String multiplayerOptionsTitle;
	private int someInt = 0;
	
	public boolean isIngame = false;

	public GuiNovamenuMultiplayerOptions(GuiNovamenuScreen par1GuiScreen, GameSettings par2GameSettings) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		if(par1GuiScreen instanceof GuiNovamenuOptions) {
			this.isIngame = ((GuiNovamenuOptions)par1GuiScreen).isIngame;
		}
		this.settings = par2GameSettings;
	}

	@Override
	public void initGui() {
		int i = 0;
		this.chatOptionsTitle = I18n.func_135053_a("options.chat.title");
		this.multiplayerOptionsTitle = I18n.func_135053_a("options.multiplayer.title");
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6, 150, 16, 0, this.settings.getKeyBinding(EnumOptions.CHAT_VISIBILITY)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 6, 150, 16, 1, this.settings.getKeyBinding(EnumOptions.CHAT_COLOR)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6 + 24, 150, 16, 2, this.settings.getKeyBinding(EnumOptions.CHAT_LINKS)));
		this.buttons.add(new GuiSliderTransparent(this, 3, this.width / 2 + 5, this.height / 6 + 24, EnumOptions.CHAT_OPACITY, this.settings.getKeyBinding(EnumOptions.CHAT_OPACITY), this.settings.getOptionFloatValue(EnumOptions.CHAT_OPACITY)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6 + 48, 150, 16, 4, this.settings.getKeyBinding(EnumOptions.CHAT_LINKS_PROMPT)));
		this.buttons.add(new GuiSliderTransparent(this, 5, this.width / 2 + 5, this.height / 6 + 48, EnumOptions.CHAT_SCALE, this.settings.getKeyBinding(EnumOptions.CHAT_SCALE), this.settings.getOptionFloatValue(EnumOptions.CHAT_SCALE)));
		this.buttons.add(new GuiSliderTransparent(this, 6, this.width / 2 - 155, this.height / 6 + 72, EnumOptions.CHAT_HEIGHT_FOCUSED, this.settings.getKeyBinding(EnumOptions.CHAT_HEIGHT_FOCUSED), this.settings.getOptionFloatValue(EnumOptions.CHAT_HEIGHT_FOCUSED)));
		this.buttons.add(new GuiSliderTransparent(this, 7, this.width / 2 + 5, this.height / 6 + 72, EnumOptions.CHAT_HEIGHT_UNFOCUSED, this.settings.getKeyBinding(EnumOptions.CHAT_HEIGHT_UNFOCUSED), this.settings.getOptionFloatValue(EnumOptions.CHAT_HEIGHT_UNFOCUSED)));
		this.buttons.add(new GuiSliderTransparent(this, 8, this.width / 2 - 155, this.height / 6 + 96, EnumOptions.CHAT_WIDTH, this.settings.getKeyBinding(EnumOptions.CHAT_WIDTH), this.settings.getOptionFloatValue(EnumOptions.CHAT_WIDTH)));
		this.someInt = this.height / 6 + 120;
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6 + 144, 150, 16, 9, this.settings.getKeyBinding(EnumOptions.SHOW_CAPE)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 6 + 168, 200, 16, 10, I18n.func_135053_a("gui.done")));
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 0:
				this.settings.setOptionValue(EnumOptions.CHAT_VISIBILITY, 1);
				this.buttons.get(0).text = this.settings.getKeyBinding(EnumOptions.CHAT_VISIBILITY);
				break;
			case 1:
				this.settings.setOptionValue(EnumOptions.CHAT_COLOR, 1);
				this.buttons.get(1).text = this.settings.getKeyBinding(EnumOptions.CHAT_COLOR);
				break;
			case 2:
				this.settings.setOptionValue(EnumOptions.CHAT_LINKS, 1);
				this.buttons.get(2).text = this.settings.getKeyBinding(EnumOptions.CHAT_LINKS);
				break;
			case 4:
				this.settings.setOptionValue(EnumOptions.CHAT_LINKS_PROMPT, 1);
				this.buttons.get(4).text = this.settings.getKeyBinding(EnumOptions.CHAT_LINKS_PROMPT);
				break;
			case 9:
				this.settings.setOptionValue(EnumOptions.SHOW_CAPE, 1);
				this.buttons.get(9).text = this.settings.getKeyBinding(EnumOptions.SHOW_CAPE);
				break;
			case 10:
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
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.chatOptionsTitle, this.width / 2, 20, 16777215);
		this.drawCenteredString(this.fontRenderer, this.multiplayerOptionsTitle, this.width / 2, this.someInt + 7, 16777215);
	}

}
