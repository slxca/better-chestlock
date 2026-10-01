package com.slxca.betterChestlock.block;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ChestContainerAdapters {

    public static final WorldlyContainer EMPTY = new EmptyWorldlyContainer();

    private ChestContainerAdapters() {
    }

    public static WorldlyContainer wrap(Container chest) {
        return new ChestWorldlyContainer(chest);
    }

    private static class EmptyWorldlyContainer extends SimpleContainer implements WorldlyContainer {

        EmptyWorldlyContainer() {
            super(0);
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return false;
        }
    }

    private static class ChestWorldlyContainer implements WorldlyContainer {

        private final Container chest;

        ChestWorldlyContainer(Container chest) {
            this.chest = chest;
        }

        @Override
        public int getContainerSize() {
            return this.chest.getContainerSize();
        }

        @Override
        public boolean isEmpty() {
            return this.chest.isEmpty();
        }

        @Override
        public ItemStack getItem(int slot) {
            return this.chest.getItem(slot);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            return this.chest.removeItem(slot, amount);
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            return this.chest.removeItemNoUpdate(slot);
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            this.chest.setItem(slot, stack);
        }

        @Override
        public void setChanged() {
            this.chest.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return this.chest.stillValid(player);
        }

        @Override
        public void clearContent() {
            this.chest.clearContent();
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            int[] slots = new int[this.chest.getContainerSize()];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
            return slots;
        }

        @Override
        public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return this.chest.canPlaceItem(slot, stack);
        }

        @Override
        public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
            return true;
        }
    }
}