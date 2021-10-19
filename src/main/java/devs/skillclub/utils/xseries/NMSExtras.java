
package devs.skillclub.utils.xseries;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static devs.skillclub.utils.xseries.ReflectionUtils.*;


public final class NMSExtras {
    public static final MethodHandle EXP_PACKET;
    public static final MethodHandle ENTITY_PACKET;
    public static final MethodHandle WORLD_HANDLE, ENTITY_HANDLE;
    public static final MethodHandle LIGHTNING_ENTITY;
    public static final MethodHandle VEC3D;
    public static final MethodHandle PACKET_PLAY_OUT_OPEN_SIGN_EDITOR, PACKET_PLAY_OUT_BLOCK_CHANGE;

    public static final MethodHandle ANIMATION_PACKET, ANIMATION_TYPE, ANIMATION_ENTITY_ID;

    public static final MethodHandle PLAY_OUT_MULTI_BLOCK_CHANGE_PACKET, MULTI_BLOCK_CHANGE_INFO, CHUNK_WRAPPER_SET, CHUNK_WRAPPER, SHORTS_OR_INFO, SET_BLOCK_DATA;

    public static final MethodHandle BLOCK_POSITION;
    public static final MethodHandle PLAY_BLOCK_ACTION;
    public static final MethodHandle GET_BUKKIT_ENTITY;
    public static final MethodHandle GET_BLOCK_TYPE;
    public static final MethodHandle GET_BLOCK, GET_IBLOCK_DATA, SANITIZE_LINES, TILE_ENTITY_SIGN, TILE_ENTITY_SIGN__GET_UPDATE_PACKET, TILE_ENTITY_SIGN__SET_LINE;

    public static final Class<?>
            MULTI_BLOCK_CHANGE_INFO_CLASS = null,
            BLOCK_DATA = getNMSClass("world.level.block.state", "IBlockData");

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle expPacket = null;
        MethodHandle entityPacket = null;
        MethodHandle worldHandle = null, entityHandle = null;
        MethodHandle lightning = null;
        MethodHandle vec3D = null;
        MethodHandle signEditorPacket = null, packetPlayOutBlockChange = null;

        MethodHandle animationPacket = null;
        MethodHandle animationType = null;
        MethodHandle animationEntityId = null;

        MethodHandle getBukkitEntity = null;
        MethodHandle blockPosition = null;
        MethodHandle playBlockAction = null;
        MethodHandle getBlockType = null;
        MethodHandle getBlock = null;
        MethodHandle getIBlockData = null;
        MethodHandle sanitizeLines = null;
        MethodHandle tileEntitySign = null, tileEntitySign_getUpdatePacket = null, tileEntitySign_setLine = null;

        MethodHandle playOutMultiBlockChange = null, multiBlockChangeInfo = null, chunkWrapper = null, chunkWrapperSet = null,
                shortsOrInfo = null, setBlockData = null;

        try {
            Class<?> nmsEntityType = getNMSClass("world.entity", "EntityTypes");
            Class<?> nmsEntity = getNMSClass("world.entity", "Entity");
            Class<?> craftEntity = getCraftClass("entity.CraftEntity");
            Class<?> nmsVec3D = getNMSClass("world.phys", "Vec3D");
            Class<?> world = getNMSClass("world.level", "World");
            Class<?> signOpenPacket = getNMSClass("network.protocol.game", "PacketPlayOutOpenSignEditor");
            Class<?> packetPlayOutBlockChangeClass = getNMSClass("network.protocol.game", "PacketPlayOutBlockChange");
            Class<?> CraftMagicNumbers = getCraftClass("util.CraftMagicNumbers");
            Class<?> CraftSign = getCraftClass("block.CraftSign");
            Class<?> IChatBaseComponent = getNMSClass("network.chat", "IChatBaseComponent");
            Class<?> TileEntitySign = getNMSClass("world.level.block.entity", "TileEntitySign");
            Class<?> PacketPlayOutTileEntityData = getNMSClass("network.protocol.game", "PacketPlayOutTileEntityData");

            getBukkitEntity = lookup.findVirtual(nmsEntity, "getBukkitEntity", MethodType.methodType(craftEntity));
            entityHandle = lookup.findVirtual(craftEntity, "getHandle", MethodType.methodType(nmsEntity));



            expPacket = lookup.findConstructor(getNMSClass("network.protocol.game", "PacketPlayOutExperience"), MethodType.methodType(void.class, float.class,
                    int.class, int.class));

            if (!supports(16)) {
                entityPacket = lookup.findConstructor(getNMSClass("PacketPlayOutSpawnEntityWeather"), MethodType.methodType(void.class,
                        nmsEntity));
            } else {
                vec3D = lookup.findConstructor(nmsVec3D, MethodType.methodType(void.class,
                        double.class, double.class, double.class));

                entityPacket = lookup.findConstructor(getNMSClass("network.protocol.game", "PacketPlayOutSpawnEntity"), MethodType.methodType(void.class,
                        int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, nmsEntityType, int.class, nmsVec3D));
            }

            worldHandle = lookup.findVirtual(getCraftClass("CraftWorld"), "getHandle", MethodType.methodType(
                    getNMSClass("server.level", "WorldServer")));

            if (!supports(16)) {
                lightning = lookup.findConstructor(getNMSClass("world.entity", "EntityLightning"), MethodType.methodType(void.class,

                        world, double.class, double.class, double.class, boolean.class, boolean.class));
            } else {
                lightning = lookup.findConstructor(getNMSClass("world.entity", "EntityLightning"), MethodType.methodType(void.class,

                        nmsEntityType, world));
            }


            Class<?> playOutMultiBlockChangeClass = getNMSClass("network.protocol.game", "PacketPlayOutMultiBlockChange");
            Class<?> chunkCoordIntPairClass = getNMSClass("world.level", "ChunkCoordIntPair");
            try {


















                if (supports(16)) {


                } else chunkWrapper = lookup.findConstructor(chunkCoordIntPairClass, MethodType.methodType(void.class, int.class, int.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Class<?> animation = getNMSClass("network.protocol.game", "PacketPlayOutAnimation");
            animationPacket = lookup.findConstructor(animation,
                    supports(17) ? MethodType.methodType(void.class, nmsEntity, int.class) : MethodType.methodType(void.class));

            if (!supports(17)) {
                Field field = animation.getDeclaredField("a");
                field.setAccessible(true);
                animationEntityId = lookup.unreflectSetter(field);
                field = animation.getDeclaredField("b");
                field.setAccessible(true);
                animationType = lookup.unreflectSetter(field);
            }


            Class<?> blockPos = getNMSClass("core", "BlockPosition");
            Class<?> block = getNMSClass("world.level.block", "Block");
            blockPosition = lookup.findConstructor(blockPos, MethodType.methodType(void.class, double.class, double.class, double.class));
            getBlockType = lookup.findVirtual(world, "getType", MethodType.methodType(BLOCK_DATA, blockPos));
            getBlock = lookup.findVirtual(BLOCK_DATA, "getBlock", MethodType.methodType(block));
            playBlockAction = lookup.findVirtual(world, "playBlockAction", MethodType.methodType(void.class, blockPos, block, int.class, int.class));

            signEditorPacket = lookup.findConstructor(signOpenPacket, MethodType.methodType(void.class, blockPos));
            if (supports(17)) {
                packetPlayOutBlockChange = lookup.findConstructor(packetPlayOutBlockChangeClass, MethodType.methodType(void.class, blockPos, BLOCK_DATA));
                getIBlockData = lookup.findStatic(CraftMagicNumbers, "getBlock", MethodType.methodType(BLOCK_DATA, Material.class, byte.class));
                sanitizeLines = lookup.findStatic(CraftSign, "SANITIZE_LINES", MethodType.methodType(toArrayClass(IChatBaseComponent), String[].class));

                tileEntitySign = lookup.findConstructor(TileEntitySign, MethodType.methodType(void.class, blockPos, BLOCK_DATA));
                tileEntitySign_getUpdatePacket = lookup.findVirtual(TileEntitySign, "getUpdatePacket", MethodType.methodType(PacketPlayOutTileEntityData));
                tileEntitySign_setLine = lookup.findVirtual(TileEntitySign, "a", MethodType.methodType(void.class, int.class, IChatBaseComponent, IChatBaseComponent));
            }
        } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        EXP_PACKET = expPacket;
        ENTITY_PACKET = entityPacket;
        WORLD_HANDLE = worldHandle;
        ENTITY_HANDLE = entityHandle;
        LIGHTNING_ENTITY = lightning;
        VEC3D = vec3D;
        PACKET_PLAY_OUT_OPEN_SIGN_EDITOR = signEditorPacket;
        PACKET_PLAY_OUT_BLOCK_CHANGE = packetPlayOutBlockChange;

        ANIMATION_PACKET = animationPacket;
        ANIMATION_TYPE = animationType;
        ANIMATION_ENTITY_ID = animationEntityId;

        BLOCK_POSITION = blockPosition;
        PLAY_BLOCK_ACTION = playBlockAction;
        GET_BLOCK_TYPE = getBlockType;
        GET_BLOCK = getBlock;
        GET_IBLOCK_DATA = getIBlockData;
        SANITIZE_LINES = sanitizeLines;
        TILE_ENTITY_SIGN = tileEntitySign;
        TILE_ENTITY_SIGN__GET_UPDATE_PACKET = tileEntitySign_getUpdatePacket;
        TILE_ENTITY_SIGN__SET_LINE = tileEntitySign_setLine;

        GET_BUKKIT_ENTITY = getBukkitEntity;
        PLAY_OUT_MULTI_BLOCK_CHANGE_PACKET = playOutMultiBlockChange;
        MULTI_BLOCK_CHANGE_INFO = multiBlockChangeInfo;
        CHUNK_WRAPPER = chunkWrapper;
        CHUNK_WRAPPER_SET = chunkWrapperSet;
        SHORTS_OR_INFO = shortsOrInfo;
        SET_BLOCK_DATA = setBlockData;
    }

    private NMSExtras() { }

    public static void setExp(Player player, float bar, int lvl, int exp) {
        try {
            Object packet = EXP_PACKET.invoke(bar, lvl, exp);
            sendPacket(player, packet);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void lightning(Player player, Location location, boolean sound) {
        lightning(Collections.singletonList(player), location, sound);
    }


    public static void lightning(Collection<Player> players, Location location, boolean sound) {
        try {
            Object world = WORLD_HANDLE.invoke(location.getWorld());

            if (!supports(16)) {
                // I don't know what the isEffect and isSilent params are used for.

                Object lightningBolt = LIGHTNING_ENTITY.invoke(world, location.getX(), location.getY(), location.getZ(), false, false);
                Object packet = ENTITY_PACKET.invoke(lightningBolt);

                for (Player player : players) {
                    if (sound) XSound.ENTITY_LIGHTNING_BOLT_THUNDER.play(player);
                    sendPacket(player, packet);
                }
            } else {
                Class<?> nmsEntityType = getNMSClass("world.entity", "EntityTypes");

                Object lightningType = nmsEntityType.getField(supports(17) ? "U" : "LIGHTNING_BOLT").get(nmsEntityType);
                Object lightningBolt = LIGHTNING_ENTITY.invoke(lightningType, world);
                Object lightningBoltID = lightningBolt.getClass().getMethod("getId").invoke(lightningBolt);
                Object lightningBoltUUID = lightningBolt.getClass().getMethod("getUniqueID").invoke(lightningBolt);
                Object vec3D = VEC3D.invoke(0D, 0D, 0D);
                Object packet = ENTITY_PACKET.invoke(lightningBoltID, lightningBoltUUID, location.getX(), location.getY(), location.getZ(), 0F, 0F, lightningType, 0, vec3D);

                for (Player player : players) {
                    if (sound) XSound.ENTITY_LIGHTNING_BOLT_THUNDER.play(player);
                    sendPacket(player, packet);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void animation(Collection<? extends Player> players, LivingEntity entity, Animation animation) {
        try {
            // https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29
            Object packet;
            if (supports(17)) packet = ANIMATION_PACKET.invoke(ENTITY_HANDLE.invoke(entity), animation.ordinal());
            else {
                packet = ANIMATION_PACKET.invoke();
                ANIMATION_TYPE.invoke(packet, animation.ordinal());
                ANIMATION_ENTITY_ID.invoke(packet, entity.getEntityId());
            }

            for (Player player : players) sendPacket(player, packet);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void chest(Block chest, boolean open) {
        Location location = chest.getLocation();
        try {
            Object world = WORLD_HANDLE.invoke(location.getWorld());
            Object position = BLOCK_POSITION.invoke(location.getX(), location.getY(), location.getZ());
            Object block = GET_BLOCK.invoke(GET_BLOCK_TYPE.invoke(world, position));
            PLAY_BLOCK_ACTION.invoke(world, position, block, 1, open ? 1 : 0);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    @Deprecated
    protected static void sendBlockChange(Player player, Chunk chunk, Map<WorldlessBlockWrapper, Object> blocks) {
        try {
            Object packet = PLAY_OUT_MULTI_BLOCK_CHANGE_PACKET.invoke();

            if (supports(16)) {
                Object wrapper = CHUNK_WRAPPER.invoke(chunk.getX(), chunk.getZ());
                CHUNK_WRAPPER_SET.invoke(wrapper);

                Object dataArray = Array.newInstance(BLOCK_DATA, blocks.size());
                Object shortArray = Array.newInstance(short.class, blocks.size());

                int i = 0;
                for (Map.Entry<WorldlessBlockWrapper, Object> entry : blocks.entrySet()) {
                    Block loc = entry.getKey().block;
                    int x = loc.getX() & 15;
                    int y = loc.getY() & 15;
                    int z = loc.getZ() & 15;
                    i++;
                }

                SHORTS_OR_INFO.invoke(packet, shortArray);
                SET_BLOCK_DATA.invoke(packet, dataArray);
            } else {
                Object wrapper = CHUNK_WRAPPER.invoke(chunk.getX(), chunk.getZ());
                CHUNK_WRAPPER_SET.invoke(wrapper);

                Object array = Array.newInstance(MULTI_BLOCK_CHANGE_INFO_CLASS, blocks.size());
                int i = 0;
                for (Map.Entry<WorldlessBlockWrapper, Object> entry : blocks.entrySet()) {
                    Block loc = entry.getKey().block;
                    int x = loc.getX() & 15;
                    int z = loc.getZ() & 15;
                    i++;
                }

                SHORTS_OR_INFO.invoke(packet, array);
            }

            sendPacketSync(player, packet);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    public static void openSign(Player player, String[] lines) {
        try {
            Location loc = player.getLocation();
            Object position = BLOCK_POSITION.invoke(loc.getBlockX(), 1, loc.getBlockY());
            Object signBlockData = GET_IBLOCK_DATA.invoke(XMaterial.OAK_SIGN, (byte) 0);
            Object blockChangePacket = PACKET_PLAY_OUT_BLOCK_CHANGE.invoke(position, signBlockData);

            Object components = SANITIZE_LINES.invoke((Object[]) lines);
            Object tileSign = TILE_ENTITY_SIGN.invoke(position, signBlockData);
            for (int i = 0; i < lines.length; i++) {
                Object component = Array.get(components, i);
                TILE_ENTITY_SIGN__SET_LINE.invoke(tileSign, i, component, component);
            }
            Object signLinesUpdatePacket = TILE_ENTITY_SIGN__GET_UPDATE_PACKET.invoke(tileSign);

            Object signPacket = PACKET_PLAY_OUT_OPEN_SIGN_EDITOR.invoke(position);

            sendPacket(player, blockChangePacket, signLinesUpdatePacket, signPacket);
        } catch (Throwable x) {
            x.printStackTrace();
        }
    }


    public enum Animation {
        SWING_MAIN_ARM, HURT, LEAVE_BED, SWING_OFF_HAND, CRITICAL_EFFECT, MAGIC_CRITICAL_EFFECT;
    }

    public static class WorldlessBlockWrapper {
        public final Block block;

        public WorldlessBlockWrapper(Block block) {this.block = block;}

        @Override
        public int hashCode() {
            return (block.getY() + block.getZ() * 31) * 31 + block.getX();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Block)) return false;

            Block other = (Block) obj;
            return block.getX() == other.getX()
                    && block.getY() == other.getY()
                    && block.getZ() == other.getZ();
        }
    }
}
