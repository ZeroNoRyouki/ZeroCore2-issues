/*
 *
 * LightOverlayColourAdapter.java
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

package it.zerono.mods.zerocore.lib.client.render.vertexuploader;

import it.zerono.mods.zerocore.lib.client.render.IVertexSource;
import it.zerono.mods.zerocore.lib.data.gfx.Colour;
import it.zerono.mods.zerocore.lib.data.gfx.LightMap;

import javax.annotation.Nullable;

public class LightOverlayColourAdapter implements ISourceAdapter {

    public LightOverlayColourAdapter() {

        this._overlay = this._light = new LightMap(0, 0);
        this._colour = Colour.WHITE;
    }

    //region ISourceAdapter

    @Nullable
    @Override
    public LightMap getLightMap(IVertexSource source) {
        return this._light;
    }

    @Nullable
    @Override
    public LightMap getOverlayMap(IVertexSource source) {
        return this._overlay;
    }

    @Nullable
    @Override
    public Colour getColour(IVertexSource source) {
        return this._colour;
    }

    //endregion
    //region internals

    private final LightMap _light;
    private final LightMap _overlay;
    private final Colour _colour;

    //endregion
}

