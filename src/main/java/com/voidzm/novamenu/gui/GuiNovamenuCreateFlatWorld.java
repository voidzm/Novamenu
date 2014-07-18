//**
//**  GuiNovamenuCreateFlatWorld.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.FlatGeneratorInfo;

public class GuiNovamenuCreateFlatWorld extends GuiNovamenuScreen {

	private static RenderItem renderItem = new RenderItem();
	private final GuiNovamenuCreateWorld parent;
	private FlatGeneratorInfo genInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
	private String customizationTitle;
	private String layerMaterialLabel;
	private String heightLabel;
	private GuiNovamenuFlatWorldListSlot listSlot;
	private GuiButtonTransparent buttonRemoveLayer;
	
	public GuiNovamenuCreateFlatWorld(GuiNovamenuCreateWorld createWorld, String string) {
		this.parent = createWorld;
		this.imageTick = createWorld.imageTick;
		this.setFlatGeneratorInfo(string);
	}

	public String getFlatGeneratorInfo() {
		return this.genInfo.toString();
	}

	public void setFlatGeneratorInfo(String par1Str) {
		this.genInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par1Str);
	}
	
	@Override
	public void initGui() {
		this.buttons.clear();
		this.customizationTitle = StatCollector.translateToLocal("createWorld.customize.flat.title");
		this.layerMaterialLabel = StatCollector.translateToLocal("createWorld.customize.flat.tile");
		this.heightLabel = StatCollector.translateToLocal("createWorld.customize.flat.height");
		this.listSlot = new GuiNovamenuFlatWorldListSlot(this);
		this.listSlot.boxshift = 1;
		this.buttons.add(this.buttonRemoveLayer = new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 52, 150, 20, 4, StatCollector.translateToLocal("createWorld.customize.flat.removeLayer")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 28, 150, 16, 0, StatCollector.translateToLocal("gui.done")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 52, 150, 16, 5, StatCollector.translateToLocal("createWorld.customize.presets")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 28, 150, 16, 1, StatCollector.translateToLocal("gui.cancel")));
		this.genInfo.func_82645_d();
		this.func_82270_g();
	}

	@Override
	public void buttonEvent(int id) {
		int i = this.genInfo.getFlatLayers().size() - this.listSlot.field_82454_a - 1;
		switch(id) {
		case 0:
			this.parent.genOptions = this.getFlatGeneratorInfo();
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		case 4:
			if(this.func_82272_i()) {
				this.genInfo.getFlatLayers().remove(i);
				this.listSlot.field_82454_a = Math.min(this.listSlot.field_82454_a, this.genInfo.getFlatLayers().size() - 1);
			}
			break;
		case 5:
			this.mc.displayGuiScreen(new GuiNovamenuFlatPresets(this));
		}
		this.genInfo.func_82645_d();
		this.func_82270_g();
	}
	
	public void func_82270_g() {
		boolean flag = this.func_82272_i();
		this.buttonRemoveLayer.enabled = flag;
	}

	private boolean func_82272_i() {
		return this.listSlot.field_82454_a > -1 && this.listSlot.field_82454_a < this.genInfo.getFlatLayers().size();
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, 43, 0xBB000000);
		this.drawRect(0, 43, width, 44, 0xDD000000);
		this.drawRect(0, 44, width, height-61, 0x88000000);
		this.drawRect(0, height-61, width, height-60, 0xDD000000);
		this.drawRect(0, height-60, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.listSlot.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.customizationTitle, this.width / 2, 8, 16777215);
		int k = this.width / 2 - 92 - 16;
		this.drawString(this.fontRenderer, this.layerMaterialLabel, k, 32, 16777215);
		this.drawString(this.fontRenderer, this.heightLabel, k + 2 + 213 - this.fontRenderer.getStringWidth(this.heightLabel), 32, 16777215);
	}

	public static RenderItem getRenderItem(){
		return renderItem;
	}

	public static FlatGeneratorInfo func_82271_a(GuiNovamenuCreateFlatWorld par0GuiCreateFlatWorld) {
		return par0GuiCreateFlatWorld.genInfo;
	}
}
