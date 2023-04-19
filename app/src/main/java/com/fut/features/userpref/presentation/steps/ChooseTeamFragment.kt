package com.fut.features.userpref.presentation.steps

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseTeamFragment : UserStartPreferencesStepBaseFragment() {

    override var step = UserStartPreferencesStepEnum.CHOOSE_TEAM

}