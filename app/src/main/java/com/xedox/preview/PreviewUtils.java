package com.xedox.preview;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

// this code now not worked !
// source - https://qna.habr.com/q/1379588

public class PreviewUtils {

    /** Метод генерирующий байткод */
    private static byte[] generateBytecode(String className) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // Объявление генерируемого класса
        cw.visit(
                Opcodes.V1_9,
                Opcodes.ACC_PUBLIC,
                className,
                null,
                "java/lang/Object",
                new String[] {});

        // Конструктор генерируемого класса
        MethodVisitor con = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        con.visitCode();
        con.visitVarInsn(Opcodes.ALOAD, 0);
        con.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        con.visitInsn(Opcodes.RETURN);
        con.visitMaxs(1, 1);

        // Метод генерируемого класса
        // выводящий его имя
        MethodVisitor mv =
                cw.visitMethod(Opcodes.ACC_PUBLIC, "introduceYourself", "()V", null, null);

        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("I am ");
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/lang/Object",
                "getClass",
                "()Ljava/lang/Class;",
                false);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                false);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "toString",
                "()Ljava/lang/String;",
                false);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 1);

        cw.visitEnd();

        return cw.toByteArray();
    }
}
