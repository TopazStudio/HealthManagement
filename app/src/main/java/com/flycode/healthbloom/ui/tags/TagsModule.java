package com.flycode.healthbloom.ui.tags;

import android.content.Context;

import com.flycode.healthbloom.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class TagsModule {

    @Provides
    Context provideTagsActivityContext(TagsActivity tagsActivity){
        return tagsActivity;
    }

    @Provides
    TagsContract.TagsPresenter<TagsContract.TagsView> provideTagsPresenter() {
        return new TagsPresenter<>();
    }

    @Provides
    @PerActivity
    TagsAdapter provideTagsAdapter(TagsActivity tagsActivity){
        return new TagsAdapter(tagsActivity);
    }
}
