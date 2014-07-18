package com.voidzm.novamenu.util;

import java.util.Comparator;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

import com.voidzm.novamenu.gui.GuiNovamenuStats;
import com.voidzm.novamenu.gui.GuiNovamenuStatsItemSlot;

public class SorterNovamenuStatsItem implements Comparator {

	private GuiNovamenuStats parentGUI;

	private GuiNovamenuStatsItemSlot parentSlot;

	public SorterNovamenuStatsItem(GuiNovamenuStatsItemSlot par1GuiSlotStatsItem, GuiNovamenuStats par2GuiStats) {
		this.parentSlot = par1GuiSlotStatsItem;
		this.parentGUI = par2GuiStats;
	}

	public int func_78337_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting) {
		int i = par1StatCrafting.getItemID();
		int j = par2StatCrafting.getItemID();
		StatBase statbase = null;
		StatBase statbase1 = null;
		if(this.parentSlot.intJ == 0) {
			statbase = StatList.objectBreakStats[i];
			statbase1 = StatList.objectBreakStats[j];
		}
		else if(this.parentSlot.intJ == 1) {
			statbase = StatList.objectCraftStats[i];
			statbase1 = StatList.objectCraftStats[j];
		}
		else if(this.parentSlot.intJ == 2) {
			statbase = StatList.objectUseStats[i];
			statbase1 = StatList.objectUseStats[j];
		}
		if(statbase != null || statbase1 != null) {
			if(statbase == null) {
				return 1;
			}
			if(statbase1 == null) {
				return -1;
			}
			int k = GuiNovamenuStats.getStatsFileWriter(this.parentGUI).writeStat(statbase);
			int l = GuiNovamenuStats.getStatsFileWriter(this.parentGUI).writeStat(statbase1);
			if(k != l) {
				return (k - l) * this.parentSlot.intK;
			}
		}
		return i - j;
	}

	public int compare(Object par1Obj, Object par2Obj) {
		return this.func_78337_a((StatCrafting)par1Obj, (StatCrafting)par2Obj);
	}

}
