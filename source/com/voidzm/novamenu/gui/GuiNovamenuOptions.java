package com.voidzm.novamenu.gui;

import cpw.mods.fml.client.FMLClientHandler;
import java.util.ArrayList;

import com.voidzm.novamenu.asm.NovamenuPlugin;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public class GuiNovamenuOptions extends GuiNovamenuScreen {
	
	private GuiNovamenuScreen parent;
	private GameSettings settings;
	private String screenTitle = "Options";
	
	public boolean isIngame = false;
	
	public GuiNovamenuOptions(GuiNovamenuScreen par1GuiScreen, GameSettings par2GameSettings) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		if(par1GuiScreen instanceof GuiNovamenuIngameMenu) {
			this.isIngame = true;
		}
		this.settings = par2GameSettings;
	}

	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiSliderTransparent(this, 0, this.width / 2 - 155, this.height / 6 - 12, EnumOptions.MUSIC, this.settings.getKeyBinding(EnumOptions.MUSIC), this.settings.getOptionFloatValue(EnumOptions.MUSIC)));
		this.buttons.add(new GuiSliderTransparent(this, 1, this.width / 2 + 5, this.height / 6 - 12, EnumOptions.SOUND, this.settings.getKeyBinding(EnumOptions.SOUND), this.settings.getOptionFloatValue(EnumOptions.SOUND)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6 + 12, 150, 16, 2, this.settings.getKeyBinding(EnumOptions.INVERT_MOUSE)));
		this.buttons.add(new GuiSliderTransparent(this, 3, this.width / 2 + 5, this.height / 6 + 12, EnumOptions.SENSITIVITY, this.settings.getKeyBinding(EnumOptions.SENSITIVITY), this.settings.getOptionFloatValue(EnumOptions.SENSITIVITY)));
		this.buttons.add(new GuiSliderTransparent(this, 4, this.width / 2 - 155, this.height / 6 + 36, EnumOptions.FOV, this.settings.getKeyBinding(EnumOptions.FOV), this.settings.getOptionFloatValue(EnumOptions.FOV)));
		GuiButtonTransparent difficultyButton = new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 6 + 36, 150, 16, 5, this.settings.getKeyBinding(EnumOptions.DIFFICULTY));
		if(this.mc.theWorld != null && this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			difficultyButton.enabled = false;
			difficultyButton.text = StatCollector.translateToLocal("options.difficulty") + ": " + StatCollector.translateToLocal("options.difficulty.hardcore");
		}
		this.buttons.add(difficultyButton);
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 6 + 60, 150, 16, 6, this.settings.getKeyBinding(EnumOptions.TOUCHSCREEN)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 152, this.height / 6 + 90, 150, 16, 7, I18n.func_135053_a("options.video")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 2, this.height / 6 + 90, 150, 16, 8, I18n.func_135053_a("options.controls")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 152, this.height / 6 + 114, 150, 16, 9, I18n.func_135053_a("options.language")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 2, this.height / 6 + 114, 150, 16, 10, I18n.func_135053_a("options.multiplayer.title")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 152, this.height / 6 + 138, 150, 16, 11, I18n.func_135053_a("options.resourcepack")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 2, this.height / 6 + 138, 150, 16, 12, I18n.func_135053_a("options.snooper.view")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 6 + 168, 200, 16, 13, I18n.func_135053_a("gui.done")));
	}

	public void buttonEvent(int id) {
		switch(id) {
			case 2:
				this.settings.setOptionValue(EnumOptions.INVERT_MOUSE, 1);
				this.buttons.get(2).text = this.settings.getKeyBinding(EnumOptions.INVERT_MOUSE);
				break;
			case 5:
				this.settings.setOptionValue(EnumOptions.DIFFICULTY, 1);
				this.buttons.get(5).text = this.settings.getKeyBinding(EnumOptions.DIFFICULTY);
				break;
			case 6:
				this.settings.setOptionValue(EnumOptions.TOUCHSCREEN, 1);
				this.buttons.get(6).text = this.settings.getKeyBinding(EnumOptions.TOUCHSCREEN);
				break;
			case 7:
				this.mc.gameSettings.saveOptions();
				ArrayList<ModContainer> ofList = new ArrayList<ModContainer>();
				FMLClientHandler.instance().addSpecialModEntries(ofList);
				if(!ofList.isEmpty() || !NovamenuPlugin.getConfiguration().useCustomVideoSettingsMenu) {
					this.mc.displayGuiScreen(new GuiVideoSettings(this, this.settings));
				}
				else {
					this.mc.displayGuiScreen(new GuiNovamenuVideoSettings(this, this.settings));
				}
				break;
			case 8:
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiNovamenuControls(this, this.settings));
				break;
			case 9:
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiNovamenuLanguage(this, this.settings, this.mc.func_135016_M()));
				break;
			case 10:
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiNovamenuMultiplayerOptions(this, this.settings));
				break;
			case 11:
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiNovamenuResourcePacks(this, this.settings));
				break;
			case 12:
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiNovamenuSnooper(this, this.settings));
				break;
			case 13:
				this.mc.gameSettings.saveOptions();
				if(!(this.parent instanceof GuiNovamenuIngameMenu)) {
					this.parent.imageTick = this.imageTick;
				}
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
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 15, 16777215);
	}

}
