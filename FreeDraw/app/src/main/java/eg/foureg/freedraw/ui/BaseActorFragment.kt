package eg.foureg.freedraw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import eg.foureg.freedraw.common.actor.Actor
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher

open class BaseActorFragment: Fragment(), Actor {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActorMessageDispatcher.registerActor(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActorMessageDispatcher.unregisterActor(this)
    }

    override fun handleMessage(message: ActorMessage) {

    }
}