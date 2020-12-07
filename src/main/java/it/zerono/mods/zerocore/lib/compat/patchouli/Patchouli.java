/*
 *
 * PatchouliSetup.java
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

package it.zerono.mods.zerocore.lib.compat.patchouli;

import it.zerono.mods.zerocore.internal.Log;
import it.zerono.mods.zerocore.lib.compat.Mods;
import it.zerono.mods.zerocore.lib.compat.patchouli.component.standardpage.Crafting;
import it.zerono.mods.zerocore.lib.compat.patchouli.component.standardpage.Multiblock;
import it.zerono.mods.zerocore.lib.compat.patchouli.component.standardpage.Smelting;
import it.zerono.mods.zerocore.lib.compat.patchouli.component.standardpage.Spotlight;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.patchouli.client.book.template.BookTemplate;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PatchouliSetup {

    @SubscribeEvent
    public static void onClientInit(final FMLClientSetupEvent event) {

        Mods.PATCHOULI.ifPresent(() -> () -> {

            Log.LOGGER.info("Initializing Patchouli custom templates...");

            BookTemplate.registerComponent("zcsptMultiblock", Multiblock.class);
            BookTemplate.registerComponent("zcsptSpotlight", Spotlight.class);
            BookTemplate.registerComponent("zcsptCrafting", Crafting.class);
            BookTemplate.registerComponent("zcsptSmelting", Smelting.class);
        });
    }
}