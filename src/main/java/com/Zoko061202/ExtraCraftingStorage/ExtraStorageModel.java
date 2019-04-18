package com.Zoko061202.ExtraCraftingStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import appeng.block.crafting.BlockCraftingUnit;
import appeng.client.render.cablebus.CubeBuilder;
import appeng.core.AppEng;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class ExtraStorageModel implements IModel {

	private final static ResourceLocation RING_CORNER = texture("ring_corner");
	private final static ResourceLocation RING_SIDE_HOR = texture("ring_side_hor");
	private final static ResourceLocation RING_SIDE_VER = texture("ring_side_ver");
	private final static ResourceLocation UNIT_BASE = texture("unit_base");
	private final static ResourceLocation LIGHT_BASE = texture("light_base");
	private final static ResourceLocation STORAGE_256K_LIGHT = textureEX("storage_256k_light");
	private final static ResourceLocation STORAGE_1024K_LIGHT = textureEX("storage_1024k_light");
	private final static ResourceLocation STORAGE_4096K_LIGHT = textureEX("storage_4096k_light");
	private final static ResourceLocation STORAGE_16384K_LIGHT = textureEX("storage_16384k_light");

	private final BlockCraftingUnit.CraftingUnitType type;

	ExtraStorageModel(BlockCraftingUnit.CraftingUnitType type){
		this.type = type;
	}

	@Override
	public Collection<ResourceLocation> getDependencies(){
		return Collections.emptyList();
	}

	@Override
	public Collection<ResourceLocation> getTextures(){
		return ImmutableList.of( RING_CORNER, RING_SIDE_HOR, RING_SIDE_VER, UNIT_BASE, LIGHT_BASE, STORAGE_256K_LIGHT, STORAGE_1024K_LIGHT,
				STORAGE_4096K_LIGHT, STORAGE_16384K_LIGHT);
	}

	@Override
	public IBakedModel bake( IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		TextureAtlasSprite ringCorner = bakedTextureGetter.apply( RING_CORNER );
		TextureAtlasSprite ringSideHor = bakedTextureGetter.apply( RING_SIDE_HOR );
		TextureAtlasSprite ringSideVer = bakedTextureGetter.apply( RING_SIDE_VER );

		switch(this.type){
			case STORAGE_1K:
			case STORAGE_4K:
			case STORAGE_16K:
			case STORAGE_64K:
				return new LightBakedModel( format, ringCorner, ringSideHor, ringSideVer, bakedTextureGetter
						.apply(LIGHT_BASE), getLightTexture(bakedTextureGetter, this.type));
			default:
				throw new IllegalArgumentException( "Unsupported crafting unit type: " + this.type );
		}
	}

	private static TextureAtlasSprite getLightTexture( Function<ResourceLocation, TextureAtlasSprite> textureGetter, BlockCraftingUnit.CraftingUnitType type ){
		switch( type ){
			case STORAGE_1K:
				return textureGetter.apply( STORAGE_256K_LIGHT );
			case STORAGE_4K:
				return textureGetter.apply( STORAGE_1024K_LIGHT );
			case STORAGE_16K:
				return textureGetter.apply( STORAGE_4096K_LIGHT );
			case STORAGE_64K:
				return textureGetter.apply( STORAGE_16384K_LIGHT );
			default:
				throw new IllegalArgumentException( "Crafting unit type " + type + " does not use a light texture." );
		}
	}

	@Override
	public IModelState getDefaultState(){
		return TRSRTransformation.identity();
	}

	private static ResourceLocation texture( String name ){
		return new ResourceLocation(AppEng.MOD_ID, "blocks/crafting/" + name);
	}

	private static ResourceLocation textureEX(String name){
		return new ResourceLocation(ExtraCraftingStorage.ModID, "blocks/" + name);
	}

	class LightBakedModel extends CraftingCubeBakedModel{

		private final TextureAtlasSprite baseTexture;

		private final TextureAtlasSprite lightTexture;

		LightBakedModel( VertexFormat format, TextureAtlasSprite ringCorner, TextureAtlasSprite ringHor, TextureAtlasSprite ringVer, TextureAtlasSprite baseTexture, TextureAtlasSprite lightTexture )
		{
			super( format, ringCorner, ringHor, ringVer );
			this.baseTexture = baseTexture;
			this.lightTexture = lightTexture;
		}

		@Override
		protected void addInnerCube( EnumFacing facing, IBlockState state, CubeBuilder builder, float x1, float y1, float z1, float x2, float y2, float z2 )
		{
			builder.setTexture( this.baseTexture );
			builder.addCube( x1, y1, z1, x2, y2, z2 );

			boolean powered = state.getValue( BlockCraftingUnit.POWERED );
			builder.setRenderFullBright( powered );
			builder.setTexture( this.lightTexture );
			builder.addCube( x1, y1, z1, x2, y2, z2 );
		}
	}

}
