package com.voidzm.novamenu.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.multiplayer.LanServer;
import net.minecraft.client.multiplayer.LanServerList;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ThreadLanServerFind;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuMultiplayer extends GuiNovamenuScreen {

	private static int threadsPending = 0;
	private static Object lock = new Object();
	
	private GuiNovamenuScreen parent;
	
	private GuiNovamenuServerSlot slotServer;
	private ServerList internetServerList;
	
	private int selectedServer = -1;
	private GuiButtonTransparent buttonEdit;
	private GuiButtonTransparent buttonSelect;
	private GuiButtonTransparent buttonDelete;
	
	private boolean deleteClicked = false;
	private boolean addClicked = false;
	private boolean editClicked = false;
	private boolean directClicked = false;
	
	private String lagTooltip = null;
	
	private ServerData serverData = null;
	private LanServerList localServerList;
	private ThreadLanServerFind serverFindThread;
	
	private int ticksOpen;
	private boolean initialized;
	private List localServers = Collections.emptyList();
	
	public GuiNovamenuMultiplayer(GuiNovamenuScreen screen) {
		this.parent = screen;
		this.imageTick = screen.imageTick;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		if(!this.initialized) {
			this.initialized = true;
			this.internetServerList = new ServerList(this.mc);
			this.internetServerList.loadServerList();
			this.localServerList = new LanServerList();
			try {
				this.serverFindThread = new ThreadLanServerFind(this.localServerList);
				this.serverFindThread.start();
			}
			catch(Exception exception) {
				this.mc.getLogAgent().logWarning("Unable to start LAN server detection: " + exception.getMessage());
			}
			this.slotServer = new GuiNovamenuServerSlot(this);
		}
		else {
			this.slotServer.func_77207_a(this.width, this.height, 32, this.height - 64);
		}
		this.initGuiControls();
	}

	public void initGuiControls() {
		this.buttons.add(this.buttonEdit = new GuiButtonTransparent(this, this.width / 2 - 154, this.height - 28, 70, 16, 7, I18n.getString("selectServer.edit")));
		this.buttons.add(this.buttonDelete = new GuiButtonTransparent(this, this.width / 2 - 74, this.height - 28, 70, 16, 2, I18n.getString("selectServer.delete")));
		this.buttons.add(this.buttonSelect = new GuiButtonTransparent(this, this.width / 2 - 154, this.height - 52, 100, 16, 1, I18n.getString("selectServer.select")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 50, this.height - 52, 100, 16, 4, I18n.getString("selectServer.direct")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 4 + 50, this.height - 52, 100, 16, 3, I18n.getString("selectServer.add")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 4, this.height - 28, 70, 16, 8, I18n.getString("selectServer.refresh")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 4 + 76, this.height - 28, 75, 16, 0, I18n.getString("gui.cancel")));
		boolean flag = this.selectedServer >= 0 && this.selectedServer < this.slotServer.getSize();
		this.buttonSelect.enabled = flag;
		this.buttonEdit.enabled = flag;
		this.buttonDelete.enabled = flag;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++this.ticksOpen;
		if(this.localServerList.getWasUpdated()) {
			this.localServers = this.localServerList.getLanServers();
			this.localServerList.setWasNotUpdated();
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if(this.serverFindThread != null) {
			this.serverFindThread.interrupt();
			this.serverFindThread = null;
		}
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		case 1:
			this.joinServer(this.selectedServer);
			break;
		case 2:
			String s = this.internetServerList.getServerData(this.selectedServer).serverName;
			if(s != null) {
				this.deleteClicked = true;
				String s1 = I18n.getString("selectServer.deleteQuestion");
				String s2 = "\'" + s + "\' " + I18n.getString("selectServer.deleteWarning");
				String s3 = I18n.getString("selectServer.deleteButton");
				String s4 = I18n.getString("gui.cancel");
				GuiNovamenuYesNo guiyesno = new GuiNovamenuYesNo(this, s1, s2, s3, s4, this.selectedServer);
				this.mc.displayGuiScreen(guiyesno);
			}
			break;
		case 3:
			this.addClicked = true;
			this.mc.displayGuiScreen(new GuiNovamenuAddServer(this, this.serverData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
			break;
		case 4:
			this.directClicked = true;
			this.mc.displayGuiScreen(new GuiNovamenuDirectConnect(this, this.serverData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
			break;
		case 7:
			this.editClicked = true;
			ServerData sdata = this.internetServerList.getServerData(this.selectedServer);
			this.serverData = new ServerData(sdata.serverName, sdata.serverIP);
			this.serverData.setHideAddress(sdata.isHidingAddress());
			this.mc.displayGuiScreen(new GuiNovamenuAddServer(this, this.serverData));
			break;
		case 8:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(new GuiNovamenuMultiplayer(this.parent));
			break;
		}
	}

	@Override
	public void confirmClicked(boolean par1, int par2) {
		if(this.deleteClicked) {
			this.deleteClicked = false;
			if(par1) {
				this.internetServerList.removeServerData(par2);
				this.internetServerList.saveServerList();
				this.selectedServer = -1;
			}
			this.mc.displayGuiScreen(this);
		}
		else if(this.directClicked) {
			this.directClicked = false;
			if(par1) {
				this.connectToServer(this.serverData);
			}
			else {
				this.mc.displayGuiScreen(this);
			}
		}
		else if(this.addClicked) {
			this.addClicked = false;
			if(par1) {
				this.internetServerList.addServerData(this.serverData);
				this.internetServerList.saveServerList();
				this.selectedServer = -1;
			}
			this.mc.displayGuiScreen(this);
		}
		else if(this.editClicked) {
			this.editClicked = false;
			if(par1) {
				ServerData serverdata = this.internetServerList.getServerData(this.selectedServer);
				serverdata.serverName = this.serverData.serverName;
				serverdata.serverIP = this.serverData.serverIP;
				serverdata.setHideAddress(this.serverData.isHidingAddress());
				this.internetServerList.saveServerList();
			}
			this.mc.displayGuiScreen(this);
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		int j = this.selectedServer;
		if(par2 == 59) {
			this.mc.gameSettings.hideServerAddress = !this.mc.gameSettings.hideServerAddress;
			this.mc.gameSettings.saveOptions();
		}
		else {
			if(isShiftKeyDown() && par2 == 200) {
				if(j > 0 && j < this.internetServerList.countServers()) {
					this.internetServerList.swapServers(j, j - 1);
					--this.selectedServer;
					if(j < this.internetServerList.countServers() - 1) {
						this.slotServer.func_77208_b(-this.slotServer.slotHeight);
					}
				}
			}
			else if(isShiftKeyDown() && par2 == 208) {
				if(j < this.internetServerList.countServers() - 1) {
					this.internetServerList.swapServers(j, j + 1);
					++this.selectedServer;
					if(j > 0) {
						this.slotServer.func_77208_b(this.slotServer.slotHeight);
					}
				}
			}
			else if(par1 == 13) {
				this.buttonEvent(1);
			}
			else {
				super.keyTyped(par1, par2);
			}
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, 32, 0xBB000000);
		this.drawRect(0, 32, width, 33, 0xDD000000);
		this.drawRect(0, 33, width, height-65, 0x88000000);
		this.drawRect(0, height-65, width, height-64, 0xDD000000);
		this.drawRect(0, height-64, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.lagTooltip = null;
		this.slotServer.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, I18n.getString("multiplayer.title"), this.width / 2, 20, 16777215);
		if(this.lagTooltip != null) {
			this.func_74007_a(this.lagTooltip, par1, par2);
		}
	}

	private void joinServer(int par1) {
		if(par1 < this.internetServerList.countServers()) {
			this.connectToServer(this.internetServerList.getServerData(par1));
		}
		else {
			par1 -= this.internetServerList.countServers();
			if(par1 < this.localServers.size()) {
				LanServer lanserver = (LanServer)this.localServers.get(par1);
				this.connectToServer(new ServerData(lanserver.getServerMotd(), lanserver.getServerIpPort()));
			}
		}
	}

	private void connectToServer(ServerData par1ServerData) {
		this.mc.displayGuiScreen(new GuiNovamenuConnecting(this, this.mc, par1ServerData));
	}

	private static void func_74017_b(ServerData par1ServerData) throws IOException {
		ServerAddress serveraddress = ServerAddress.func_78860_a(par1ServerData.serverIP);
		Socket socket = null;
		DataInputStream datainputstream = null;
		DataOutputStream dataoutputstream = null;
		try {
			socket = new Socket();
			socket.setSoTimeout(3000);
			socket.setTcpNoDelay(true);
			socket.setTrafficClass(18);
			socket.connect(new InetSocketAddress(serveraddress.getIP(), serveraddress.getPort()), 3000);
			datainputstream = new DataInputStream(socket.getInputStream());
			dataoutputstream = new DataOutputStream(socket.getOutputStream());
			dataoutputstream.write(254);
			dataoutputstream.write(1);
			if(datainputstream.read() != 255) {
				throw new IOException("Bad message");
			}
			String s = Packet.readString(datainputstream, 256);
			char[] achar = s.toCharArray();
			for(int i = 0; i < achar.length; ++i) {
				if(achar[i] != 167 && achar[i] != 0 && ChatAllowedCharacters.allowedCharacters.indexOf(achar[i]) < 0) {
					achar[i] = 63;
				}
			}
			s = new String(achar);
			int j;
			int k;
			String[] astring;
			if(s.startsWith("\u00a7") && s.length() > 1) {
				astring = s.substring(1).split("\u0000");
				
				System.out.println("Server: " + par1ServerData.serverIP + ", " + Arrays.toString(astring));
				
				if(MathHelper.parseIntWithDefault(astring[0], 0) == 1) {
					par1ServerData.serverMOTD = astring[3];
					par1ServerData.field_82821_f = MathHelper.parseIntWithDefault(astring[1], par1ServerData.field_82821_f);
					par1ServerData.gameVersion = astring[2];
					j = MathHelper.parseIntWithDefault(astring[4], 0);
					k = MathHelper.parseIntWithDefault(astring[5], 0);
					if(j >= 0 && k >= 0) {
						par1ServerData.populationInfo = EnumChatFormatting.GRAY + "" + j + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + k;
					}
					else {
						par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
					}
				}
				else {
					par1ServerData.gameVersion = "???";
					par1ServerData.serverMOTD = "" + EnumChatFormatting.DARK_GRAY + "???";
					par1ServerData.field_82821_f = 62;
					par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
			}
			else {
				astring = s.split("\u00a7");
				s = astring[0];
				j = -1;
				k = -1;
				try {
					j = Integer.parseInt(astring[1]);
					k = Integer.parseInt(astring[2]);
				}
				catch (Exception exception) {}
				par1ServerData.serverMOTD = EnumChatFormatting.GRAY + s;
				if(j >= 0 && k > 0) {
					par1ServerData.populationInfo = EnumChatFormatting.GRAY + "" + j + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + k;
				}
				else {
					par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
				par1ServerData.gameVersion = "1.3";
				par1ServerData.field_82821_f = 60;
			}
		}
		finally {
			try {
				if(datainputstream != null) {
					datainputstream.close();
				}
			}
			catch(Throwable throwable) {}
			try {
				if(dataoutputstream != null) {
					dataoutputstream.close();
				}
			}
			catch (Throwable throwable1) {}
			try {
				if(socket != null) {
					socket.close();
				}
			}
			catch (Throwable throwable2) {}
		}
	}

	protected void func_74007_a(String par1Str, int par2, int par3) {
		if(par1Str != null) {
			int k = par2 + 12;
			int l = par3 - 12;
			int i1 = this.fontRenderer.getStringWidth(par1Str);
			this.drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, -1073741824, -1073741824);
			this.fontRenderer.drawStringWithShadow(par1Str, k, l, -1);
		}
	}

	public static ServerList getInternetServerList(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.internetServerList;
	}

	public static List getListOfLanServers(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.localServers;
	}

	public static int getSelectedServer(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.selectedServer;
	}

	public static int getAndSetSelectedServer(GuiNovamenuMultiplayer par0GuiMultiplayer, int par1) {
		return par0GuiMultiplayer.selectedServer = par1;
	}

	public static GuiButtonTransparent getButtonSelect(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.buttonSelect;
	}

	public static GuiButtonTransparent getButtonEdit(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.buttonEdit;
	}

	public static GuiButtonTransparent getButtonDelete(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.buttonDelete;
	}

	public static void func_74008_b(GuiNovamenuMultiplayer par0GuiMultiplayer, int par1) {
		par0GuiMultiplayer.joinServer(par1);
	}

	public static int getTicksOpened(GuiNovamenuMultiplayer par0GuiMultiplayer) {
		return par0GuiMultiplayer.ticksOpen;
	}

	public static Object getLock() {
		return lock;
	}

	public static int getThreadsPending() {
		return threadsPending;
	}

	public static int increaseThreadsPending() {
		return threadsPending++;
	}

	public static void func_82291_a(ServerData par0ServerData) throws IOException {
		func_74017_b(par0ServerData);
	}

	public static int decreaseThreadsPending() {
		return threadsPending--;
	}

	public static String getAndSetLagTooltip(GuiNovamenuMultiplayer par0GuiMultiplayer, String par1Str) {
		return par0GuiMultiplayer.lagTooltip = par1Str;
	}
	
}
