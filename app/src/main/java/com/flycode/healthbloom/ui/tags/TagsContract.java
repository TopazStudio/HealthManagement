package com.flycode.healthbloom.ui.tags;

import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

import java.util.List;

public interface TagsContract {
    interface TagsView extends MvpView {
        void addTag(Tag tag);
        void addMultipleTags(List<Tag> tags);
        void deleteTag(Tag tag);
        void updateTag(Tag tag,int index);
    }
    interface TagsPresenter<V extends TagsContract.TagsView> extends MvpPresenter<V> {
        void deleteTag(Tag tag);
        void fetchTags();
        void updateTag(Tag tag,int index);
        void addTag(Tag tag);
    }
}
