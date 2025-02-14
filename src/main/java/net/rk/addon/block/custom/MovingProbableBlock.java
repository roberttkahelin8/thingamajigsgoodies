package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.TickPriority;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.item.TGItems;
import net.rk.thingamajigs.block.TBlocks;
import net.rk.addon.util.Utilities;

import java.util.List;
import java.util.stream.Stream;

public class MovingProbableBlock extends Block{
    public static IntegerProperty MOVES = IntegerProperty.create("moves",0,32);

    public MovingProbableBlock(Properties properties){
        super(properties.noCollission().noLootTable());
        this.registerDefaultState(this.defaultBlockState().setValue(MOVES,0));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsgoodies.moving_probable_block.desc")
                .withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOVES);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos,this,10,TickPriority.EXTREMELY_LOW);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState normBS = state;

        if(level.getRandom().nextInt(20) >= 18){
            if(level.getBlockState(pos.north()).is(Blocks.AIR)){
                level.setBlock(pos.north(),state,3);

                level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                updateState(level,normBS,pos.north());
            }
        }
        else if (level.getRandom().nextInt(20) == 17){
            if(level.getBlockState(pos.east()).is(Blocks.AIR)){
                level.setBlock(pos.east(),state,3);

                level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                updateState(level,normBS,pos.east());
            }
        }
        else if (level.getRandom().nextInt(20) == 16){
            if(level.getBlockState(pos.west()).is(Blocks.AIR)){
                level.setBlock(pos.west(),state,3);

                level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                updateState(level,normBS,pos.west());
            }
        }
        else if(level.getRandom().nextInt(20) <= 15){
            if(level.getBlockState(pos.south()).is(Blocks.AIR)){
                level.setBlock(pos.south(),state,3);

                level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                updateState(level,normBS,pos.south());
            }
        }

        level.scheduleTick(pos,this,3,TickPriority.EXTREMELY_LOW);
    }

    // note that there are a lot of chance stuff involved and this may result in less than stellar rewards
    public void updateState(Level lvl, BlockState bs, BlockPos bp){
        int magicNum = lvl.getRandom().nextInt(42); // unintentional random magic number

        if(lvl.getRandom().nextInt(100) <= magicNum){ // bad luck = no more moves
            lvl.setBlock(bp,Blocks.AIR.defaultBlockState(),3);
            lvl.playSound(null,bp,SoundEvents.CHICKEN_EGG,SoundSource.BLOCKS,1.0f,1.0f);
            generateReward(lvl,bs,bp);
            return;
        }

        if(lvl.getBlockState(bp).getValue(MOVES) < 30){
            lvl.setBlock(bp,bs.setValue(MOVES,bs.getValue(MOVES) + 1),3);
            lvl.playSound(null,bp,
                    SoundEvents.CRAFTER_CRAFT, SoundSource.BLOCKS,1.0f,1.0f);
        }
        else{
            lvl.setBlock(bp,Blocks.AIR.defaultBlockState(),3);
            lvl.playSound(null,bp,SoundEvents.CHICKEN_EGG,SoundSource.BLOCKS,1.0f,1.0f);
            generateReward(lvl,bs,bp);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(MOVES,0);
    }

    // quite simple yet large and complex in terms of run time but shouldn't ruin computers or servers
    public void generateReward(Level lvl, BlockState bs, BlockPos bp){
        List<Item> itemsTier28 = List.of(TGItems.PURIFYING_NUGGET.get(),Items.ENCHANTED_GOLDEN_APPLE,Items.EMERALD_BLOCK,Items.ECHO_SHARD,Items.DIAMOND_BLOCK,Items.TOTEM_OF_UNDYING);
        List<Item> itemsTier27 = List.of(Items.DIAMOND,Items.EMERALD,Items.GOLDEN_APPLE,Items.IRON_BLOCK);
        List<Item> itemsTier26 = List.of(Items.IRON_INGOT,Items.GOLD_INGOT,Items.APPLE,Items.COOKED_BEEF,Items.COOKIE,Items.GLOW_BERRIES,Items.HONEY_BOTTLE);
        List<Item> itemsTier25 = List.of(Items.BUCKET,Items.COD_BUCKET,Items.MILK_BUCKET,Items.SALMON_BUCKET,Items.LAVA_BUCKET,Items.POWDER_SNOW_BUCKET);

        List<Item> itemsTier0 = List.of(Items.DIRT,Items.STICK,TBlocks.POOP.asItem(),TBlocks.BRAMBLE.asItem());

        List<Item> weaponsChoice = List.of(Items.NETHERITE_SWORD,Items.TRIDENT,Items.OAK_SIGN,Items.STICK);

        RandomSource rs = lvl.getRandom();

        ItemStack tier28 = new ItemStack(itemsTier28.get(rs.nextInt(itemsTier28.size())));
        ItemStack tier27 = new ItemStack(itemsTier27.get(rs.nextInt(itemsTier27.size())));
        ItemStack tier26 = new ItemStack(itemsTier26.get(rs.nextInt(itemsTier26.size())));
        ItemStack tier25 = new ItemStack(itemsTier25.get(rs.nextInt(itemsTier25.size())));




        ItemStack tier0 = new ItemStack(itemsTier0.get(rs.nextInt(itemsTier0.size())));

        // enchant weapon (for enemy spawn) randomly based on tag
        ItemStack weapon = new ItemStack(weaponsChoice.get(rs.nextInt(weaponsChoice.size())));

        List<Holder<Enchantment>> eHolderList = List.of(
                Utilities.getEnchantmentHolderFromKeyStatic(lvl,Enchantments.FIRE_ASPECT),
                Utilities.getEnchantmentHolderFromKeyStatic(lvl,Enchantments.SHARPNESS),
                Utilities.getEnchantmentHolderFromKeyStatic(lvl,Enchantments.KNOCKBACK),
                Utilities.getEnchantmentHolderFromKeyStatic(lvl,Enchantments.SMITE),
                Utilities.getEnchantmentHolderFromKeyStatic(lvl,Enchantments.SWEEPING_EDGE)
        );

        Holder<Enchantment> holder = eHolderList.get(rs.nextInt(eHolderList.size()));
        int l = Mth.nextInt(lvl.random,1,3);

        weapon.enchant(holder,l);



        if(bs.getValue(MOVES) >= 28){
            popResource(lvl,bp,tier28);
        }
        else if(bs.getValue(MOVES) == 27){
            popResource(lvl,bp,tier27);
        }
        else if(bs.getValue(MOVES) == 26){
            popResource(lvl,bp,tier26);
        }
        else if(bs.getValue(MOVES) >= 20 && bs.getValue(MOVES) <= 25){
            popResource(lvl,bp,tier25);
        }
        else if(bs.getValue(MOVES) >= 15 && bs.getValue(MOVES) <= 19){
            List<Block> blks1 = List.of(Blocks.OAK_LOG,
                    Blocks.CAKE,Blocks.CALCITE,
                    Blocks.DEEPSLATE,Blocks.FIRE,
                    Blocks.PACKED_ICE,Blocks.SPONGE,
                    Blocks.RED_CONCRETE,Blocks.END_STONE,
                    Blocks.INFESTED_STONE,Blocks.WATER,Blocks.CAMPFIRE);

            BlockState rBs1 = blks1.get(rs.nextInt(blks1.size())).defaultBlockState();

            if(rs.nextInt(100) <= 70){
                if(!lvl.getBlockState(bp.below()).is(Blocks.BEDROCK)){
                    lvl.setBlock(bp.below(),rBs1,3);
                }
                else{
                    lvl.setBlock(bp,rBs1,3);
                }
                float f1 = rs.nextFloat();
                if (f1 < 0.9f){
                    f1 = 0.9f;
                }
                lvl.playSound(lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),5,true),bp,
                        SoundEvents.AMBIENT_CAVE.value(),SoundSource.BLOCKS,1.0f,f1);
            }
            else{
                if(!lvl.getBlockState(bp.below()).is(Blocks.BEDROCK)){
                    lvl.destroyBlock(bp.below(),false,null);
                    lvl.setBlock(bp.below(),Blocks.LAVA.defaultBlockState(),3);
                    Blaze blz1 = new Blaze(EntityType.BLAZE,lvl);
                    blz1.setSilent(true);
                    blz1.addEffect(new MobEffectInstance(
                            MobEffects.HEALTH_BOOST,
                            1000,
                            20,
                            true,
                            false,
                            false));
                    blz1.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            1000,
                            2,
                            true,
                            false,
                            false));
                    blz1.setPos(bp.getX(),bp.getY(),bp.getZ());
                    lvl.addFreshEntity(blz1);
                }
                else{
                    lvl.setBlock(bp,Blocks.LAVA.defaultBlockState(),3);
                    failureMessage(lvl,bp);
                }
            }
        }
        else if(bs.getValue(MOVES) >= 10 && bs.getValue(MOVES) <= 14){
            if(rs.nextInt(100) <= 2){
                PrimedTnt tnt = new PrimedTnt(lvl,bp.getX(),bp.getY(),bp.getZ(),null);
                tnt.setBlockState(TGBlocks.MOVING_PROBABLE_BLOCK.get().defaultBlockState());
                tnt.move(MoverType.PISTON,new Vec3(0,10,0));
                tnt.setFuse(7);
                lvl.addFreshEntity(tnt);
            }
            else if(rs.nextInt(100) <= 15){
                Stream<BlockState> bses = lvl.getBlockStates(new AABB(1,1,1,1,10,1));
                if(bses.toList().stream().allMatch(BlockStateBase::isAir)){
                    FallingBlockEntity.fall(lvl,bp.above(7),Blocks.SNOW_BLOCK.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(5),Blocks.SNOW_BLOCK.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(9),Blocks.CARVED_PUMPKIN.defaultBlockState());
                }
                else{
                    popResource(lvl,bp,new ItemStack(Items.SNOWBALL));
                    lvl.setBlock(bp,Blocks.STONE_SLAB.defaultBlockState(),3);
                    lvl.playSound(lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),5,true),
                            bp,SoundEvents.VAULT_INSERT_ITEM_FAIL,SoundSource.BLOCKS,1.0f,1.0f);
                    failureMessage(lvl,bp);
                }
            }
            else{
                if(lvl.getBlockState(bp.above()).is(Blocks.AIR) && lvl.getBlockState(bp.above().above()).is(Blocks.AIR) && lvl.getBlockState(bp.above().above().above()).is(Blocks.AIR)){
                    lvl.setBlock(bp.above().above().above(),Blocks.BLACK_WOOL.defaultBlockState(),3);
                    lvl.setBlock(bp.above().above(),Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING,Direction.UP),3);
                    lvl.setBlock(bp.above(),Blocks.REDSTONE_BLOCK.defaultBlockState(),3);
                }
                else{
                    failureMessage(lvl,bp);
                }
            }
        }
        else if(bs.getValue(MOVES) >= 5 && bs.getValue(MOVES) <= 9){
            if(rs.nextInt(100) <= 3){
                Stream<BlockState> bses2 = lvl.getBlockStates(new AABB(1,1,1,1,16,1));
                if(bses2.toList().stream().allMatch(BlockStateBase::isAir)){
                    FallingBlockEntity.fall(lvl,bp.above(2),Blocks.RED_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(4),Blocks.ORANGE_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(6),Blocks.YELLOW_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(8),Blocks.LIME_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(10),Blocks.BLUE_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(12),Blocks.PURPLE_WOOL.defaultBlockState());
                    FallingBlockEntity.fall(lvl,bp.above(14),TBlocks.GOLD_BOWLING_PIN.get().defaultBlockState());
                }
                else{
                    failureMessage(lvl,bp);
                }
            }
            else if(rs.nextInt(100) <= 10){
                if(lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),10,true) != null){
                    List<Holder<MobEffect>> effects = List.of(
                            MobEffects.WITHER,
                            MobEffects.WEAKNESS,
                            MobEffects.CONFUSION,
                            MobEffects.UNLUCK,
                            MobEffects.POISON,
                            MobEffects.MOVEMENT_SLOWDOWN,
                            MobEffects.DIG_SLOWDOWN);

                    lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),10,true)
                            .addEffect(new MobEffectInstance(
                                    effects.get(rs.nextInt(effects.size())),
                                    rs.nextInt(90,120),
                                    rs.nextInt(1,2),
                                    true,
                                    false,
                                    false));
                }
            }
            else{
                List<Item> itemsTier1 = List.of(Items.OAK_LOG,Items.BIRCH_LOG,Items.ANDESITE,Items.DIORITE,Items.GRANITE);
                ItemStack tier1 = new ItemStack(itemsTier1.get(rs.nextInt(itemsTier1.size())));
                popResource(lvl,bp,tier1);
            }
        }
        else{
            if(rs.nextInt(72) <= 5){
                lvl.explode(null,bp.getX(),bp.getY(),bp.getZ(),5, Level.ExplosionInteraction.NONE);
            }
            else if(rs.nextInt(100) <= 1){
                if(rs.nextInt(75) <= 35){
                    lvl.setBlock(bp,TGBlocks.MOVING_PROBABLE_BLOCK.get().defaultBlockState(),3);
                }
                else{
                    lvl.setBlock(bp,Blocks.STONE.defaultBlockState(),3);
                }
            }
            else if(rs.nextInt(50) <= 2){
                for(int lcount = 1; lcount < 5; lcount += 1){
                    LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT,lvl);
                    lb.setSilent(true);
                    if(rs.nextInt(100) <= 50){
                        lb.setPos(bp.getX() + rs.nextInt(lcount),bp.getY(),bp.getZ() + rs.nextInt(lcount));
                    }
                    else{
                        lb.setPos(bp.getX() - rs.nextInt(lcount),bp.getY(),bp.getZ() - rs.nextInt(lcount));
                    }
                    lvl.addFreshEntity(lb);
                }
            }
            else if(rs.nextInt(100) <= 3){
                if(!lvl.isClientSide){
                    if(rs.nextInt(100) <= 30){
                        Skeleton s1 = new Skeleton(EntityType.SKELETON,lvl);

                        s1.setItemSlot(EquipmentSlot.MAINHAND,weapon);
                        s1.setItemSlot(EquipmentSlot.HEAD,Items.DARK_OAK_FENCE_GATE.getDefaultInstance());
                        s1.setPos(bp.getX(),bp.getY(),bp.getZ());

                        s1.setDropChance(EquipmentSlot.HEAD,0.0f);
                        s1.setDropChance(EquipmentSlot.MAINHAND,0.0f);

                        List<Holder<MobEffect>> effects = List.of(MobEffects.INFESTED,MobEffects.OOZING,MobEffects.WEAVING,MobEffects.WIND_CHARGED);

                        s1.addEffect(new MobEffectInstance(
                                effects.get(rs.nextInt(effects.size())),
                                1000,
                                1,
                                true,
                                false,
                                false));

                        lvl.gameEvent(null,GameEvent.ENTITY_PLACE,bp);
                        lvl.addFreshEntity(s1);
                    }
                    else{
                        Zombie z1 = new Zombie(lvl);

                        z1.setItemSlot(EquipmentSlot.MAINHAND,weapon);
                        z1.setItemSlot(EquipmentSlot.HEAD,Items.CREEPER_HEAD.getDefaultInstance());
                        z1.setPos(bp.getX(),bp.getY(),bp.getZ());

                        z1.setDropChance(EquipmentSlot.HEAD,0.0f);
                        z1.setDropChance(EquipmentSlot.MAINHAND,0.0f);

                        lvl.gameEvent(null,GameEvent.ENTITY_PLACE,bp);
                        lvl.addFreshEntity(z1);
                    }
                }
            }
            else{
                popResource(lvl,bp,tier0);
            }
        }

        // bottom


    }

    private void failureMessage(Level lvl, BlockPos bp){
        if(lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),5,true) != null){
            lvl.getNearestPlayer(bp.getX(),bp.getY(),bp.getZ(),5,true)
                    .displayClientMessage(Component
                            .translatable("message.thingamajigsgoodies.moving_probable_block.no_fun"),true);
        }
    }
}
