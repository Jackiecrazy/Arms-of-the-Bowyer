// ==================================================================
// This file is part of Render Player API.
//
// Render Player API is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// Render Player API is distributed in the hope that it will be
// useful, but WITHOUT ANY WARRANTY; without even the implied
// warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// See the GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License and the GNU General Public License along with Render
// Player API. If not, see <http://www.gnu.org/licenses/>.
// ==================================================================

package api.player.render;

import java.util.*;

import org.objectweb.asm.*;

public final class RenderPlayerConstructorVisitor extends MethodVisitor
{
	private final boolean isObfuscated;
	private final Map<String, Stack<String>> constructorReplacements;

	public RenderPlayerConstructorVisitor(MethodVisitor paramMethodVisitor, boolean isObfuscated, Map<String, Stack<String>> constructorReplacements)
	{
		super(262144, paramMethodVisitor);
		this.isObfuscated = isObfuscated;
		this.constructorReplacements = constructorReplacements;
	}

	public void visitTypeInsn(int opcode, String type)
	{
		if(opcode == Opcodes.NEW && constructorReplacements != null && constructorReplacements.containsKey(type))
		{
			Stack<String> replacementOwnerList = constructorReplacements.get(type);
			if(!replacementOwnerList.isEmpty())
				type = replacementOwnerList.peek();
			int typeSeparatorIndex = type.indexOf(":");
			if(typeSeparatorIndex > 0)
				type = type.substring(0, typeSeparatorIndex);
		}
		super.visitTypeInsn(opcode, type);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc)
	{
		if(name.equals("<init>") && constructorReplacements != null && constructorReplacements.containsKey(owner))
		{
			Stack<String> replacementOwnerList = constructorReplacements.get(owner);
			if(!replacementOwnerList.isEmpty())
				owner = replacementOwnerList.pop();
			int typeSeparatorIndex = owner.indexOf(":");
			if(typeSeparatorIndex > 0)
			{
				mv.visitLdcInsn(owner.substring(typeSeparatorIndex + 1));
				owner = owner.substring(0, typeSeparatorIndex);
				int resultSeparatorIndex = desc.indexOf(")");
				desc = desc.substring(0, resultSeparatorIndex) + "Ljava/lang/String;" + desc.substring(resultSeparatorIndex);
			}
		}
		super.visitMethodInsn(opcode, owner, name, desc);
		if(name.equals("<init>") && owner.equals(isObfuscated ? "boh" : "net/minecraft/client/renderer/entity/RendererLivingEntity"))
		{
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/render/RenderPlayerAPI", "create", "(Lapi/player/render/IRenderPlayerAPI;)Lapi/player/render/RenderPlayerAPI;");
			mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "bop" : "net/minecraft/client/renderer/entity/RenderPlayer", "renderPlayerAPI", "Lapi/player/render/RenderPlayerAPI;");

			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/render/RenderPlayerAPI", "beforeLocalConstructing", "(Lapi/player/render/IRenderPlayerAPI;)V");
		}
	}

	public void visitInsn(int opcode)
	{
		if(opcode == Opcodes.RETURN)
		{
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/render/RenderPlayerAPI", "afterLocalConstructing", "(Lapi/player/render/IRenderPlayerAPI;)V");
		}
		super.visitInsn(opcode);
	}
}
