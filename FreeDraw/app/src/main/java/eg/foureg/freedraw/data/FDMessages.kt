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