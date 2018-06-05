package com.flycode.healthbloom.ui.exercise.exerciseView;

import android.content.Context;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.di.scope.PerActivity;
import com.flycode.healthbloom.ui.tags.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ExerciseViewModule {
    @Provides
    Context provideExerciseViewActivityContext(ExerciseViewActivity exerciseViewActivity){
        return exerciseViewActivity;
    }

    @Provides
    @PerActivity
    ExerciseViewContract.ExerciseViewPresenter<ExerciseViewContract.ExerciseViewView>
    provideExerciseViewPresenter(
            Note note,
            @Named("edit_tags") List<Tag> editTags,
            @Named("view_tags") List<Tag> viewTags,
            Steps steps
    ) {
        ExerciseViewPresenter<ExerciseViewContract.ExerciseViewView> presenter = new ExerciseViewPresenter<>();
        presenter.setNote(note);
        presenter.setSteps(steps);
        presenter.setEditTags(editTags);
        presenter.setViewTags(viewTags);
        return presenter;
    }

    @Provides
    StepsUpperDetailsViewPagerAdapter provideStepUpperDetailsAdapter(ExerciseViewActivity exerciseViewActivity,Steps steps){
        return new StepsUpperDetailsViewPagerAdapter(exerciseViewActivity.getSupportFragmentManager());
    }

    @PerActivity
    @Provides
    TagsAdapter provideTagsAdapter(ExerciseViewActivity exerciseViewActivity){
        return new TagsAdapter(exerciseViewActivity);
    }

    @PerActivity
    @Provides
    Note provideNote(){
        return new Note();
    }

    @PerActivity
    @Provides
    @Named("view_tags")
    List<Tag> provideStepsTags(){
        return new ArrayList<>();
    }

    @PerActivity
    @Provides
    @Named("edit_tags")
    List<Tag> provideEditTags(){
        return new ArrayList<>();
    }

    @PerActivity
    @Provides
    Steps provideSteps(){
        return new Steps();
    }
}
