/*
 * This file is part of the CarpetPatcher project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  WenDavid and contributors
 *
 * CarpetPatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CarpetPatcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CarpetPatcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package club.mcams.carpetpatcher.impl.pingSampleListInjection;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.AdjustableModifyConstantNode;
import com.bawnorton.mixinsquared.api.MixinAnnotationAdjuster;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

public class PingSampleListMixinAdjuster implements MixinAnnotationAdjuster {
    @Override
    public AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotation) {
        //#if MC>=11904
        if ("carpet.mixins.MinecraftServer_pingPlayerSampleLimit".equals(mixinClassName) && annotation.is(ModifyConstant.class)) {
            AdjustableModifyConstantNode wrapOpNode = annotation.as(AdjustableModifyConstantNode.class);
            List<String> methods = wrapOpNode.getMethod();
            if ("tickServer".equals(methods.get(0))) {
                methods.set(0, "createMetadataPlayers");
                wrapOpNode.setMethod(methods);
                return wrapOpNode;
            }
        }
        //#endif
        return annotation;
    }
}
