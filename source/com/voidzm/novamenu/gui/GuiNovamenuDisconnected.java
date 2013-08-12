package com.voidzm.novamenu.gui;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import com.voidzm.novamenu.asm.NovamenuPlugin;
import com.voidzm.novamenu.asm.ReobfuscationMappingHelper;

import cpw.mods.fml.client.GuiDupesFound;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

public class GuiNovamenuDisconnected extends GuiNovamenuScreen {

	private String errorMessage;
	private String errorDetail;
	private Object[] messageArray;
	private List messageList;
	private final GuiNovamenuScreen parent;

	public GuiNovamenuDisconnected(GuiNovamenuScreen par1GuiScreen, String par2Str, String par3Str, int tick, Object ... par4ArrayOfObj) {
		this.parent = par1GuiScreen;
		this.errorMessage = I18n.func_135053_a(par2Str);
		this.errorDetail = par3Str;
		this.messageArray = par4ArrayOfObj;
		this.imageTick = tick;
	}

	public GuiNovamenuDisconnected(GuiScreen par1Disconnected) {
		if(!(par1Disconnected instanceof GuiDisconnected)) { // The ASM code that is supposed to call this went very, very bad.
			throw new RuntimeException();
		}
		this.parent = new GuiNovamenuMultiplayer(new GuiNovamenuMainMenu());
		String oldMessage = "";
		String oldDetail = "";
		Object[] oldMessageArray = null;
		try {
			String errorMessageName = NovamenuPlugin.isDevEnvironment ? "errorMessage" : ReobfuscationMappingHelper.getInstance().attemptRemapFieldName("net.minecraft.client.gui.GuiDisconnected/errorMessage");
			String errorDetailName = NovamenuPlugin.isDevEnvironment ? "errorDetail" : ReobfuscationMappingHelper.getInstance().attemptRemapFieldName("net.minecraft.client.gui.GuiDisconnected/errorDetail");
			String objArrayName = NovamenuPlugin.isDevEnvironment ? "field_74247_c" : ReobfuscationMappingHelper.getInstance().attemptRemapFieldName("net.minecraft.client.gui.GuiDisconnected/field_74247_c");
			Field m = GuiDisconnected.class.getDeclaredField(errorMessageName);
			m.setAccessible(true);
			oldMessage = (String)m.get(par1Disconnected);
			Field d = GuiDisconnected.class.getDeclaredField(errorDetailName);
			d.setAccessible(true);
			oldDetail = (String)d.get(par1Disconnected);
			Field o = GuiDisconnected.class.getDeclaredField(objArrayName);
			o.setAccessible(true);
			oldMessageArray = (Object[])o.get(par1Disconnected);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.errorMessage = oldMessage;
		this.errorDetail = oldDetail;
		this.messageArray = oldMessageArray;
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {}

	@Override
	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 0, I18n.func_135053_a("gui.toMenu")));
		if(this.messageArray != null) {
			this.messageList = this.fontRenderer.listFormattedStringToWidth(I18n.func_135052_a(this.errorDetail, this.messageArray), this.width - 50);
		}
		else {
			this.messageList = this.fontRenderer.listFormattedStringToWidth(I18n.func_135053_a(this.errorDetail), this.width - 50);
		}
	}
	
	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 11184810);
		int k = this.height / 2 - 30;
		if(this.messageList != null) {
			for(Iterator iterator = this.messageList.iterator(); iterator.hasNext(); k += this.fontRenderer.FONT_HEIGHT) {
				String s = (String)iterator.next();
				this.drawCenteredString(this.fontRenderer, s, this.width / 2, k, 16777215);
			}
		}
	}

}
