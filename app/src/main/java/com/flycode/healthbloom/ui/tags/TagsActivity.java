package com.flycode.healthbloom.ui.tags;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.databinding.CustomTagEntryBinging;
import com.flycode.healthbloom.databinding.TagsActivityBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TagsActivity
        extends BaseViewWithNav
        implements TagsContract.TagsView,ColorChooserDialog.ColorCallback {

    @Inject
    TagsContract.TagsPresenter<TagsContract.TagsView> presenter;
    @Inject
    TagsAdapter tagsAdapter;

    private TagsActivityBinding tagsActivityBinding;
    private CustomTagEntryBinging customTagEntryBinging;
    private MaterialDialog customTagEntryDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD CONTENT
        tagsActivityBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_tags,null,false);
        baseActivityBinding.contentFrame.addView(tagsActivityBinding.getRoot());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) tagsActivityBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    private void init() {
        presenter.fetchTags();
        setupChipsRecycler();
        setupTagEntryView();
    }

    private void setupTagEntryView() {
        customTagEntryBinging = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.custom_view_tag_entry  ,null,false);

        customTagEntryDialog = new MaterialDialog.Builder(this)
                .customView(customTagEntryBinging.getRoot(),true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Get tag in dialog
                        Tag tag = customTagEntryBinging.getTag();

                        //Check if update
                        if(tag.id != 0)
                            presenter.updateTag(tag,tagsAdapter.getTagList().indexOf(tag));
                        else presenter.addTag(tag);

                    }
                })
                .build();


        final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        final Context context = this;

        customTagEntryBinging.primaryColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorChooserDialog.Builder(context,
                        R.string.tag_primary_color_picker_title)
                        .accentMode(true)
                        .preselect(customTagEntryBinging.getTag().PrimaryColor.get())
                        .show(fragmentManager);
            }
        });

        customTagEntryBinging.secondaryColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorChooserDialog.Builder(context,
                        R.string.tag_secondary_color_picker_title)
                        .accentMode(true)
                        .preselect(customTagEntryBinging.getTag().SecondaryColor.get())
                        .show(fragmentManager);
            }
        });
    }

    private void setupChipsRecycler() {
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setMaxViewsInRow(3)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        tagsActivityBinding.chipsRecyclerView.setLayoutManager(chipsLayoutManager);
        tagsActivityBinding.chipsRecyclerView.addItemDecoration(
                new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.tag_spacing),
                getResources().getDimensionPixelOffset(R.dimen.tag_spacing)));

        tagsAdapter.setOnTagClickedListener(new TagsAdapter.OnTagClickedListener() {
            @Override
            public void onTagClicked(Tag tag) {
                customTagEntryBinging.setTag(tag);
                customTagEntryDialog.show();
            }
        });
        tagsAdapter.setOnTagDeletedListener(new TagsAdapter.OnTagDeletedListener() {
            @Override
            public void onTagDeleted(Tag tag) {
                presenter.deleteTag(tag);
            }
        });
        tagsActivityBinding.chipsRecyclerView.setAdapter(tagsAdapter);
    }

    public void onAddTag(View view){
        Tag tag = new Tag();
        //Set default colours
        tag.PrimaryColor.set(Color.WHITE);
        tag.SecondaryColor.set(Color.LTGRAY);

        customTagEntryBinging.setTag(tag);
        customTagEntryDialog.show();
    }

    @Override
    public void addTag(Tag tag){
        if (tagsAdapter.getTagList().add(tag)) {
            tagsAdapter.notifyItemInserted(tagsAdapter.getTagList().size() - 1);
        }
    }

    @Override
    public void addMultipleTags(List<Tag> tags) {
        int startPosition = tagsAdapter.getTagList().size(); //Initial start position before adding.
        if (tagsAdapter.getTagList().addAll(tags)) {
            tagsAdapter.notifyItemRangeInserted(startPosition, tagsAdapter.getTagList().size());
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        int index = tagsAdapter.getTagList().indexOf(tag);
        if(tagsAdapter.getTagList().remove(tag)){
            tagsAdapter.notifyItemRemoved(index);
        }
    }

    @Override
    public void updateTag(Tag tag,int index){
        if(tagsAdapter.getTagList().remove(index) != null){
            tagsAdapter.getTagList().add(index,tag);
            tagsAdapter.notifyItemChanged(index);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        if(dialog.getTitle() == R.string.tag_primary_color_picker_title){
            //Update Primary color of tag in dialog
            customTagEntryBinging.getTag().PrimaryColor.set(selectedColor);
        }else{
            customTagEntryBinging.getTag().SecondaryColor.set(selectedColor);
        }
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        dialog.dismiss();
    }
}
