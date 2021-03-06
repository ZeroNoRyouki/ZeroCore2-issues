/*
 *
 * CachedRender.java
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

package it.zerono.mods.zerocore.lib.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class CachedRender {

    public void paint(final double x, final double y, final double z) {

        if (null == this._list) {

            this._list = new DisplayList();
            this._list.beginList();
            this.buildRender();
            this._list.endList();
        }

        this.onBeginPainting(x, y, z);
        ModRenderHelper.bindTexture(this.getTexture());
        this._list.play();
        this.onEndPainting(x, y, z);
    }

    public void invalidate() {
        this._list = null;
    }

    //region internals

    protected abstract ResourceLocation getTexture();

    protected abstract void buildRender();

    protected void onBeginPainting(final double x, final double y, final double z) {

        RenderSystem.pushMatrix();
        RenderSystem.translated(x, y, z);
    }

    protected void onEndPainting(final double x, final double y, final double z) {

        RenderSystem.translated(-x, -y, -z);
        RenderSystem.popMatrix();
    }

    private DisplayList _list;
}
