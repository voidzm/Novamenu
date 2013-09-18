//**
//**  GuiNovamenuCreateWorld.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.util.Random;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuCreateWorld extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private GuiTextField textWorldName;
	private GuiTextField textWorldSeed;
	private String folderName;
	
	private String gamemode = "survival";
	private boolean generateStructures = true;
	private boolean allowCheats = false;
	private boolean cheatsToggled = false;
	private boolean bonusChest = false;
	
	private boolean isHardcore = false;
	private boolean createClicked;
	
	private boolean moreOptions;
	
	private GuiButtonTransparent buttonGamemode;
	private GuiButtonTransparent buttonMoreOptions;
	private GuiButtonTransparent buttonGenStructures;
	private GuiButtonTransparent buttonBonusChest;
	private GuiButtonTransparent buttonWorldType;
	private GuiButtonTransparent buttonAllowCheats;
	private GuiButtonTransparent buttonCustomize;
	
	private String gamemodeLine1;
	private String gamemodeLine2;
	
	private String seed;
	
	private String localizedNewWorldText;
	private int worldTypeID = 0;
	
	public String genOptions = "";
	
	private static final String[] ILLEGAL_WORLD_NAMES = new String[] {"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
	
	public GuiNovamenuCreateWorld(GuiNovamenuScreen parentScreen) {
		this.parent = parentScreen;
		this.imageTick = parentScreen.imageTick;
		this.seed = "";
		this.localizedNewWorldText = StatCollector.translateToLocal("selectWorld.newWorld");
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.textWorldName.updateCursorCounter();
		this.textWorldSeed.updateCursorCounter();
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 28, 150, 16, 0, I18n.getString("selectWorld.create")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 28, 150, 16, 1, I18n.getString("gui.cancel")));
		this.buttons.add(this.buttonGamemode = new GuiButtonTransparent(this, this.width / 2 - 75, 115, 150, 16, 2, I18n.getString("selectWorld.gameMode")));
		this.buttons.add(this.buttonMoreOptions = new GuiButtonTransparent(this, this.width / 2 - 75, 187, 150, 16, 3, I18n.getString("selectWorld.moreWorldOptions")));
		this.buttons.add(this.buttonGenStructures = new GuiButtonTransparent(this, this.width / 2 - 155, 100, 150, 16, 4, I18n.getString("selectWorld.mapFeatures")));
		this.buttonGenStructures.drawButton = false;
		this.buttons.add(this.buttonBonusChest = new GuiButtonTransparent(this, this.width / 2 + 5, 151, 150, 16, 7, I18n.getString("selectWorld.bonusItems")));
		this.buttonBonusChest.drawButton = false;
		this.buttons.add(this.buttonWorldType = new GuiButtonTransparent(this, this.width / 2 + 5, 100, 150, 16, 5, I18n.getString("selectWorld.mapType")));
		this.buttonWorldType.drawButton = false;
		this.buttons.add(this.buttonAllowCheats = new GuiButtonTransparent(this, this.width / 2 - 155, 151, 150, 16, 6, I18n.getString("selectWorld.allowCommands")));
		this.buttonAllowCheats.drawButton = false;
		this.buttons.add(this.buttonCustomize = new GuiButtonTransparent(this, this.width / 2 + 5, 120, 150, 16, 8, I18n.getString("selectWorld.customizeType")));
		this.buttonCustomize.drawButton = false;
		this.textWorldName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
		this.textWorldName.setFocused(true);
		this.textWorldName.setText(this.localizedNewWorldText);
		this.textWorldSeed = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
		this.textWorldSeed.setText(this.seed);
		this.func_82288_a(this.moreOptions);
		this.makeUseableName();
		this.updateButtonText();
	}
	
	private void makeUseableName() {
		this.folderName = this.textWorldName.getText().trim();
		char[] achar = ChatAllowedCharacters.allowedCharactersArray;
		int i = achar.length;
		for(int j = 0; j < i; ++j) {
			char c0 = achar[j];
			this.folderName = this.folderName.replace(c0, '_');
		}
		if(MathHelper.stringNullOrLengthZero(this.folderName)) {
			this.folderName = "World";
		}
		this.folderName = removeIllegalNames(this.mc.getSaveLoader(), this.folderName);
	}
	
	private void updateButtonText() {
		this.buttonGamemode.text = I18n.getString("selectWorld.gameMode") + " " + I18n.getString("selectWorld.gameMode." + this.gamemode);
		this.gamemodeLine1 = I18n.getString("selectWorld.gameMode." + this.gamemode + ".line1");
		this.gamemodeLine2 = I18n.getString("selectWorld.gameMode." + this.gamemode + ".line2");
		this.buttonGenStructures.text = I18n.getString("selectWorld.mapFeatures") + " ";
		if(this.generateStructures) {
			this.buttonGenStructures.text = this.buttonGenStructures.text + I18n.getString("options.on");
		}
		else {
			this.buttonGenStructures.text = this.buttonGenStructures.text + I18n.getString("options.off");
		}
		this.buttonBonusChest.text = I18n.getString("selectWorld.bonusItems") + " ";
		if(this.bonusChest && !this.isHardcore) {
			this.buttonBonusChest.text = this.buttonBonusChest.text + I18n.getString("options.on");
		}
		else {
			this.buttonBonusChest.text = this.buttonBonusChest.text + I18n.getString("options.off");
		}
		this.buttonWorldType.text = I18n.getString("selectWorld.mapType") + " " + I18n.getString(WorldType.worldTypes[this.worldTypeID].getTranslateName());
		this.buttonAllowCheats.text = I18n.getString("selectWorld.allowCommands") + " ";
		if(this.allowCheats && !this.isHardcore) {
			this.buttonAllowCheats.text = this.buttonAllowCheats.text + I18n.getString("options.on");
		}
		else {
			this.buttonAllowCheats.text = this.buttonAllowCheats.text + I18n.getString("options.off");
		}
	}
	
	public static String removeIllegalNames(ISaveFormat par0ISaveFormat, String par1Str) {
		par1Str = par1Str.replaceAll("[\\./\"]", "_");
		String[] astring = ILLEGAL_WORLD_NAMES;
		int i = astring.length;
		for(int j = 0; j < i; ++j) {
			String s1 = astring[j];
			if(par1Str.equalsIgnoreCase(s1)) {
				par1Str = "_" + par1Str + "_";
			}
		}
		while(par0ISaveFormat.getWorldInfo(par1Str) != null) {
			par1Str = par1Str + "-";
		}
		return par1Str;
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.mc.displayGuiScreen((GuiScreen)null);
			if(this.createClicked) {
				return;
			}
			this.createClicked = true;
			long i = (new Random()).nextLong();
			String s = this.textWorldSeed.getText();
			if(!MathHelper.stringNullOrLengthZero(s)) {
				try {
					long j = Long.parseLong(s);
					if(j != 0L) {
						i = j;
					}
				} catch (NumberFormatException numberformatexception) {
					i = (long)s.hashCode();
				}
			}
			WorldType.worldTypes[this.worldTypeID].onGUICreateWorldPress();
			EnumGameType enumgametype = EnumGameType.getByName(this.gamemode);
			WorldSettings worldsettings = new WorldSettings(i, enumgametype, this.generateStructures, this.isHardcore, WorldType.worldTypes[this.worldTypeID]);
			worldsettings.func_82750_a(this.genOptions);
			if(this.bonusChest && !this.isHardcore) {
				worldsettings.enableBonusChest();
			}
			if(this.allowCheats && !this.isHardcore) {
				worldsettings.enableCommands();
			}
			this.mc.launchIntegratedServer(this.folderName, this.textWorldName.getText().trim(), worldsettings);
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		case 2:
			if(this.gamemode.equals("survival")) {
				if(!this.cheatsToggled) {
					this.cheatsToggled = false;
				}
				this.isHardcore = false;
				this.gamemode = "hardcore";
				this.isHardcore = true;
				this.buttonAllowCheats.enabled = false;
				this.buttonBonusChest.enabled = false;
				this.updateButtonText();
			}
			else if(this.gamemode.equals("hardcore")) {
				if(!this.cheatsToggled) {
					this.allowCheats = true;
				}
				this.isHardcore = false;
				this.gamemode = "creative";
				this.updateButtonText();
				this.isHardcore = false;
				this.buttonAllowCheats.enabled = true;
				this.buttonBonusChest.enabled = true;
			}
			else {
				if(!this.cheatsToggled) {
					this.allowCheats = false;
				}
				this.gamemode = "survival";
				this.updateButtonText();
				this.buttonAllowCheats.enabled = true;
				this.buttonBonusChest.enabled = true;
				this.isHardcore = false;
			}
			this.updateButtonText();
			break;
		case 3:
			this.func_82287_i();
			break;
		case 4:
			this.generateStructures = !this.generateStructures;
			this.updateButtonText();
			break;
		case 5:
			++this.worldTypeID;
			if(this.worldTypeID >= WorldType.worldTypes.length) {
				this.worldTypeID = 0;
			}
			while(WorldType.worldTypes[this.worldTypeID] == null || !WorldType.worldTypes[this.worldTypeID].getCanBeCreated()) {
				++this.worldTypeID;
				if(this.worldTypeID >= WorldType.worldTypes.length) {
					this.worldTypeID = 0;
				}
			}
			this.genOptions = "";
			this.updateButtonText();
			this.func_82288_a(this.moreOptions);
			break;
		case 6:
			this.cheatsToggled = true;
			this.allowCheats = !this.allowCheats;
			this.updateButtonText();
			break;
		case 7:
			this.bonusChest = !this.bonusChest;
			this.updateButtonText();
			break;
		case 8:
			if(WorldType.worldTypes[this.worldTypeID] == WorldType.FLAT) {
				this.mc.displayGuiScreen(new GuiNovamenuCreateFlatWorld(this, this.genOptions));
			}
			break;
		}
	}

	private void func_82287_i() {
		this.func_82288_a(!this.moreOptions);
	}

	private void func_82288_a(boolean par1) {
		this.moreOptions = par1;
		this.buttonGamemode.drawButton = !this.moreOptions;
		this.buttonGenStructures.drawButton = this.moreOptions;
		this.buttonBonusChest.drawButton = this.moreOptions;
		this.buttonWorldType.drawButton = this.moreOptions;
		this.buttonAllowCheats.drawButton = this.moreOptions;
		this.buttonCustomize.drawButton = this.moreOptions && (WorldType.worldTypes[this.worldTypeID].isCustomizable());
		if(this.moreOptions){
			this.buttonMoreOptions.text = I18n.getString("gui.done");
		}
		else {
			this.buttonMoreOptions.text = I18n.getString("selectWorld.moreWorldOptions");
		}
	}

	@Override
	protected void keyTyped(char par1, int par2){
		if (this.textWorldName.isFocused() && !this.moreOptions){
			this.textWorldName.textboxKeyTyped(par1, par2);
			this.localizedNewWorldText = this.textWorldName.getText();
		}
		else if(this.textWorldSeed.isFocused() && this.moreOptions) {
			this.textWorldSeed.textboxKeyTyped(par1, par2);
			this.seed = this.textWorldSeed.getText();
		}
		if(par1 == 13) {
			this.actionPerformed((GuiButton)this.buttonList.get(0));
		}
		((GuiButtonTransparent)this.buttons.get(0)).enabled = this.textWorldName.getText().length() > 0;
		this.makeUseableName();
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3){
		super.mouseClicked(par1, par2, par3);
		if(this.moreOptions){
			this.textWorldSeed.mouseClicked(par1, par2, par3);
		}
		else {
			this.textWorldName.mouseClicked(par1, par2, par3);
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, I18n.getString("selectWorld.create"), this.width / 2, 20, 16777215);
		if(this.moreOptions) {
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.enterSeed"), this.width / 2 - 100, 47, 10526880);
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.seedInfo"), this.width / 2 - 100, 85, 10526880);
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.mapFeatures.info"), this.width / 2 - 150, 122, 10526880);
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.allowCommands.info"), this.width / 2 - 150, 172, 10526880);
			this.textWorldSeed.drawTextBox();
		}
		else {
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
			this.drawString(this.fontRenderer, I18n.getString("selectWorld.resultFolder") + " " + this.folderName, this.width / 2 - 100, 85, 10526880);
			this.textWorldName.drawTextBox();
			this.drawString(this.fontRenderer, this.gamemodeLine1, this.width / 2 - 100, 137, 10526880);
			this.drawString(this.fontRenderer, this.gamemodeLine2, this.width / 2 - 100, 149, 10526880);
		}
	}

	public void func_82286_a(WorldInfo par1WorldInfo) {
		this.localizedNewWorldText = StatCollector.translateToLocalFormatted("selectWorld.newWorld.copyOf", new Object[] {par1WorldInfo.getWorldName()});
		this.seed = par1WorldInfo.getSeed() + "";
		this.worldTypeID = par1WorldInfo.getTerrainType().getWorldTypeID();
		this.genOptions = par1WorldInfo.getGeneratorOptions();
		this.generateStructures = par1WorldInfo.isMapFeaturesEnabled();
		this.allowCheats = par1WorldInfo.areCommandsAllowed();
		if(par1WorldInfo.isHardcoreModeEnabled()) {
			this.gamemode = "hardcore";
		}
		else if(par1WorldInfo.getGameType().isSurvivalOrAdventure()) {
			this.gamemode = "survival";
		}
		else if(par1WorldInfo.getGameType().isCreative()) {
			this.gamemode = "creative";
		}
	}

}
