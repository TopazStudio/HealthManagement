package com.flycode.healthbloom.ui.base;

import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.di.component.DaggerPresenterComponent;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;

import lombok.Getter;
import lombok.Setter;

public class BaseServicePresenter<V extends MvpService> implements MvpServicePresenter<V> {
    @Getter @Setter
    private V service;
    private UtilityWrapper utilityWrapper = new UtilityWrapper();

    protected DatabaseDefinition database;
    protected User defaultUser;

    public void onAttach(V service) {
        this.service = service;
        DaggerPresenterComponent.create().inject(utilityWrapper);

        this.database = utilityWrapper.database;
        this.defaultUser = utilityWrapper.defaultUser;
    }

    public void onDetach() {
        this.service = null;
    }
}
