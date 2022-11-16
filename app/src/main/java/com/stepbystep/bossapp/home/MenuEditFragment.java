package com.stepbystep.bossapp.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stepbystep.bossapp.DO.Food;
import com.stepbystep.bossapp.R;

public class MenuEditFragment extends Fragment implements View.OnClickListener {
    static ImageView menuImage;
    static EditText menuNameText, menuPriceText, menuCntText;
    Button menuEditButton, xButton, menuDeleteButton;

    static Context context;

    static int menuId;
    static String ImagePath;
    static String realImagePath;
    static Uri ImageUri;
    boolean imageChange;

    static MenuManageFragment menuFragment = new MenuManageFragment();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_edit,container,false);
        context = view.getContext();

        imageChange = false;

        xButton = view.findViewById(R.id.ME_xButton);
        menuImage = (ImageView) view.findViewById(R.id.ME_menuImg);
        menuNameText = (EditText) view.findViewById(R.id.ME_menuNameText);
        menuCntText = (EditText) view.findViewById(R.id.ME_menuCntText);
        menuPriceText = (EditText) view.findViewById(R.id.ME_menuPriceText);
        menuEditButton = view.findViewById(R.id.ME_menuEditButton);
        menuDeleteButton = view.findViewById(R.id.ME_menuDeleteButton);

        menuEditButton.setOnClickListener(this);
        menuImage.setOnClickListener(this);
        xButton.setOnClickListener(this);
        menuDeleteButton.setOnClickListener(this);

        return view;
    }



    @Override
    public void onClick(View v) {
        if(v==menuImage){
            MenuManageActivity.menuEditClicked = true;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
            Log.d("menuChoice","image 선택 시작");
        }else if(v==menuEditButton){
            if(imageChange){
                ImageUpload(ImageUri, ImagePath);
            }else{
                editMenuUpdate(ImagePath);
            }
        }else if(v==xButton){
            menuFragment.menuEditShow(false);
        }else if(v==menuDeleteButton){
            menuDeleteRequest(menuId);
        }

    }

    public void onActivityResult(Uri uri,String ImgPath, String ImgName){
        Log.d("menuChoice","image edit fragment activity called & imgPath "+ImgPath);
        imageChange =true;

        ImageUri = uri;
        menuImage.setImageURI(uri);
        Log.d("menuChoice","image edit fragment setImageUri");
        realImagePath = ImgPath;
        ImagePath = ImgName;

    }

    public void ImageUpload(Uri uri, String ImgName){
        Uri file = uri;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.d("imageUpload","이미지 업로드 실패 "+exception);
                return;

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("imageUpload","이미지 업로드 성공");

                editMenuUpdate(ImgName);
            }
        });
    }

    private void editMenuUpdate(String imgPath){
        String menuName =menuNameText.getText().toString();
        String menuPrice = menuPriceText.getText().toString();
        String menuCnt = menuCntText.getText().toString();
        String menu_id = String.valueOf(menuId);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("FoodTruck").child("Food").child("foodname/"+menu_id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot.getRef().child("image").setValue(imgPath);
                snapshot.getRef().child("name").setValue(menuName);
                snapshot.getRef().child("cost").setValue(menuPrice);
                snapshot.getRef().child("content").setValue(menuCnt);

                menuFragment.menuEditShow(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("editMenu","menu업로드 실패");
            }
        });

    }

    private void menuDeleteRequest(int menu_Id){
        String menu_id = Integer.toString(menu_Id);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("메뉴를 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("foodname/"+menu_id);
                        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                menuFragment.menuEditShow(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("menuDelete","메뉴 삭제 실패");
                                Toast.makeText(getContext(),"메뉴를 삭제하는데 실패했습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(43,144,217));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        dialog.show();
    }
}
