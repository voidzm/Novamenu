package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public class GuiNovamenuVideoSettings extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	protected String screenTitle = "Video Settings";
	private GameSettings settings;
	
	private boolean is64Bit = false;
	
	public boolean isIngame = false;

	public GuiNovamenuVideoSettings(GuiNovamenuScreen par1GuiScreen, GameSettings par2GameSettings) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		if(par1GuiScreen instanceof GuiNovamenuOptions) { // If this is false, something went very very wrong.
			this.isIngame = ((GuiNovamenuOptions)par1GuiScreen).isIngame;
		}
		this.settings = par2GameSettings;
	}

	@Override
	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		this.screenTitle = t.translateKey("options.videoTitle");
		this.buttons.clear();
		this.is64Bit = false;
		String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
		String[] astring1 = astring;
		int i = astring.length;
		for(int j = 0; j < i; ++j) {
			String s = astring1[j];
			String s1 = System.getProperty(s);
			if(s1 != null && s1.contains("64")) {
				this.is64Bit = true;
				break;
			}
		}
		int h = this.is64Bit ? 0 : -15;
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + h, 150, 16, 0, this.settings.getKeyBinding(EnumOptions.GRAPHICS)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + h, 150, 16, 1, this.settings.getKeyBinding(EnumOptions.RENDER_DISTANCE)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + 24 + h, 150, 16, 2, this.settings.getKeyBinding(EnumOptions.AMBIENT_OCCLUSION)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 24 + h, 150, 16, 3, this.settings.getKeyBinding(EnumOptions.FRAMERATE_LIMIT)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + 48 + h, 150, 16, 4, this.settings.getKeyBinding(EnumOptions.ANAGLYPH)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 48 + h, 150, 16, 5, this.settings.getKeyBinding(EnumOptions.VIEW_BOBBING)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + 72 + h, 150, 16, 6, this.settings.getKeyBinding(EnumOptions.GUI_SCALE)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 72 + h, 150, 16, 7, this.settings.getKeyBinding(EnumOptions.ADVANCED_OPENGL)));
		this.buttons.add(new GuiSliderTransparent(this, 8, this.width / 2 - 155, this.height / 7 + 96 + h, EnumOptions.GAMMA, this.settings.getKeyBinding(EnumOptions.GAMMA), this.settings.getOptionFloatValue(EnumOptions.GAMMA)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 96 + h, 150, 16, 9, this.settings.getKeyBinding(EnumOptions.RENDER_CLOUDS)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + 120 + h, 150, 16, 10, this.settings.getKeyBinding(EnumOptions.PARTICLES)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 120 + h, 150, 16, 11, this.settings.getKeyBinding(EnumOptions.USE_SERVER_TEXTURES)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height / 7 + 144 + h, 150, 16, 12, this.settings.getKeyBinding(EnumOptions.USE_FULLSCREEN)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height / 7 + 144 + h, 150, 16, 13, this.settings.getKeyBinding(EnumOptions.ENABLE_VSYNC)));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 6 + 168, 200, 16, 14, t.translateKey("gui.done")));
	}

	@Override
	public void buttonEvent(int id) {
		int i = this.settings.guiScale;
		switch(id) {
			case 0:
				this.settings.setOptionValue(EnumOptions.GRAPHICS, 1);
				this.buttons.get(0).text = this.settings.getKeyBinding(EnumOptions.GRAPHICS);
				break;
			case 1:
				this.settings.setOptionValue(EnumOptions.RENDER_DISTANCE, 1);
				this.buttons.get(1).text = this.settings.getKeyBinding(EnumOptions.RENDER_DISTANCE);
				break;
			case 2:
				this.settings.setOptionValue(EnumOptions.AMBIENT_OCCLUSION, 1);
				this.buttons.get(2).text = this.settings.getKeyBinding(EnumOptions.AMBIENT_OCCLUSION);
				break;
			case 3:
				this.settings.setOptionValue(EnumOptions.FRAMERATE_LIMIT, 1);
				this.buttons.get(3).text = this.settings.getKeyBinding(EnumOptions.FRAMERATE_LIMIT);
				break;
			case 4:
				this.settings.setOptionValue(EnumOptions.ANAGLYPH, 1);
				this.buttons.get(4).text = this.settings.getKeyBinding(EnumOptions.ANAGLYPH);
				break;
			case 5:
				this.settings.setOptionValue(EnumOptions.VIEW_BOBBING, 1);
				this.buttons.get(5).text = this.settings.getKeyBinding(EnumOptions.VIEW_BOBBING);
				break;
			case 6:
				this.settings.setOptionValue(EnumOptions.GUI_SCALE, 1);
				this.buttons.get(6).text = this.settings.getKeyBinding(EnumOptions.GUI_SCALE);
				break;
			case 7:
				this.settings.setOptionValue(EnumOptions.ADVANCED_OPENGL, 1);
				this.buttons.get(7).text = this.settings.getKeyBinding(EnumOptions.ADVANCED_OPENGL);
				break;
			case 9:
				this.settings.setOptionValue(EnumOptions.RENDER_CLOUDS, 1);
				this.buttons.get(9).text = this.settings.getKeyBinding(EnumOptions.RENDER_CLOUDS);
				break;
			case 10:
				this.settings.setOptionValue(EnumOptions.PARTICLES, 1);
				this.buttons.get(10).text = this.settings.getKeyBinding(EnumOptions.PARTICLES);
				break;
			case 11:
				this.settings.setOptionValue(EnumOptions.USE_SERVER_TEXTURES, 1);
				this.buttons.get(11).text = this.settings.getKeyBinding(EnumOptions.USE_SERVER_TEXTURES);
				break;
			case 12:
				this.settings.setOptionValue(EnumOptions.USE_FULLSCREEN, 1);
				this.buttons.get(12).text = this.settings.getKeyBinding(EnumOptions.USE_FULLSCREEN);
				break;
			case 13:
				this.settings.setOptionValue(EnumOptions.ENABLE_VSYNC, 1);
				this.buttons.get(13).text = this.settings.getKeyBinding(EnumOptions.ENABLE_VSYNC);
				break;
			case 14:
				this.mc.gameSettings.saveOptions();
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
		}
		if(this.settings.guiScale != i) {
			ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int j = res.getScaledWidth();
			int k = res.getScaledHeight();
			this.setWorldAndResolution(this.mc, j, k);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(!isIngame) {
			super.drawScreenBackground(par1, par2, par3);
		}
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, this.is64Bit ? 20 : 5, 16777215);
		if(!this.is64Bit && this.settings.renderDistance == 0) {
			this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("options.farWarning1"), this.width / 2, this.height / 6 + 144 + 1, 11468800);
			this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("options.farWarning2"), this.width / 2, this.height / 6 + 144 + 13, 11468800);
		}
	}

}
