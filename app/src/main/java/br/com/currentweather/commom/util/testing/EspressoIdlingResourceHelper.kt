package br.com.currentweather.commom.util.testing

import br.com.currentweather.commom.idling.ApiIdlingResource

class EspressoIdlingResourceHelper : IdlingResourceHelper {
    override fun increment() = ApiIdlingResource.increment()

    override fun decrement() = ApiIdlingResource.decrement()
}
