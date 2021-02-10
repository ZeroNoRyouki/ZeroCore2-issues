/*
 *
 * ClearRecipesMessage.java
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

package it.zerono.mods.zerocore.internal.network;

import it.zerono.mods.zerocore.lib.network.AbstractModMessage;
import it.zerono.mods.zerocore.lib.recipe.ModRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClearRecipesMessage
        extends AbstractModMessage {

    /**
     * Construct the local message to be sent over the network.
     */
    public ClearRecipesMessage() {
    }

    /**
     * Construct the message from the data received from the network.
     * Read your payload from the {@link PacketBuffer} and store it locally for later processing.
     *
     * @param buffer the {@link PacketBuffer} containing the data received from the network.
     */
    public ClearRecipesMessage(PacketBuffer buffer) {
        super(buffer);
    }

    //region AbstractModMessage

    /**
     * Encode your data into the {@link PacketBuffer} so it could be sent on the network to the other side.
     *
     * @param buffer the {@link PacketBuffer} to encode your data into
     */
    @Override
    public void encodeTo(PacketBuffer buffer) {
    }

    /**
     * Process the data received from the network.
     *
     * @param messageContext context for {@link NetworkEvent}
     */
    @Override
    public void processMessage(final NetworkEvent.Context messageContext) {

        messageContext.enqueueWork(ModRecipeType::invalidate);
        messageContext.setPacketHandled(true);
    }

    //endregion
}