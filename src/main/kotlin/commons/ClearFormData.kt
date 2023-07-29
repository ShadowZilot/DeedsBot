package commons

import core.Updating
import staging.StateHandling

interface ClearFormData {

    fun clearForm()

    class Base(
        private val mUpdating: Updating,
        private val mStates: StateHandling
    ) : ClearFormData {

        override fun clearForm() {
            mStates.state(mUpdating).editor(mStates).apply {
                deleteValue("name")
                deleteValue("contact")
                deleteValue("reason")
                deleteValue("contactBackParameter")
                deleteValue("waitForFirstContact")
                deleteValue("waitForSecondContact")
                deleteValue("waitForThirdContact")
            }.commit()
        }
    }
}