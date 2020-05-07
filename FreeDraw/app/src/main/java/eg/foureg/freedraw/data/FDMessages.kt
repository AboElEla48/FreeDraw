package eg.foureg.freedraw.data

import eg.foureg.freedraw.common.actor.ActorMessage

const val messageBackToFragmentID  : Int = 10
val messageBackToFragmentMap = HashMap<Int,Any>()
val messageBackToFragment = ActorMessage(messageBackToFragmentID, messageBackToFragmentMap)

const val messageNavigateToBoardsListFragmentID  : Int = 20
val messageNavigateToBoardsListFragmentMap = HashMap<Int,Any>()
val messageNavigateToBoardsListFragment = ActorMessage(messageNavigateToBoardsListFragmentID, messageNavigateToBoardsListFragmentMap)