package eg.foureg.freedraw.common.actor

import eg.foureg.freedraw.common.Logs

object ActorMessageDispatcher {
    const val TAG = "ActorMessageDispatcher"

    fun registerActor(actor: Actor) {
        if(!actors.contains(actor)) {
            actors.add(actor)
            Logs.debug(TAG, "registerActor() | ${actor::class.java.name}")
        }
    }

    fun unregisterActor(actor: Actor) {
        Logs.debug(TAG, "unregisterActor() | ${actor::class.java.name}")
        actors.remove(actor)
    }

    fun <T : Any> sendMessage(actorClass : Class<T>, message: ActorMessage) : Boolean {
        for (act in actors) {
            if(act::class.java.name == actorClass.name) {
                act.handleMessage(message)
                Logs.debug(TAG, "sendMessage() | Send message to ${actorClass.name}")
                return true
            }
        }

        return false
    }

    private val actors = ArrayList<Actor>()
}