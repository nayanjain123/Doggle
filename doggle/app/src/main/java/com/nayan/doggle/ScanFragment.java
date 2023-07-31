package com.nayan.doggle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ScanFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private ImageView imageView;
    private TextView resultTextView;
    private Button captureButton;
    private Button galleryButton;

    private ImageClassifier imageClassifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        resultTextView = rootView.findViewById(R.id.result);
        captureButton = rootView.findViewById(R.id.captureButton);
        galleryButton = rootView.findViewById(R.id.galleryButton);

        try {
            imageClassifier = new ImageClassifier(getActivity());
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Failed to initialize image classifier.", Toast.LENGTH_SHORT).show();
        }

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    openImageGallery();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_GALLERY);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap capturedImage = (Bitmap) extras.get("data");
                imageView.setImageBitmap(capturedImage);
                classifyImage(capturedImage);
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(selectedImage);
                    classifyImage(selectedImage);
                } catch (IOException e) {
                    Toast.makeText(requireActivity(), "Failed to open image from gallery.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void classifyImage(Bitmap image) {
        if (imageClassifier != null) {
            imageClassifier.classifyImage(image, new ImageClassifier.ClassifierCallback() {
                @Override
                public void onClassificationResult(String result, float confidence) {
                    String classificationResult = result + "-> " + String.format(Locale.getDefault(), "%.2f", confidence * 100) + "%";
                    resultTextView.setText(classificationResult);
                }


                public void onError() {
                    Toast.makeText(requireActivity(), "Failed to classify image.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireActivity(), "Image classifier not initialized.", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openImageGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }
}
