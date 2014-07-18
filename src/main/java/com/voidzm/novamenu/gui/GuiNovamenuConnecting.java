package com.voidzm.novamenu.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

import com.voidzm.novamenu.thread.ThreadNovamenuConnectToServer;

public class GuiNovamenuConnecting extends GuiNovamenuScreen {

	private NetClientHandler netHandler;
	private GuiNovamenuScreen parent;
	private boolean cancelled = false;

	public GuiNovamenuConnecting(GuiNovamenuScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData) {
		this.mc = par2Minecraft;
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		ServerAddress serveraddress = ServerAddress.func_78860_a(par3ServerData.serverIP);
		par2Minecraft.setServerData(par3ServerData);
		this.spawnNewServerThread(serveraddress.getIP(), serveraddress.getPort());
	}

	public GuiNovamenuConnecting(GuiNovamenuScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4) {
		this.mc = par2Minecraft;
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		this.spawnNewServerThread(par3Str, par4);
	}

	private void spawnNewServerThread(String par1Str, int par2) {
		this.mc.getLogAgent().logInfo("Connecting to " + par1Str + ", " + par2);
		(new ThreadNovamenuConnectToServer(this, par1Str, par2)).start();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if(this.netHandler != null) {
			this.netHandler.processReadPackets();
		}
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {}

	@Override
	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 0, I18n.getString("gui.cancel")));
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.cancelled = true;
			if(this.netHandler != null) {
				this.netHandler.disconnect();
			}
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		if(this.netHandler == null) {
			this.drawCenteredString(this.fontRenderer, I18n.getString("connect.connecting"), this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
		}
		else {
			this.drawCenteredString(this.fontRenderer, I18n.getString("connect.authorizing"), this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, this.netHandler.field_72560_a, this.width / 2, this.height / 2 - 10, 16777215);
		}
	}

	public static NetClientHandler setNetClientHandler(GuiNovamenuConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler) {
		return par0GuiConnecting.netHandler = par1NetClientHandler;
	}

	public static Minecraft func_74256_a(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.mc;
	}

	public static boolean isCancelled(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.cancelled;
	}

	public static Minecraft func_74254_c(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.mc;
	}

	public static NetClientHandler getNetClientHandler(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.netHandler;
	}

	public static GuiNovamenuScreen func_98097_e(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.parent;
	}

	public static Minecraft func_74250_f(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.mc;
	}

	public static Minecraft func_74251_g(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.mc;
	}

	public static Minecraft func_98096_h(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.mc;
	}

	public static int getTick(GuiNovamenuConnecting par0GuiConnecting) {
		return par0GuiConnecting.imageTick;
	}
	
	public static void forceTermination(GuiNovamenuConnecting gui) {
		gui.cancelled = true;
		gui.netHandler = null;
	}

}
