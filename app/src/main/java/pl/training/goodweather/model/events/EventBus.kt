package pl.training.goodweather.model.events

import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject

class EventBus<T> {
    private val subject = ReplaySubject.create<T>(1)

    fun publish(event: T) {
        subject.onNext(event)
    }

    fun get() = subject as Observable<T>
}