package org.pitest.mutationtest.engine.gregor.mutators;

/*
 * The remove conditionals mutator will remove all conditionals statements
 *  such that the guarded statements always execute
 */

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.Context;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;

public enum DeleteConditionalMutator implements MethodMutatorFactory {

  DELETE_CONDITIONALS_MUTATOR;

  public MethodVisitor create(final Context context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new DeleteConditionalMethodVisitor(this, context, methodVisitor);
  }

  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  public String getName() {
    return name();
  }

}

class DeleteConditionalMethodVisitor extends MethodVisitor {

  private static final String        DESCRIPTION = "deleted conditional";
  private final Context              context;
  private final MethodMutatorFactory factory;

  public DeleteConditionalMethodVisitor(final MethodMutatorFactory factory,
      final Context context, final MethodVisitor delegateMethodVisitor) {
    super(Opcodes.ASM4, delegateMethodVisitor);
    this.context = context;
    this.factory = factory;
  }

  @Override
  public void visitJumpInsn(final int opcode, final Label label) {

    if (canMutate(opcode)) {
      final MutationIdentifier newId = this.context.registerMutation(
          this.factory, DESCRIPTION);

      if (this.context.shouldMutate(newId)) {
        emptyStack(opcode);
        super.visitJumpInsn(Opcodes.GOTO, label);
      } else {
        this.mv.visitJumpInsn(opcode, label);
      }
    } else {
      this.mv.visitJumpInsn(opcode, label);
    }

  }

  private void emptyStack(final int opcode) {
    switch (opcode) {
    case Opcodes.IF_ICMPNE:
    case Opcodes.IF_ICMPEQ:
    case Opcodes.IF_ACMPEQ:
    case Opcodes.IF_ACMPNE:
    case Opcodes.IF_ICMPGE:
    case Opcodes.IF_ICMPGT:
    case Opcodes.IF_ICMPLE:
    case Opcodes.IF_ICMPLT:
      super.visitInsn(Opcodes.POP2);
      break;
    default:
      super.visitInsn(Opcodes.POP);
    }

  }

  private boolean canMutate(final int opcode) {
    switch (opcode) {
    case Opcodes.IFEQ:
    case Opcodes.IFNE:
    case Opcodes.IFLE:
    case Opcodes.IFGE:
    case Opcodes.IFGT:
    case Opcodes.IFLT:   	
    case Opcodes.IFNONNULL:
    case Opcodes.IFNULL:
    case Opcodes.IF_ICMPNE:
    case Opcodes.IF_ICMPEQ:
    case Opcodes.IF_ACMPEQ:
    case Opcodes.IF_ACMPNE:
    case Opcodes.IF_ICMPGE:
    case Opcodes.IF_ICMPGT:
    case Opcodes.IF_ICMPLE:
    case Opcodes.IF_ICMPLT:
      return true;
    default:
      return false;
    }
  }

}
