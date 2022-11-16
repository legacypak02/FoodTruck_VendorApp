package com.stepbystep.bossapp.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stepbystep.bossapp.R;

import java.util.TreeSet;

public class MenuAddFragment extends Fragment {
    static ImageView menuImage;
    static EditText menuNameText, menuPriceText, menuCntText;
    Button menuAddButton, xButton;

    MenuManageFragment menuFragment;

    static boolean imageChoice;
    static String imageName;
    static Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_add,container,false);

        imageChoice = false;
        imageName = "";
        imageUri = null;

        menuFragment = new MenuManageFragment();

        menuImage = view.findViewById(R.id.MA_menuImg);
        menuNameText = view.findViewById(R.id.MA_menuNameText);
        menuPriceText = view.findViewById(R.id.MA_menuPriceText);
        menuCntText = view.findViewById(R.id.MA_menuCntText);
        menuAddButton = view.findViewById(R.id.MA_menuAddButton);
        xButton = view. findViewById(R.id.MA_xButton);

        return view;
    }


    public void onActivityResult(Uri uri, String ImgPath, String ImgName){
        imageChoice =true;

        menuImage.setImageURI(uri);

        imageName = ImgName;
        imageUri = uri;

    }


}
