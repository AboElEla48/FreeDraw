package eg.foureg.freedraw.data

import eg.foureg.freedraw.common.actor.ActorMessage

const val messageBackToFragmentID  : Int = 10
val messageBackToFragmentMap = HashMap<Int,Any>()
val messageBackToFragment = ActorMessage(messageBackToFragmentID, messageBackToFragmentMap)

const val messageNavigateToBoardsListFragmentID  : Int = 20
val messageNavigateToBoardsListFragmentMap = HashMap<Int,Any>()
val messageNavigateToBoardsListFragment = ActorMessage(messageNavigateToBoardsListFragmentID, messageNavigateToBoardsListFragmentMap)

const val messageNavigateToEditBoardFragmentID  : Int = 30
val messageNavigateToEditBoardFragmentMap = HashMap<Int,Any>()
const val messageNavigateToEditBoardParam = 31
val messageNavigateToEditBoardFragment = ActorMessage(messageNavigateToEditBoardFragmentID, messageNavigateToEditBoardFragmentMap)

const val messageEditBoardMoveShapeID  : Int = 40
val messageEditBoardMoveShapeMap = HashMap<Int,Any>()
const val messageEditBoardMoveShapeParam = 41
val messageEditBoardMoveShape = ActorMessage(messageEditBoardMoveShapeID, messageEditBoardMoveShapeMap)

const val messageEditBoardFinishMoveShapeID  : Int = 50
val messageEditBoardFinishMoveShapeMap = HashMap<Int,Any>()
val messageEditBoardFinishMoveShape = ActorMessage(messageEditBoardFinishMoveShapeID, messageEditBoardFinishMoveShapeMap)

const val messageEditBoardInvalidateDrawID  : Int = 60
val messageEditBoardInvalidateDrawMap = HashMap<Int,Any>()
val messageEditBoardInvalidateDraw = ActorMessage(messageEditBoardInvalidateDrawID, messageEditBoardInvalidateDrawMap)

const val messageEditBoardSetBoardNameID  : Int = 70
val messageEditBoardSetBoardNameMap = HashMap<Int,Any>()
const val messageEditBoardSetBoardNameParam = 71
val messageEditBoardSetBoardName = ActorMessage(messageEditBoardSetBoardNameID, messageEditBoardSetBoardNameMap)

const val messageEditBoardClearID  : Int = 80
val messageEditBoardClearMap = HashMap<Int,Any>()
val messageEditBoardClear = ActorMessage(messageEditBoardClearID, messageEditBoardClearMap)

const val messageDeleteBoardsFromListingID  : Int = 90
val messageDeleteBoardsFromListingMap = HashMap<Int,Any>()
val messageDeleteBoardsFromListing = ActorMessage(messageDeleteBoardsFromListingID, messageDeleteBoardsFromListingMap)

const val messageInsertBoardID  : Int = 100
val messageInsertBoardMap = HashMap<Int,Any>()
val messageInsertBoard = ActorMessage(messageInsertBoardID, messageInsertBoardMap)