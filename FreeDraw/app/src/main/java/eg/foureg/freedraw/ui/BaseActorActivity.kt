package eg.foureg.freedraw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eg.foureg.freedraw.common.actor.Actor
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher

open class BaseActorActivity: AppCompatActivity(), Actor {

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