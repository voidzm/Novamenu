package com.voidzm.novamenu.util;

import net.minecraft.client.Minecraft;

public class NovamenuFontUtils {

	public static String injectWrapping(String par1Str, int par2) {
		int j = sizeStringToWidth(par1Str, par2);
		if(par1Str.length() <= j) {
			return par1Str;
		}
		else {
			String s1 = par1Str.substring(0, j);
			char c0 = par1Str.charAt(j);
			boolean flag = c0 == 32 || c0 == 10;
			String s2 = getFormatFromString(s1) + par1Str.substring(j + (flag ? 1 : 0));
			return s1 + "\n" + injectWrapping(s2, par2);
		}
	}

	private static int sizeStringToWidth(String par1Str, int par2) {
		int j = par1Str.length();
		int k = 0;
		int l = 0;
		int i1 = -1;
		for(boolean flag = false; l < j; ++l) {
			char c0 = par1Str.charAt(l);
			switch(c0) {
				case 10:
					--l;
					break;
				case 167:
					if(l < j - 1) {
						++l;
						char c1 = par1Str.charAt(l);
						if(c1 != 108 && c1 != 76) {
							if(c1 == 114 || c1 == 82 || isFormatColor(c1)) {
								flag = false;
							}
						}
						else {
							flag = true;
						}
					}
					break;
				case 32:
					i1 = l;
				default:
					k += Minecraft.getMinecraft().fontRenderer.getCharWidth(c0);
					if(flag) {
						++k;
					}
			}
			if(c0 == 10) {
				++l;
				i1 = l;
				break;
			}
			if(k > par2) {
				break;
			}
		}
		return l != j && i1 != -1 && i1 < l ? i1 : l;
	}

	private static boolean isFormatColor(char par0) {
		return par0 >= 48 && par0 <= 57 || par0 >= 97 && par0 <= 102 || par0 >= 65 && par0 <= 70;
	}

	private static boolean isFormatSpecial(char par0) {
		return par0 >= 107 && par0 <= 111 || par0 >= 75 && par0 <= 79 || par0 == 114 || par0 == 82;
	}

	private static String getFormatFromString(String par0Str) {
		String s1 = "";
		int i = -1;
		int j = par0Str.length();
		while((i = par0Str.indexOf(167, i + 1)) != -1) {
			if(i < j - 1) {
				char c0 = par0Str.charAt(i + 1);
				if(isFormatColor(c0)) {
					s1 = "\u00a7" + c0;
				}
				else if(isFormatSpecial(c0)) {
					s1 = s1 + "\u00a7" + c0;
				}
			}
		}
		return s1;
	}
	
}
