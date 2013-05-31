package com.voidzm.novamenu.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumOS;
import net.minecraft.util.StringTranslate;

import org.lwjgl.Sys;

public class GuiNovamenuTexturePacks extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private int refreshTimer = -1;
	private String fileLocation = "";

	private GuiNovamenuTexturePackSlot texturePackSlot;

	private GameSettings settings;

	public boolean isIngame = false;

	public GuiNovamenuTexturePacks(GuiNovamenuScreen par1, GameSettings par2) {
		this.parent = par1;
		this.imageTick = par1.imageTick;
		if(par1 instanceof GuiNovamenuOptions) {
			this.isIngame = ((GuiNovamenuOptions)par1).isIngame;
		}
		this.settings = par2;
	}

	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 154, this.height - 43, 150, 16, 5, t.translateKey("texturePack.openFolder")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 4, this.height - 43, 150, 16, 6, t.translateKey("gui.done")));
		this.mc.texturePackList.updateAvaliableTexturePacks();
		this.fileLocation = (new File(Minecraft.getMinecraftDir(), "texturepacks")).getAbsolutePath();
		this.texturePackSlot = new GuiNovamenuTexturePackSlot(this);
		this.texturePackSlot.registerScrollButtons(this.buttons, 7, 8);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 5:
				if(Minecraft.getOs() == EnumOS.MACOS) {
					try {
						this.mc.getLogAgent().logInfo(this.fileLocation);
						Runtime.getRuntime().exec(new String[] { "/usr/bin/open", this.fileLocation });
						return;
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
				else if(Minecraft.getOs() == EnumOS.WINDOWS) {
					String s = String.format("cmd.exe /C start \"Open file\" \"%s\"", this.fileLocation);
					try {
						Runtime.getRuntime().exec(s);
						return;
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
				boolean flag = false;
				try {
					Class clazz = Class.forName("java.awt.Desktop");
					Object object = clazz.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
					clazz.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {(new File(Minecraft.getMinecraftDir(), "texturepacks")).toURI()});
				}
				catch(Throwable throwable) {
					throwable.printStackTrace();
					flag = true;
				}
				if(flag) {
					this.mc.getLogAgent().logInfo("Opening via system class!");
					Sys.openURL("file://" + this.fileLocation);
				}
				break;
			case 6:
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
			default:
				this.texturePackSlot.actionPerformed(id);
				break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(this.refreshTimer <= 0) {
			this.mc.texturePackList.updateAvaliableTexturePacks();
			this.refreshTimer += 20;
		}
		if(!isIngame) {
			super.drawScreenBackground(par1, par2, par3);
		}
		this.drawRect(0, 0, width, 32, 0xBB000000);
		this.drawRect(0, 32, width, 33, 0xDD000000);
		this.drawRect(0, 33, width, height-52, 0x88000000);
		this.drawRect(0, height-52, width, height-51, 0xDD000000);
		this.drawRect(0, height-51, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.texturePackSlot.drawScreen(par1, par2, par3);
		StringTranslate t = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, t.translateKey("texturePack.title"), this.width / 2, 13, 16777215);
		this.drawCenteredString(this.fontRenderer, t.translateKey("texturePack.folderInfo"), this.width / 2 - 77, this.height - 21, 8421504);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		--this.refreshTimer;
	}
	
	public static Minecraft fetchMinecraft(GuiNovamenuTexturePacks par1) {
		return par1.mc;
	}
	
	public static FontRenderer fetchFontRenderer(GuiNovamenuTexturePacks par1) {
		return par1.fontRenderer;
	}

}
