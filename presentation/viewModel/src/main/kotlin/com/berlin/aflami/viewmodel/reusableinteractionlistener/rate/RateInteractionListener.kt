package com.berlin.aflami.viewmodel.reusableinteractionlistener.rate

interface RateInteractionListener {
    fun onRateIconClicked(id: Long)
    fun onSelectRateClicked(rate: Float)
    fun onSubmitRateClicked(rate: Int)
    fun onCancelRatingClicked()
}