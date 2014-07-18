package com.voidzm.novamenu.gui;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

import com.voidzm.novamenu.util.SorterNovamenuStatsItem;

public class GuiNovamenuStatsItemSlot extends GuiNovamenuStatsSlot {

	private GuiNovamenuStats parent;
	
	public GuiNovamenuStatsItemSlot(GuiNovamenuStats screen) {
		super(screen);
		this.parent = screen;
		this.list = new ArrayList();
		Iterator iterator = StatList.itemStats.iterator();
		while(iterator.hasNext()) {
			StatCrafting statcrafting = (StatCrafting)iterator.next();
			boolean flag = false;
			int i = statcrafting.getItemID();
			if(GuiNovamenuStats.getStatsFileWriter(screen).writeStat(statcrafting) > 0) {
				flag = true;
			}
			else if(StatList.objectBreakStats[i] != null && GuiNovamenuStats.getStatsFileWriter(parent).writeStat(StatList.objectBreakStats[i]) > 0) {
				flag = true;
			}
			else if(StatList.objectCraftStats[i] != null && GuiNovamenuStats.getStatsFileWriter(parent).writeStat(StatList.objectCraftStats[i]) > 0) {
				flag = true;
			}
			if(flag) {
				this.list.add(statcrafting);
			}
		}
		this.comparator = new SorterNovamenuStatsItem(this, screen);
	}

	protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator) {
		super.func_77222_a(par1, par2, par3Tessellator);
		if(this.intG == 0) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 115 - 18 + 1, par2 + 1 + 1, 72, 18);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 115 - 18, par2 + 1, 72, 18);
		}
		if(this.intG == 1) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 165 - 18 + 1, par2 + 1 + 1, 18, 18);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 165 - 18, par2 + 1, 18, 18);
		}
		if(this.intG == 2) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 215 - 18 + 1, par2 + 1 + 1, 36, 18);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 215 - 18, par2 + 1, 36, 18);
		}
	}


	@Override
	protected String func_77258_c(int i) {
		return i == 1 ? "stat.crafted" : (i == 2 ? "stat.used" : "stat.depleted");
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
		StatCrafting statcrafting = this.func_77257_d(par1);
		int i1 = statcrafting.getItemID();
		GuiNovamenuStats.drawItemSprite(this.parent, par2 + 40, par3, i1);
		this.func_77260_a((StatCrafting)StatList.objectBreakStats[i1], par2 + 115, par3, par1 % 2 == 0);
		this.func_77260_a((StatCrafting)StatList.objectCraftStats[i1], par2 + 165, par3, par1 % 2 == 0);
		this.func_77260_a(statcrafting, par2 + 215, par3, par1 % 2 == 0);
	}

}
