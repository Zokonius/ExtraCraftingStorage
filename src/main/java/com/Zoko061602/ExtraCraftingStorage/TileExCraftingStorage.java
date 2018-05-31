package com.Zoko061602.ExtraCraftingStorage;

import appeng.tile.crafting.TileCraftingStorageTile;
import appeng.tile.crafting.TileCraftingTile;
import net.minecraft.item.ItemStack;

public class TileExCraftingStorage extends TileCraftingStorageTile {

	private static final int KILO_SCALAR = 1024;


	@Override
	protected ItemStack getItemFromTile( final Object obj ){
		final int storage = ( (TileCraftingTile) obj ).getStorageBytes() / KILO_SCALAR;

		switch(storage){
			case 256:  return new ItemStack(ExtraCraftingStorage.blockCraftingstorage, 1, 0);
			case 1024: return new ItemStack(ExtraCraftingStorage.blockCraftingstorage, 1, 2);
			case 4096: return new ItemStack(ExtraCraftingStorage.blockCraftingstorage, 1, 4);
			case 16384: return new ItemStack(ExtraCraftingStorage.blockCraftingstorage, 1, 6);
		}

		return super.getItemFromTile(obj);
	}

	@Override
	public int getStorageBytes(){
		if(this.worldObj == null || this.notLoaded()) return 0;

		switch(this.blockMetadata&3){
		default:
			case 0: return 256*1024;
			case 1: return 1024*1024;
			case 2: return 4096*1024;
			case 3: return 16384*1024;
		}
	}




}
