package eg.foureg.freedraw.common.actor

interface Actor {
    fun handleMessage(message: ActorMessage)
}