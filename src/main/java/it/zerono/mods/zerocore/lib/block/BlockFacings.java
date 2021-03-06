/*
 *
 * BlockFacings.java
 *
 * This file is part of Zero CORE 2 by ZeroNoRyouki, a Minecraft mod.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * DO NOT REMOVE OR EDIT THIS HEADER
 *
 */

package it.zerono.mods.zerocore.lib.block;

import it.zerono.mods.zerocore.lib.block.property.BlockFacingsProperty;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A general purpose class to track the state of all 6 faces of a block
 * <p>
 * Example usages:
 * - track which faces are exposed on the outside walls of a complex structure
 * - track which faces is connected to a face of a similar block
 */
public final class BlockFacings {

    public static final BlockFacings NONE;
    public static final BlockFacings ALL;
    public static final BlockFacings DOWN;
    public static final BlockFacings UP;
    public static final BlockFacings NORTH;
    public static final BlockFacings SOUTH;
    public static final BlockFacings WEST;
    public static final BlockFacings EAST;
    public static final BlockFacings VERTICAL;
    public static final BlockFacings HORIZONTAL;
    public static final BlockFacings AXIS_X;
    public static final BlockFacings AXIS_Y;
    public static final BlockFacings AXIS_Z;

    public static final BooleanProperty FACING_DOWN = BooleanProperty.create("downFacing");
    public static final BooleanProperty FACING_UP = BooleanProperty.create("upFacing");
    public static final BooleanProperty FACING_WEST = BooleanProperty.create("westFacing");
    public static final BooleanProperty FACING_EAST = BooleanProperty.create("eastFacing");
    public static final BooleanProperty FACING_NORTH = BooleanProperty.create("northFacing");
    public static final BooleanProperty FACING_SOUTH = BooleanProperty.create("southFacing");

    public byte value() {
        return this._value;
    }

    /**
     * Check if a specific face is "set"
     *
     * @param facing the face to check
     * @return true if the face is "set", false otherwise
     */
    public boolean isSet(final Direction facing) {
        return 0 != (this._value & (1 << facing.getIndex()));
    }

    /**
     * Check if any face is "set" except the specified face
     *
     * @param facing the face to exclude
     * @return true if any face is "set" with the exception of the specified one, false otherwise
     */
    public boolean except(final Direction facing) {
        return this.any() && !this.isSet(facing);
    }

    public boolean none() {
        return 0 == this._value;
    }

    public boolean any() {
        return 0 != this._value;
    }

    public boolean one() {
        return 1 == this.countFacesIf(true);
    }

    public boolean some() {
        return 0 != this._value;
    }

    public boolean all() {
        return 0x3f == this._value;
    }

    public boolean down() {
        return this.isSet(Direction.DOWN);
    }

    public boolean up() {
        return this.isSet(Direction.UP);
    }

    public boolean north() {
        return this.isSet(Direction.NORTH);
    }

    public boolean south() {
        return this.isSet(Direction.SOUTH);
    }

    public boolean west() {
        return this.isSet(Direction.WEST);
    }

    public boolean east() {
        return this.isSet(Direction.EAST);
    }

    public void ifSet(final Direction facing, final Runnable task) {

        if (this.isSet(facing)) {
            task.run();
        }
    }

    public void ifNotSet(final Direction facing, final Runnable task) {

        if (!this.isSet(facing)) {
            task.run();
        }
    }

    public void ifSet(final Direction facing, final Consumer<Direction> consumer) {

        if (this.isSet(facing)) {
            consumer.accept(facing);
        }
    }

    public void ifNotSet(final Direction facing, final Consumer<Direction> consumer) {

        if (!this.isSet(facing)) {
            consumer.accept(facing);
        }
    }

    public BlockState toBlockState(final BlockState state) {
        return state.with(FACING_DOWN, this.isSet(Direction.DOWN))
                .with(FACING_UP, this.isSet(Direction.UP))
                .with(FACING_WEST, this.isSet(Direction.WEST))
                .with(FACING_EAST, this.isSet(Direction.EAST))
                .with(FACING_NORTH, this.isSet(Direction.NORTH))
                .with(FACING_SOUTH, this.isSet(Direction.SOUTH));
    }

    /**
     * Return a BlockFacing object that describe the current facing with the given face set or unset
     *
     * @param facing the face to modify
     * @param value  the new value for the state of the face
     * @return a BlockFacing object
     */
    public BlockFacings set(final Direction facing, final boolean value) {

        byte newHash = this._value;

        if (value) {
            newHash |= (1 << facing.getIndex());
        } else {
            newHash &= ~(1 << facing.getIndex());
        }

        return BlockFacings.from(newHash);
    }

    /**
     * Count the number of faces that are in the required state
     *
     * @param areSet specify if you are looking for "set" faces (true) or not (false)
     * @return the number of faces found in the required state
     */
    public int countFacesIf(final boolean areSet) {

        final int checkFor = areSet ? 1 : 0;
        int mask = this._value;
        int faces = 0;

        for (int i = 0; i < 6; ++i, mask = mask >>> 1) {
            if ((mask & 1) == checkFor) {
                ++faces;
            }
        }

        return faces;
    }

    /**
     * Return a BlockFacingsProperty for the current facing
     *
     * @return a BlockFacingsProperty value
     */
    public BlockFacingsProperty toProperty() {

        for (final BlockFacingsProperty value : BlockFacingsProperty.values()) {
            if (value.getHash() == this._value) {
                return value;
            }
        }

        return BlockFacingsProperty.None;
    }

    /**
     * Offset the given BlockPos in all direction set in this object
     *
     * @param originalPosition the original position
     * @return the new position
     */
    public BlockPos offsetBlockPos(final BlockPos originalPosition) {

        int x = 0, y = 0, z = 0;

        for (Direction facing : Direction.values()) {
            if (this.isSet(facing)) {

                x += facing.getXOffset();
                y += facing.getYOffset();
                z += facing.getZOffset();
            }
        }

        return originalPosition.add(x, y, z);
    }

    /**
     * Return the first face that is in the required state
     *
     * @param isSet specify if you are looking for "set" faces (true) or not (false)
     * @return the first face that match the required state or null if no face is found
     */
    public Optional<Direction> firstIf(final boolean isSet) {

        for (Direction facing : Direction.values()) {
            if (isSet == this.isSet(facing)) {
                return Optional.of(facing);
            }
        }

        return Optional.empty();
    }

    /**
     * Return a BlockFacing object that describe the passed in state
     *
     * @param down  the state of the "down" face
     * @param up    the state of the "up" face
     * @param north the state of the "north" face
     * @param south the state of the "south" face
     * @param west  the state of the "west" face
     * @param east  the state of the "east" face
     * @return a BlockFacing object
     */
    public static BlockFacings from(final boolean down, final boolean up, final boolean north, final boolean south,
                                    final boolean west, final boolean east) {
        return BlockFacings.from(BlockFacings.computeHash(down, up, north, south, west, east));
    }

    /**
     * Return a BlockFacing object with the passed in {@link Direction} set to true
     *
     * @param directions the {@link Direction}s to set to true
     * @return a BlockFacing object
     */
    public static BlockFacings from(final Direction... directions) {

        final boolean[] facings = {false, false, false, false, false, false};

        for (final Direction direction : directions) {
            facings[direction.ordinal()] = true;
        }

        return from(facings);
    }

    /**
     * Return a BlockFacing object that describe the passed in state
     *
     * @param facings an array describing the state. the elements of the array must be filled in following the order in Direction.VALUES
     * @return a BlockFacing object
     */
    public static BlockFacings from(final boolean[] facings) {
        return BlockFacings.from(BlockFacings.computeHash(facings));
    }

    public static BlockFacings from(final Direction.Axis axis) {

        switch (axis) {

            default:
            case X:
                return AXIS_X;

            case Y:
                return AXIS_Y;

            case Z:
                return AXIS_Z;
        }
    }

    public static BlockFacings from(final Direction.Plane plane) {

        switch (plane) {

            default:
            case VERTICAL:
                return VERTICAL;

            case HORIZONTAL:
                return HORIZONTAL;
        }
    }

    static BlockFacings from(final Byte hash) {

        BlockFacings facings = BlockFacings.s_cache.get(hash);

        if (null == facings) {

            facings = new BlockFacings(hash);
            BlockFacings.s_cache.put(hash, facings);
        }

        return facings;
    }

    public static Byte computeHash(final boolean down, final boolean up, final boolean north, final boolean south,
                                   final boolean west, final boolean east) {

        byte hash = 0;

        if (down) {
            hash |= (1 << Direction.DOWN.getIndex());
        }

        if (up) {
            hash |= (1 << Direction.UP.getIndex());
        }

        if (north) {
            hash |= (1 << Direction.NORTH.getIndex());
        }

        if (south) {
            hash |= (1 << Direction.SOUTH.getIndex());
        }

        if (west) {
            hash |= (1 << Direction.WEST.getIndex());
        }

        if (east) {
            hash |= (1 << Direction.EAST.getIndex());
        }

        return hash;
    }

    //region Object

    @Override
    public String toString() {
        return BlockFacings.NONE == this ? "Facings: NONE" :
                String.format("Facings: %s%s%s%s%s%s",
                        this.isSet(Direction.DOWN) ? "DOWN " : "",
                        this.isSet(Direction.UP) ? "UP " : "",
                        this.isSet(Direction.NORTH) ? "NORTH " : "",
                        this.isSet(Direction.SOUTH) ? "SOUTH " : "",
                        this.isSet(Direction.WEST) ? "WEST " : "",
                        this.isSet(Direction.EAST) ? "EAST " : "");
    }

    @Override
    public int hashCode() {
        return this._value;
    }

    //endregion
    //region internals

    private BlockFacings(final byte value) {
        this._value = value;
    }

    static Byte computeHash(final boolean[] facings) {

        byte hash = 0;
        int len = facings.length;

        if (len > Direction.values().length) {
            throw new IllegalArgumentException("Invalid length of facings array");
        }

        for (int i = 0; i < len; ++i) {
            if (facings[i]) {
                hash |= (1 << i);
            }
        }

        return hash;
    }

    private byte _value;

    private static final Map<Byte, BlockFacings> s_cache;

    static {

        Byte hash;

        s_cache = new HashMap<>(12);

        hash = BlockFacings.computeHash(false, false, false, false, false, false);
        s_cache.put(hash, NONE = new BlockFacings(hash));

        hash = BlockFacings.computeHash(true, true, true, true, true, true);
        s_cache.put(hash, ALL = new BlockFacings(hash));

        hash = BlockFacings.computeHash(true, false, false, false, false, false);
        s_cache.put(hash, DOWN = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, true, false, false, false, false);
        s_cache.put(hash, UP = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, true, false, false, false);
        s_cache.put(hash, NORTH = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, false, true, false, false);
        s_cache.put(hash, SOUTH = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, false, false, true, false);
        s_cache.put(hash, WEST = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, false, false, false, true);
        s_cache.put(hash, EAST = new BlockFacings(hash));

        hash = BlockFacings.computeHash(true, true, false, false, false, false);
        s_cache.put(hash, VERTICAL = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, true, true, true, true);
        s_cache.put(hash, HORIZONTAL = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, false, false, true, true);
        s_cache.put(hash, AXIS_X = new BlockFacings(hash));

        hash = BlockFacings.computeHash(false, false, true, true, false, false);
        s_cache.put(hash, AXIS_Z = new BlockFacings(hash));

        AXIS_Y = VERTICAL;
    }

    //endregion
}
