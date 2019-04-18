package com.Zoko061602.ExtraCraftingStorage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid=ExtraCraftingStorage.ModID,name=ExtraCraftingStorage.ModID,version="1.0-rv3-beta10",dependencies="required-after:extracells;required-after:appliedenergistics2")
public class ExtraCraftingStorage {

	public static final String ModID = "ExtraCraftingStorage";

	public static Block blockCraftingstorage;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerBlock(blockCraftingstorage=new BlockExCraftingStorage(), ItemBlockExCraftingStorage.class, "blockExCraftingStorage");
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {
		Item  itemECstorageCell = GameRegistry.findItem("extracells", "storage.component");
		Block blockAECU = GameRegistry.findBlock("appliedenergistics2", "tile.BlockCraftingUnit");

		for(int i=0;!(i==4);i++)
		GameRegistry.addShapelessRecipe(new ItemStack(blockCraftingstorage, 1, i),new Object[] {new ItemStack(blockAECU,1,0),new ItemStack(itemECstorageCell,1,i)});

	}

}
