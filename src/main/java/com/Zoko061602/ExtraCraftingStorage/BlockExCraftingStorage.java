package com.Zoko061602.ExtraCraftingStorage;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import appeng.block.crafting.BlockCraftingUnit;
import appeng.client.render.blocks.RenderBlockCraftingCPU;
import appeng.core.features.AEFeature;
import appeng.core.sync.GuiBridge;
import appeng.tile.crafting.TileCraftingTile;
import appeng.util.Platform;
import extracells.Extracells;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockExCraftingStorage extends BlockCraftingUnit {

	private static IIcon[] icons = new IIcon[10];

	public BlockExCraftingStorage(){
		this.setTileEntity(TileExCraftingStorage.class);
        this.setCreativeTab(Extracells.ModTab());
		this.hasSubtypes = true;
		this.setFeature(EnumSet.of( AEFeature.CraftingCPU));
		this.setBlockName("blockCraftingStorage");
	}

	@Override
	protected RenderBlockCraftingCPU<? extends BlockCraftingUnit, ? extends TileCraftingTile> getRenderer(){
		return new RenderBlockCraftingCPU<BlockExCraftingStorage, TileCraftingTile>();
	}

	@Override
	public boolean onActivated( World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ ){
		TileCraftingTile tg = this.getTileEntity( w, x, y, z);
		if(tg != null && !p.isSneaking() && tg.isFormed() && tg.isActive() ){
			if(Platform.isClient()) return true;
			Platform.openGUI(p, tg, ForgeDirection.getOrientation(side), GuiBridge.GUI_CRAFTING_CPU);
			return true;
		}

		return false;
	}

	@Override
	public void setRenderStateByMeta( int itemDamage ){
		IIcon front = this.getIcon( ForgeDirection.SOUTH.ordinal(), itemDamage );
		IIcon other = this.getIcon( ForgeDirection.NORTH.ordinal(), itemDamage );
		this.getRendererInstance().setTemporaryRenderIcons( other, other, front, other, other, other );
	}

	@Override
	public void breakBlock( World w, int x, int y, int z, Block a, int b ){
		TileCraftingTile cp = this.getTileEntity( w, x, y, z );
		if(cp != null)cp.breakCluster();


		super.breakBlock( w, x, y, z, a, b );
	}

	@Override
	public String getUnlocalizedName( ItemStack is ){
		if( is.getItemDamage() == 1 ) return "tile.appliedenergistics2.BlockCraftingAcceleratdsfor";

		return this.getItemUnlocalizedName( is );
	}

	protected String getItemUnlocalizedName( ItemStack is ){
		return super.getUnlocalizedName( is );
	}

	@Override
	public void onNeighborBlockChange( World w, int x, int y, int z, Block junk ){
		TileCraftingTile cp = this.getTileEntity( w, x, y, z );
		if( cp != null )
			cp.updateMultiBlock();
	}

	@Override
	public int getDamageValue( World w, int x, int y, int z ){
		int meta = w.getBlockMetadata( x, y, z );
		return this.damageDropped( meta );
	}

	@Override
	public void registerBlockIcons(IIconRegister ir) {
		int j=256;
		for(int i=0;!(i==4);i++) {
			icons[2*i] = ir.registerIcon(ExtraCraftingStorage.ModID+":BlockCraftingStorage"+j+"k");
			icons[2*i+1] = ir.registerIcon(ExtraCraftingStorage.ModID+":BlockCraftingStorage"+j+"kFit");
			j*=4;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getCheckedSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i=0;!(i==4);i++)
		list.add(new ItemStack(item,1,i));

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
	ArrayList<ItemStack> r = new ArrayList<ItemStack>();
	if((world.getTileEntity(x, y, z)!=null)&(world.getTileEntity(x, y, z)instanceof TileExCraftingStorage))
	switch(((TileExCraftingStorage)world.getTileEntity(x, y, z)).getStorageBytes()) {
	case 256*1024: metadata=0;break;
	case 1024*1024: metadata=1;break;
	case 4096*1024: metadata=2;break;
	case 16384*1024: metadata=3;break;
	}

     r.add(new ItemStack(this,1,metadata));
		return r;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return getDrops(world, x, y, z, 0, 0).get(0);
	}


	@Override
	public IIcon getIcon(int side, int meta) {
		switch(meta&(~4)){
			default:
			case 0:return icons[0];
			case 1:return icons[2];
			case 2:return icons[4];
			case 3:return icons[6];
			case 8:return icons[1];
			case 1|8:return icons[3];
			case 2|8:return icons[5];
			case 3|8:return icons[7];
		}
	}

}
