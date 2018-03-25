package com.example.artem.newsapp.dialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.artem.newsapp.MainActivity;
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.BookMark;
import com.example.artem.newsapp.fragments.ViewModel;
import com.squareup.picasso.Picasso;


public class MyDialogFragment extends DialogFragment{

    private String urlForImage;
    private String titleText;
    private String linkName;
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
        getDialog().setTitle("Simple Dialog");

        viewModel = new ViewModel(getActivity().getApplication());

        ImageView image = rootView.findViewById(R.id.fragment_dialog__image);
        Picasso.with(getActivity())
                .load(urlForImage)
                .into(image);

        TextView title = rootView.findViewById(R.id.fragment_dialog__title);
        title.setText(titleText);

        Button buttonForOpeningLink = rootView.findViewById(R.id.fragment_dialog__open_in_browser);
        buttonForOpeningLink.setOnClickListener(new OnButtonForOpeningLinkClicked());

        Button buttonToBookmarkPage = rootView.findViewById(R.id.fragment_dialog__add_in_bookmarks);
        buttonToBookmarkPage.setOnClickListener(new OnButtonAddToBookMarksClicked());

        Button buttonToCloseDialogFragment = rootView.findViewById(R.id.fragment_dialog__cancel);
        buttonToCloseDialogFragment.setOnClickListener(new OnButtonCloseDialogFragmentClicked());

        Button buttonToFindAndDeleteBookMark = rootView.findViewById(R.id.fragment_dialog__delete_from_bookmarks);
        buttonToFindAndDeleteBookMark.setOnClickListener(new OnButtonDeleteBookmarkClicked());

        return rootView;
    }

    public void setImageUrl(String imageUrl){
        urlForImage = imageUrl;
    }
    public void setTitleText(String title){
        titleText = title;
    }
    public void setTextOfLink(String link){
        linkName = link;
    }


    public class OnButtonForOpeningLinkClicked implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String url = linkName;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    public class OnButtonAddToBookMarksClicked implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            boolean isClicked = true;
            BookMark bookMark = new BookMark();
            bookMark.setTitleOfBookMarkedPage(titleText);
            bookMark.setThumbnailUrlOfBookMarkedPage(urlForImage);
            viewModel.addBookMarkInAList(bookMark);
            ((MainActivity) getActivity())
                    .bus()
                    .send(isClicked);
            dismiss();
        }
    }

    public class OnButtonCloseDialogFragmentClicked implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            dismiss();
        }
    }

    public class OnButtonDeleteBookmarkClicked implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            boolean isClicked = false;
            String title = titleText;
            viewModel.findAndDeleteBookmark(title);
            ((MainActivity) getActivity())
                    .bus()
                    .send(isClicked);
            dismiss();
        }
    }

}
