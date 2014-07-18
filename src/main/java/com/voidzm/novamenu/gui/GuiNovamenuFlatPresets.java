//**
//**  GuiNovamenuFlatPresets.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuFlatPresets extends GuiNovamenuScreen {

	private static RenderItem renderItem = new RenderItem();
	private static final List presets = new ArrayList();
	private final GuiNovamenuCreateFlatWorld parent;
	private String field_82300_d;
	private String field_82308_m;
	private String field_82306_n;
	protected GuiNovamenuPresetsListSlot theFlatPresetsListSlot;
	private GuiButtonTransparent theButton;
	private GuiTextField theTextField;

	public GuiNovamenuFlatPresets(GuiNovamenuCreateFlatWorld parentScreen) {
		this.parent = parentScreen;
		this.imageTick = parentScreen.imageTick;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.field_82300_d = StatCollector.translateToLocal("createWorld.customize.presets.title");
		this.field_82308_m = StatCollector.translateToLocal("createWorld.customize.presets.share");
		this.field_82306_n = StatCollector.translateToLocal("createWorld.customize.presets.list");
		this.theTextField = new GuiTextField(this.fontRenderer, 50, 40, this.width - 100, 20);
		this.theFlatPresetsListSlot = new GuiNovamenuPresetsListSlot(this);
		this.theFlatPresetsListSlot.boxshift = 1;
		this.theTextField.setMaxStringLength(1230);
		this.theTextField.setText(this.parent.getFlatGeneratorInfo());
		this.buttons.add(this.theButton = new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 28, 150, 16, 0, StatCollector.translateToLocal("createWorld.customize.presets.select")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 28, 150, 16, 1, StatCollector.translateToLocal("gui.cancel")));
		this.func_82296_g();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		this.theTextField.mouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if(!this.theTextField.textboxKeyTyped(par1, par2)) {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			if(this.func_82293_j()) {
				this.parent.setFlatGeneratorInfo(this.theTextField.getText());
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
			}
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, 80, 0xBB000000);
		this.drawRect(0, 80, width, 81, 0xDD000000);
		this.drawRect(0, 81, width, height-38, 0x88000000);
		this.drawRect(0, height-38, width, height-37, 0xDD000000);
		this.drawRect(0, height-37, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.theFlatPresetsListSlot.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.field_82300_d, this.width / 2, 8, 16777215);
		this.drawString(this.fontRenderer, this.field_82308_m, 50, 30, 10526880);
		this.drawString(this.fontRenderer, this.field_82306_n, 50, 70, 10526880);
		this.theTextField.drawTextBox();
	}

	@Override
	public void updateScreen() {
		this.theTextField.updateCursorCounter();
		super.updateScreen();
	}

	public void func_82296_g() {
		boolean flag = this.func_82293_j();
		this.theButton.enabled = flag;
	}

	private boolean func_82293_j() {
		return this.theFlatPresetsListSlot.field_82459_a > -1 && this.theFlatPresetsListSlot.field_82459_a < presets.size() || this.theTextField.getText().length() > 1;
	}

	public static void addPresetNoFeatures(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, FlatLayerInfo ... par3ArrayOfFlatLayerInfo) {
		addPreset(par0Str, par1, par2BiomeGenBase, (List)null, par3ArrayOfFlatLayerInfo);
	}

	public static void addPreset(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, List par3List, FlatLayerInfo ... par4ArrayOfFlatLayerInfo) {
		FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
		for(int j = par4ArrayOfFlatLayerInfo.length - 1; j >= 0; --j) {
			flatgeneratorinfo.getFlatLayers().add(par4ArrayOfFlatLayerInfo[j]);
		}
		flatgeneratorinfo.setBiome(par2BiomeGenBase.biomeID);
		flatgeneratorinfo.func_82645_d();
		if(par3List != null) {
			Iterator iterator = par3List.iterator();
			while(iterator.hasNext()) {
				String s1 = (String)iterator.next();
				flatgeneratorinfo.getWorldFeatures().put(s1, new HashMap());
			}
		}
		presets.add(new GuiFlatPresetsItem(par1, par0Str, flatgeneratorinfo.toString()));
	}

	public static RenderItem getPresetIconRenderer() {
		return renderItem;
	}

	public static List getPresets() {
		return presets;
	}

	public static GuiNovamenuPresetsListSlot func_82292_a(GuiNovamenuFlatPresets par0GuiFlatPresets) {
		return par0GuiFlatPresets.theFlatPresetsListSlot;
	}

	public static GuiTextField func_82298_b(GuiNovamenuFlatPresets par0GuiFlatPresets) {
		return par0GuiFlatPresets.theTextField;
	}

	static {
		addPreset("Classic Flat", Block.grass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(2, Block.dirt.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPreset("Tunnelers\' Dream", Block.stone.blockID, BiomeGenBase.extremeHills, Arrays.asList(new String[] {"biome_1", "dungeon", "decoration", "stronghold", "mineshaft"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(230, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPreset("Water World", Block.waterMoving.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(90, Block.waterStill.blockID), new FlatLayerInfo(5, Block.sand.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(5, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPreset("Overworld", Block.tallGrass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPreset("Snowy Kingdom", Block.snow.blockID, BiomeGenBase.icePlains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.snow.blockID), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPreset("Bottomless Pit", Item.feather.itemID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(2, Block.cobblestone.blockID)});
		addPreset("Desert", Block.sand.blockID, BiomeGenBase.desert, Arrays.asList(new String[] {"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"}), new FlatLayerInfo[] {new FlatLayerInfo(8, Block.sand.blockID), new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
		addPresetNoFeatures("Redstone Ready", Item.redstone.itemID, BiomeGenBase.desert, new FlatLayerInfo[] {new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
	}

	protected static class GuiFlatPresetsItem {
		
		public int iconId;
		public String presetName;
		public String presetData;

		public GuiFlatPresetsItem(int par1, String par2Str, String par3Str) {
			this.iconId = par1;
			this.presetName = par2Str;
			this.presetData = par3Str;
		}
		
	}

}
