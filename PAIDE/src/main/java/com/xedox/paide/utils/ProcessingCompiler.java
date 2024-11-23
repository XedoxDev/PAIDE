package com.xedox.paide.utils;

import org.objectweb.asm.*;

public class ProcessingCompiler {

    public static byte[] compileProcessingCode(String code) throws Exception {
 
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(
                Opcodes.V17,
                Opcodes.ACC_PUBLIC,
                "MySketch",
                null,
                "processing/core/PApplet",
                null);

        MethodVisitor mvSetup = cw.visitMethod(Opcodes.ACC_PUBLIC, "setup", "()V", null, null);
        mvSetup.visitCode();
        mvSetup.visitInsn(Opcodes.RETURN);
        mvSetup.visitMaxs(0, 0);
        mvSetup.visitEnd();

        MethodVisitor mvDraw = cw.visitMethod(Opcodes.ACC_PUBLIC, "draw", "()V", null, null);
        mvDraw.visitCode();
        mvDraw.visitInsn(Opcodes.RETURN);
        mvDraw.visitMaxs(0, 0); 
        mvDraw.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}
