package eg.foureg.freedraw.model

import eg.foureg.freedraw.data.Shape
import java.util.*

/**
 * Model to hold actions that can undo
 */
class UndoActionModel {
    val actionsStack = Stack<Shape>()
}