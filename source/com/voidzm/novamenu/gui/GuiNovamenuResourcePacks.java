package com.voidzm.novamenu.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumOS;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.Util;

import org.lwjgl.Sys;

public class GuiNovamenuResourcePacks extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private int refreshTimer = -1;

	private GuiNovamenuResourcePackSlot resourcePackSlot;

	private GameSettings settings;

	public boolean isIngame = false;

	public GuiNovamenuResourcePacks(GuiNovamenuScreen par1, GameSettings par2) {
		this.parent = par1;
		this.imageTick = par1.imageTick;
		if(par1 instanceof GuiNovamenuOptions) {
			this.isIngame = ((GuiNovamenuOptions)par1).isIngame;
		}
		this.settings = par2;
	}

	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 154, this.height - 43, 150, 16, 5, I18n.func_135053_a("resourcePack.openFolder")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 4, this.height - 43, 150, 16, 6, I18n.func_135053_a("gui.done")));
		this.resourcePackSlot = new GuiNovamenuResourcePackSlot(this, this.mc.func_110438_M());
		this.resourcePackSlot.registerScrollButtons(this.buttons, 7, 8);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 5:
				File file1 = GuiNovamenuResourcePackSlot.fetchResourcePackRepository(this.resourcePackSlot).func_110612_e();
				String s = file1.getAbsolutePath();
				if(Util.func_110647_a() == EnumOS.MACOS) {
					try {
						this.mc.getLogAgent().logInfo(s);
						Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
						return;
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
				else if(Util.func_110647_a() == EnumOS.WINDOWS) {
					String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);
					try {
						Runtime.getRuntime().exec(s1);
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
					clazz.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {file1.toURI()});
				}
				catch(Throwable throwable) {
					throwable.printStackTrace();
					flag = true;
				}
				if(flag) {
					this.mc.getLogAgent().logInfo("Opening via system class!");
					Sys.openURL("file://" + s);
				}
				break;
			case 6:
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
			default:
				this.resourcePackSlot.actionPerformed(id);
				break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(this.refreshTimer <= 0) {
			GuiNovamenuResourcePackSlot.fetchResourcePackRepository(this.resourcePackSlot).func_110611_a();
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
		this.resourcePackSlot.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a("resourcePack.title"), this.width / 2, 13, 16777215);
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a("resourcePack.folderInfo"), this.width / 2 - 77, this.height - 21, 8421504);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		--this.refreshTimer;
	}
	
	public static Minecraft fetchMinecraft(GuiNovamenuResourcePacks par1) {
		return par1.mc;
	}
	
	public static FontRenderer fetchFontRenderer(GuiNovamenuResourcePacks par1) {
		return par1.fontRenderer;
	}

}
