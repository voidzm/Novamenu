package com.voidzm.novamenu.thread;

import java.net.ConnectException;
import java.net.UnknownHostException;

import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet2ClientProtocol;

import com.voidzm.novamenu.gui.GuiNovamenuConnecting;
import com.voidzm.novamenu.gui.GuiNovamenuDisconnected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ThreadNovamenuConnectToServer extends Thread {
	
	public final String ip;
	public final int port;

	public final GuiNovamenuConnecting connectingGui;

	public ThreadNovamenuConnectToServer(GuiNovamenuConnecting par1GuiConnecting, String par2Str, int par3) {
		this.connectingGui = par1GuiConnecting;
		this.ip = par2Str;
		this.port = par3;
	}

	public void run() {
		try {
			GuiNovamenuConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(GuiNovamenuConnecting.func_74256_a(this.connectingGui), this.ip, this.port));
			if(GuiNovamenuConnecting.isCancelled(this.connectingGui)) return;
			GuiNovamenuConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2ClientProtocol(61, GuiNovamenuConnecting.func_74254_c(this.connectingGui).session.username, this.ip, this.port));
		}
		catch(UnknownHostException unknownhostexception){
			if(GuiNovamenuConnecting.isCancelled(this.connectingGui)) return;
			GuiNovamenuConnecting.func_74250_f(this.connectingGui).displayGuiScreen(new GuiNovamenuDisconnected(GuiNovamenuConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", GuiNovamenuConnecting.getTick(this.connectingGui), new Object[] {"Unknown host \'" + this.ip + "\'"}));
		}
		catch(ConnectException connectexception) {
			if(GuiNovamenuConnecting.isCancelled(this.connectingGui)) return;
			GuiNovamenuConnecting.func_74251_g(this.connectingGui).displayGuiScreen(new GuiNovamenuDisconnected(GuiNovamenuConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", GuiNovamenuConnecting.getTick(this.connectingGui), new Object[] {connectexception.getMessage()}));
		}
		catch(Exception exception) {
			if(GuiNovamenuConnecting.isCancelled(this.connectingGui)) return;
			exception.printStackTrace();
			GuiNovamenuConnecting.func_98096_h(this.connectingGui).displayGuiScreen(new GuiNovamenuDisconnected(GuiNovamenuConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", GuiNovamenuConnecting.getTick(this.connectingGui), new Object[] {exception.toString()}));
		}
	}
}

